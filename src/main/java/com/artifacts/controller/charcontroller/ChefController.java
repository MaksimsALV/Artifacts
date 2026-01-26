package com.artifacts.controller.charcontroller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import static com.artifacts.game.launcher.Launcher.runChef;
import static com.artifacts.game.launcher.Launcher.stopChef;

@Controller
public class ChefController {
    @PostMapping("/runChef")
    public String start(@RequestParam String action,
                        @RequestParam(required = false, defaultValue = "") String activityLocation,
                        @RequestParam(required = false, defaultValue = "") String utilityOne,
                        @RequestParam(required = false, defaultValue = "") String utilityTwo,
                        @RequestParam(required = false, defaultValue = "") String consumable,
                        @RequestParam(required = false, defaultValue = "false") boolean fightTask) {
        runChef(action, activityLocation, utilityOne, utilityTwo, consumable, fightTask);
        return "redirect:/";
    }
    @PostMapping("/stopChef")
    public String stop() {
        stopChef();
        return "redirect:/";
    }
}
