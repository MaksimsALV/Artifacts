package com.artifacts.controller.charcontroller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import static com.artifacts.Launcher.runLumberjack;
import static com.artifacts.Launcher.stopLumberjack;

@Controller
public class LumberjackController {
    @PostMapping("/runLumberjack")
    public String start(@RequestParam String action,
                        @RequestParam String activityLocation,
                        @RequestParam(required = false, defaultValue = "") String utilityOne,
                        @RequestParam(required = false, defaultValue = "") String utilityTwo) {
        runLumberjack(action, activityLocation, utilityOne, utilityTwo);
        return "redirect:/";
    }
    @PostMapping("/stopLumberjack")
    public String stop() {
        stopLumberjack();
        return "redirect:/";
    }
}
