package com.artifacts.controllers.characters;

import com.artifacts.game.Launcher;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

//import static com.artifacts.game.Launcher.runLumberjack;
//import static com.artifacts.game.Launcher.stopLumberjack;

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
                        @RequestParam(required = false, defaultValue = "false") boolean fightTask,
                        @RequestParam(required = false, defaultValue = "false") boolean miningAll) {
        launcher.runLumberjack(action, activityLocation, utilityOne, utilityTwo, consumable, fightTask, miningAll);
        return "redirect:/";
    }
    @PostMapping("/stopLumberjack")
    public String stop() {
        launcher.stopLumberjack();
        return "redirect:/";
    }
}
