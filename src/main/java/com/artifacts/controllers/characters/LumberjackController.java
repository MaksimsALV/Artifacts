package com.artifacts.controllers.characters;

import com.artifacts.game.launcher.Launcher;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

//import static com.artifacts.game.launcher.Launcher.runLumberjack;
//import static com.artifacts.game.launcher.Launcher.stopLumberjack;

@Controller
public class LumberjackController {
    private final Launcher launcher;

    public LumberjackController(Launcher launcher) {
        this.launcher = launcher;
    }

    @PostMapping("/runLumberjack")
    public String start(@RequestParam String action,
                        @RequestParam(required = false, defaultValue = "") String activityLocation,
                        @RequestParam(required = false, defaultValue = "") String utilityOne,
                        @RequestParam(required = false, defaultValue = "") String utilityTwo,
                        @RequestParam(required = false, defaultValue = "") String consumable,
                        @RequestParam(required = false, defaultValue = "false") boolean fightTask) {
        launcher.runLumberjack(action, activityLocation, utilityOne, utilityTwo, consumable, fightTask);
        return "redirect:/";
    }
    @PostMapping("/stopLumberjack")
    public String stop() {
        launcher.stopLumberjack();
        return "redirect:/";
    }
}
