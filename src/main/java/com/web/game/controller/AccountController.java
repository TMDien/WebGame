package com.web.game.controller;

import com.web.game.dao.UserDAO;
import com.web.game.entity.User;
import com.web.game.service.UserService;
import com.web.game.user.RegisUser;
import com.web.game.util.EmailDetail;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;
import java.util.UUID;
import java.util.logging.Logger;

@Controller
@RequestMapping("/")
public class AccountController {

    private Logger logger = Logger.getLogger(getClass().getName());

    private UserService userService;

    @Value("${password.token.reset.timeout.minutes}")
    private int password_token_timeout;

    private String site_domain = "http://localhost:8080/";

    @Autowired
    public AccountController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("regisUser", new RegisUser());
        return "views_account/login";
    }

    @PostMapping("/login/processRegistrationForm")
    public String processRegistrationForm(@Valid @ModelAttribute("regisUser") RegisUser regisUser,
                                          BindingResult bindingResult, HttpSession session, Model model) {

        String email = regisUser.getEmail();
        logger.info("Processing registration form for: " + email);

        // form validation
        if (bindingResult.hasErrors()){
            return "views_account/login";
        }

        //Check the database if user already exists
        User existing = userService.findByUserName(email);
        if (existing != null){
            model.addAttribute("regisUser", new RegisUser());
            model.addAttribute("registrationError", "User name already exists.");

            logger.warning("User name already exists.");
            return "views_account/login";
        }

        // create user account and store in the database
        userService.save(regisUser);

        logger.info("Successfully created user: " + email);

        // place user in the web http session for later use
        session.setAttribute("user", regisUser);

        return "redirect:/";

    }

    @GetMapping("/signup")
    public String signup(Model model) {
        return "views_account/signup";
    }


    @GetMapping("/forgot-password")
    public String forgot_password(Model model) {
        return "views_account/forgot_password";
    }

    @PostMapping("/reset-password")
    public String reset_password(@Valid @ModelAttribute("username") String email, RedirectAttributes attributes, Model model) {

        //Check the database if user already exists
        User existing = userService.findByUserName(email);

        if (existing != null){
            User user = userService.findByUserName(email);
            String reset_token = UUID.randomUUID().toString();
            user.setToken(reset_token);
            user.setPassword_reset_token_expiry(LocalDateTime.now().plusMinutes(password_token_timeout));
            attributes.addFlashAttribute("message", "Password reset email sent");
            userService.save(user);
            String reset_message = "This is reset password link: " + site_domain + "change-password?token=" + reset_token; EmailDetail emailDetail = new EmailDetail(user.getEmail(), reset_message, "Reset password Luxd Gaming Shop");
            if (userService.sendSimpleEmail(emailDetail) == false){
                attributes.addFlashAttribute("error", "Error while sending email, contact admin");
                return "redirect:/forgot-password";
            }


            return "redirect:/login";
        }else {
            attributes.addFlashAttribute("error", "User does not exist");
            return "redirect:/forgot-password";
        }
    }

    @GetMapping("/change-password")
    public String change_password(@RequestParam("token") String token, RedirectAttributes attributes,Model model) {
        if(token.equals("")){
            attributes.addFlashAttribute("error", "Invalid token");
            return "redirect:/forgot-password";
        }
        User existing = userService.findByToken(token);
        if(existing != null){
            User user = userService.findByToken(token);
            LocalDateTime now = LocalDateTime.now();
            if (now.isAfter(existing.getPassword_reset_token_expiry())){
                attributes.addFlashAttribute("error", "Invalid token");
                return "redirect:/forgot-password";
            }
            model.addAttribute("account", user);
            return "views_account/change_password";
        }
        attributes.addFlashAttribute("error", "Invalid token");
        return "redirect:/forgot-password";
    }

    @PostMapping("/post-change-password")
    public String post_change_password(@ModelAttribute User user, RedirectAttributes attributes, Model model) {
        User user_by_name = userService.findByUserName(user.getEmail());
        user_by_name.setPassword(user.getPassword());
        user_by_name.setToken("");
        userService.save(user_by_name);
        attributes.addFlashAttribute("message", "Password changed successfully");
        return "redirect:/login";
    }
}
