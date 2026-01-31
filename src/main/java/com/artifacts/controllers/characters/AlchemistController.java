package com.artifacts.controllers.characters;

import com.artifacts.game.launcher.Launcher;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

//import static com.artifacts.game.launcher.Launcher.runAlchemist;
//import static com.artifacts.game.launcher.Launcher.stopAlchemist;

@Controller
public class AlchemistController {
    private final Launcher launcher;

    public AlchemistController(Launcher launcher) {
        this.launcher = launcher;
    }

    @PostMapping("/runAlchemist")
    public String start(@RequestParam String action,
                        @RequestParam(required = false, defaultValue = "") String activityLocation,
                        @RequestParam(required = false, defaultValue = "") String utilityOne,
                        @RequestParam(required = false, defaultValue = "") String utilityTwo,
                        @RequestParam(required = false, defaultValue = "") String consumable,
                        @RequestParam(required = false, defaultValue = "false") boolean fightTask) {
        launcher.runAlchemist(action, activityLocation, utilityOne, utilityTwo, consumable, fightTask);
        return "redirect:/";
    }
    @PostMapping("/stopAlchemist")
    public String stop() {
        launcher.stopAlchemist();
        return "redirect:/";
    }
}
