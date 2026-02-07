package com.artifacts.controllers.characters;

import com.artifacts.game.Launcher;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

//import static com.artifacts.game.Launcher.runMiner;
//import static com.artifacts.game.Launcher.stopMiner;

@Controller
public class MinerController {
    private final Launcher launcher;

    public MinerController(Launcher launcher) {
        this.launcher = launcher;
    }

    @PostMapping("/runMiner")
    public String start(@RequestParam String action,
                        @RequestParam(required = false, defaultValue = "") String activityLocation,
                        @RequestParam(required = false, defaultValue = "") String utilityOne,
                        @RequestParam(required = false, defaultValue = "") String utilityTwo,
                        @RequestParam(required = false, defaultValue = "") String consumable,
                        @RequestParam(required = false, defaultValue = "false") boolean fightTask,
                        @RequestParam(required = false, defaultValue = "false") boolean miningAll) {
        launcher.runMiner(action, activityLocation, utilityOne, utilityTwo, consumable, fightTask, miningAll);
        return "redirect:/";
    }
    @PostMapping("/stopMiner")
    public String stop() {
        launcher.stopMiner();
        return "redirect:/";
    }
}
