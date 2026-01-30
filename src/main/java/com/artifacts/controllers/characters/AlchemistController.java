package com.artifacts.controllers.characters;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import static com.artifacts.game.launcher.Launcher.runAlchemist;
import static com.artifacts.game.launcher.Launcher.stopAlchemist;

@Controller
public class AlchemistController {
    @PostMapping("/runAlchemist")
    public String start(@RequestParam String action,
                        @RequestParam(required = false, defaultValue = "") String activityLocation,
                        @RequestParam(required = false, defaultValue = "") String utilityOne,
                        @RequestParam(required = false, defaultValue = "") String utilityTwo,
                        @RequestParam(required = false, defaultValue = "") String consumable,
                        @RequestParam(required = false, defaultValue = "false") boolean fightTask) {
        runAlchemist(action, activityLocation, utilityOne, utilityTwo, consumable, fightTask);
        return "redirect:/";
    }
    @PostMapping("/stopAlchemist")
    public String stop() {
        stopAlchemist();
        return "redirect:/";
    }
}
