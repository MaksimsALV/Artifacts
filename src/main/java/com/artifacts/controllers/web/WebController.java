package com.artifacts.controllers.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;

import static com.artifacts.api.endpoints.get.GetAllItems.*;
import static com.artifacts.api.endpoints.get.GetAllMonsters.getAllMonstersAsList;
import static com.artifacts.api.endpoints.get.GetAllResources.getAllResourceCodesAsList;
import static com.artifacts.api.endpoints.get.GetAllResources.getAllResourcesAsList;

@Controller
public class WebController {
    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("resources", getAllResourceCodesAsList());
        model.addAttribute("monsters", getAllMonstersAsList());
        model.addAttribute("weapons", getAllWeaponCraftingItemsAsList());
        model.addAttribute("gear", getAllGearCraftingItemsAsList());
        model.addAttribute("jewelry", getAllJewelryCraftingItemsAsList());
        model.addAttribute("wood", getAllWoodcuttingCraftingItemsAsList());
        model.addAttribute("mining", getAllMiningCraftingItemsAsList());
        model.addAttribute("cooking", getAllCookingCraftingItemsAsList());
        model.addAttribute("alchemy", getAllAlchemyCraftingItemsAsList());
        model.addAttribute("utilities", getAllUtilityItemsAsList());
        model.addAttribute("consumables", getAllConsumablesAsList());
        return "index";
    }
}