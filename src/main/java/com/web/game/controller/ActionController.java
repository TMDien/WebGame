package com.web.game.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class ActionController {
    @GetMapping("/detail_assasin_creed")
    public String detailAssasinCreed(Model model) {
        return "views_detail_product/product_assasin";
    }

    @GetMapping("/detail_tower_of_fantasy")
    public String detailTowerOfFantasy(Model model) {
        return "views_detail_product/product_tower";
    }

    @GetMapping("/detail_super_people")
    public String detailSuperPeople(Model model) {
        return "views_detail_product/product_super";
    }

    @GetMapping("/detail_dragon_fight")
    public String detailDragonFight(Model model) {
        return "views_detail_product/product_dragon";
    }

    @GetMapping("/categories_action")
    public String categoriesAction(Model model) {
        return "categories/categories_action";
    }

    @GetMapping("/categories_strategy")
    public String categoriesStrategy(Model model) {
        return "categories/categories_strategy";
    }

    @GetMapping("/categories_adventure")
    public String categoriesAdventure(Model model) {
        return "categories/categories_adventure";
    }

    @GetMapping("/categories_role")
    public String categoriesRole(Model model) {
        return "categories/categories_role";
    }

    @GetMapping("/free_store")
    public String showFreeStore() {
        return "categories/free_store";
    }
}
