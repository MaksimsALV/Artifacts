package com.artifacts.controller.charcontroller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import static com.artifacts.Launcher.runChef;
import static com.artifacts.Launcher.stopChef;

@Controller
public class ChefController {
    @PostMapping("/runChef")
    public String start(@RequestParam String action,
                        @RequestParam String value,
                        @RequestParam(required = false, defaultValue = "") String utilityOne,
                        @RequestParam(required = false, defaultValue = "") String utilityTwo) {
        runChef(action, value, utilityOne, utilityTwo);
        return "redirect:/";
    }
    @PostMapping("/stopChef")
    public String stop() {
        stopChef();
        return "redirect:/";
    }
}
