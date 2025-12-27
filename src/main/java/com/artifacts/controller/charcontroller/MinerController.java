package com.artifacts.controller.charcontroller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import static com.artifacts.Launcher.runMiner;
import static com.artifacts.Launcher.stopMiner;

@Controller
public class MinerController {
    @PostMapping("/runMiner")
    public String start(@RequestParam String action, @RequestParam String value) {
        runMiner(action, value);
        return "redirect:/";
    }
    @PostMapping("/stopMiner")
    public String stop() {
        stopMiner();
        return "redirect:/";
    }
}
