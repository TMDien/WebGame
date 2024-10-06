package com.web.game.controller;

import jakarta.persistence.Column;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class AdminController {

    @GetMapping("/admin")
    public String admin() {
        return "views_admin/admin";
    }

    @GetMapping("/menu")
    public String menu() {
        return "views_admin/menu";
    }

    @GetMapping("/add_menu")
    public String addMenu() {
        return "views_admin/add-menu";
    }

    @GetMapping("/edit_menu")
    public String editMenu() {
        return "views_admin/edit-menu";
    }

    @GetMapping("/banner")
    public String banner() {
        return "views_admin/banner";
    }

    @GetMapping("/edit_banner")
    public String editBanner() {
        return "views_admin/edit-banner";
    }

    @GetMapping("/add_banner")
    public String addBanner() {
        return "views_admin/add-banner";
    }

}
