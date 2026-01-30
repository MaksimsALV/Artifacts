package com.artifacts.controller.webcontroller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;

import static com.artifacts.api.endpoints.get.GetAllItems.getAllConsumablesAsList;
import static com.artifacts.api.endpoints.get.GetAllItems.getAllUtilityItemsAsList;
import static com.artifacts.api.endpoints.get.GetAllMonsters.getAllMonstersAsList;
import static com.artifacts.api.endpoints.get.GetAllResources.getAllResourcesAsList;

@Controller
public class WebController {
    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("resources", getAllResourcesAsList());
        model.addAttribute("monsters", getAllMonstersAsList());
        model.addAttribute("utilities", getAllUtilityItemsAsList());
        model.addAttribute("consumables", getAllConsumablesAsList());
        return "index";
    }
}