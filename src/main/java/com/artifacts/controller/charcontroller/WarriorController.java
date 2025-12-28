package com.artifacts.controller.charcontroller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import static com.artifacts.Launcher.runWarrior;
import static com.artifacts.Launcher.stopWarrior;

@Controller
public class WarriorController {
    @PostMapping("/runWarrior")
    public String start(@RequestParam String action,
                        @RequestParam String value,
                        @RequestParam(required = false, defaultValue = "") String utilityOne,
                        @RequestParam(required = false, defaultValue = "") String utilityTwo) {
        runWarrior(action, value, utilityOne, utilityTwo);
        return "redirect:/";
    }
    @PostMapping("/stopWarrior")
    public String stop() {
        stopWarrior();
        return "redirect:/";
    }
}
