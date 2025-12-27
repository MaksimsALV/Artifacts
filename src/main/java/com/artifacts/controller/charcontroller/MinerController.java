package com.artifacts.controller.charcontroller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

import static com.artifacts.runApplication.runMiner;
import static com.artifacts.runApplication.stopMiner;

@Controller
public class MinerController {
    @PostMapping("/runMiner")
    public String start() throws InterruptedException {
        runMiner();
        return "redirect:/";
    }
    @PostMapping("/stopMiner")
    public String stop() throws InterruptedException {
        stopMiner();
        return "redirect:/";
    }
}
