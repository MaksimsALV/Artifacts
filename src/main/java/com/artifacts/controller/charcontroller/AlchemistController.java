package com.artifacts.controller.charcontroller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

import static com.artifacts.runApplication.runAlchemist;
import static com.artifacts.runApplication.stopAlchemist;

@Controller
public class AlchemistController {
    @PostMapping("/runAlchemist")
    public String start() {
        runAlchemist();
        return "redirect:/";
    }
    @PostMapping("/stopAlchemist")
    public String stop() {
        stopAlchemist();
        return "redirect:/";
    }
}
