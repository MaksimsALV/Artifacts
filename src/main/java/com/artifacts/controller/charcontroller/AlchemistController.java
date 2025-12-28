package com.artifacts.controller.charcontroller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import static com.artifacts.Launcher.runAlchemist;
import static com.artifacts.Launcher.stopAlchemist;

@Controller
public class AlchemistController {
    @PostMapping("/runAlchemist")
    public String start(@RequestParam String action,
                        @RequestParam String value,
                        @RequestParam(required = false, defaultValue = "") String utilityOne,
                        @RequestParam(required = false, defaultValue = "") String utilityTwo) {
        runAlchemist(action, value, utilityOne, utilityTwo);
        return "redirect:/";
    }
    @PostMapping("/stopAlchemist")
    public String stop() {
        stopAlchemist();
        return "redirect:/";
    }
}
