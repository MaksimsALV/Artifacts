package com.artifacts.controller.charcontroller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

import static com.artifacts.Launcher.runWarrior;
import static com.artifacts.Launcher.stopWarrior;

@Controller
public class WarriorController {
    @PostMapping("/runWarrior")
    public String start() {
        runWarrior();
        return "redirect:/";
    }
    @PostMapping("/stopWarrior")
    public String stop() {
        stopWarrior();
        return "redirect:/";
    }
}
