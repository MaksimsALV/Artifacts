package com.artifacts.controllers.characters;

import com.artifacts.game.Launcher;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

//import static com.artifacts.game.Launcher.runWarrior;
//import static com.artifacts.game.Launcher.stopWarrior;

@Controller
public class WarriorController {
    private final Launcher launcher;

    public WarriorController(Launcher launcher) {
        this.launcher = launcher;
    }

    @PostMapping("/runWarrior")
    public String start(@RequestParam String action,
                        @RequestParam(required = false, defaultValue = "") String activityLocation,
                        @RequestParam(required = false, defaultValue = "") String utilityOne,
                        @RequestParam(required = false, defaultValue = "") String utilityTwo,
                        @RequestParam(required = false, defaultValue = "") String consumable,
                        @RequestParam(required = false, defaultValue = "false") boolean fightTask,
                        @RequestParam(required = false, defaultValue = "false") boolean miningAll,
                        @RequestParam(required = false, defaultValue = "false") boolean woodcuttingAll,
                        @RequestParam(required = false, defaultValue = "false") boolean fishingAll,
                        @RequestParam(required = false, defaultValue = "false") boolean herbsAll) {
        launcher.runWarrior(action, activityLocation, utilityOne, utilityTwo, consumable, fightTask, miningAll, woodcuttingAll, fishingAll, herbsAll);
        return "redirect:/";
    }
    @PostMapping("/stopWarrior")
    public String stop() {
        launcher.stopWarrior();
        return "redirect:/";
    }
}
