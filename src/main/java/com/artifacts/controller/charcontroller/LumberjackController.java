package com.artifacts.controller.charcontroller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

import static com.artifacts.runApplication.runLumberjack;
import static com.artifacts.runApplication.stopLumberjack;

@Controller
public class LumberjackController {
    @PostMapping("/runLumberjack")
    public String start() {
        runLumberjack();
        return "redirect:/";
    }
    @PostMapping("/stopLumberjack")
    public String stop() {
        stopLumberjack();
        return "redirect:/";
    }
}
