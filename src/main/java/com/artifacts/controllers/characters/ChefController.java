package com.artifacts.controllers.characters;

import com.artifacts.game.Launcher;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

//import static com.artifacts.game.Launcher.runChef;
//import static com.artifacts.game.Launcher.stopChef;

@Controller
public class ChefController {
    private final Launcher launcher;

    public ChefController(Launcher launcher) {
        this.launcher = launcher;
    }

    @PostMapping("/runChef")
    public String start(@RequestParam String action,
                        @RequestParam(required = false, defaultValue = "") String activityLocation,
                        @RequestParam(required = false, defaultValue = "") String utilityOne,
                        @RequestParam(required = false, defaultValue = "") String utilityTwo,
                        @RequestParam(required = false, defaultValue = "") String consumable,
                        @RequestParam(required = false, defaultValue = "false") boolean fightTask) {
        launcher.runChef(action, activityLocation, utilityOne, utilityTwo, consumable, fightTask);
        return "redirect:/";
    }
    @PostMapping("/stopChef")
    public String stop() {
        launcher.stopChef();
        return "redirect:/";
    }
}
