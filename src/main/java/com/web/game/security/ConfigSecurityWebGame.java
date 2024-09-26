package com.web.game.security;

import com.web.game.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
public class ConfigSecurityWebGame {

    private static final String[] WHITELIST = {
            "/",
            "/login/**",
            "/signup",
            "/forgot-password",
            "/reset-password",
            "/change-password",
            "post-change-password",
            "/assets/css/**",
            "/icon/**",
            "/assets/images/**",
            "/assets/js/**",
            "/vendor/bootstrap/**",
            "/vendor/jquery/**",

    };

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider(UserService userService) {
        DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
        auth.setUserDetailsService(userService); //set the custom user details service
        auth.setPasswordEncoder(passwordEncoder()); //set the password encoder - bcrypt
        return auth;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, AuthenticationSuccessHandler authenticationSuccessHandler) throws Exception {

        http
                .authorizeHttpRequests(configurer ->
                        configurer
                                .requestMatchers(WHITELIST).permitAll()
                                .requestMatchers("/", "/shop", "/contact", "/product-details").hasAnyRole("EMPLOYEE", "MANAGER", "ADMIN")
                                .requestMatchers("/leaders/**", "/manager").hasRole("MANAGER")
                                .requestMatchers("/systems/**", "/admin").hasRole("ADMIN")
                                .anyRequest().authenticated()
                )
                .formLogin(form ->
                        form
                                .loginPage("/login").loginProcessingUrl("/authenticateTheUser")
                                .defaultSuccessUrl("/", true).failureUrl("/login?error")
                                .successHandler(authenticationSuccessHandler)
                                .permitAll()
                )
                .logout(logout -> logout.logoutUrl("/logout").logoutSuccessUrl("/")
                ).rememberMe(me -> me.rememberMeParameter("remember-me"));


        http.csrf(csrf -> csrf.disable());
        return http.build();
    }


}
