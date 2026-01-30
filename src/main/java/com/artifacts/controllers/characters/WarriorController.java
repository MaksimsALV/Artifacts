package com.artifacts.controllers.characters;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import static com.artifacts.game.launcher.Launcher.runWarrior;
import static com.artifacts.game.launcher.Launcher.stopWarrior;

@Controller
public class WarriorController {
    @PostMapping("/runWarrior")
    public String start(@RequestParam String action,
                        @RequestParam(required = false, defaultValue = "") String activityLocation,
                        @RequestParam(required = false, defaultValue = "") String utilityOne,
                        @RequestParam(required = false, defaultValue = "") String utilityTwo,
                        @RequestParam(required = false, defaultValue = "") String consumable,
                        @RequestParam(required = false, defaultValue = "false") boolean fightTask) {
        runWarrior(action, activityLocation, utilityOne, utilityTwo, consumable, fightTask);
        return "redirect:/";
    }
    @PostMapping("/stopWarrior")
    public String stop() {
        stopWarrior();
        return "redirect:/";
    }
}
