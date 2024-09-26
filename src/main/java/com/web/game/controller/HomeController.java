package com.web.game.controller;

import com.web.game.entity.User;
import com.web.game.service.UserService;
import com.web.game.user.RegisUser;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.logging.Logger;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home(Model model) {
        return "views_web/home";
    }

    @GetMapping("/product-details")
    public String productDetails(Model model) {
        return "views_web/product-details";
    }

    @GetMapping("/contact")
    public String contact(Model model) {
        return "views_web/contact";
    }

    @GetMapping("/shop")
    public String shop(Model model) {
        return "views_web/shop";
    }

    @GetMapping("/leaders")
    public String showLeaders() {

        return "leaders";
    }

    // add request mapping for /systems

    @GetMapping("/systems")
    public String showSystems() {

        return "systems";
    }


}
