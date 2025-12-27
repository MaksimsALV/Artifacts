package com.artifacts.controller.charcontroller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

import static com.artifacts.Launcher.runChef;
import static com.artifacts.Launcher.stopChef;

@Controller
public class ChefController {
    @PostMapping("/runChef")
    public String start() {
        runChef();
        return "redirect:/";
    }
    @PostMapping("/stopChef")
    public String stop() {
        stopChef();
        return "redirect:/";
    }
}
