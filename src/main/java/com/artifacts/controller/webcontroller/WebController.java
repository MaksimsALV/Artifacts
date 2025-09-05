package com.artifacts.controller.webcontroller;
import com.artifacts.game.endpoints.maps.GetAllMaps;
import com.artifacts.game.endpoints.monsters.GetAllMonsters;
import com.artifacts.game.endpoints.resources.GetAllResources;
import com.artifacts.game.endpoints.serverdetails.GetServerDetails;
import com.artifacts.game.library.characters.Characters;
import com.artifacts.game.logic.activity.fighting.Fighting;
import com.artifacts.game.logic.activity.gathering.Gathering;
import jakarta.servlet.http.HttpServletRequest;
import org.json.JSONObject;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.artifacts.game.library.characters.Characters.*;

@Controller
public class WebController {
    private Thread thread1;
    private Thread thread2;
    private Thread thread3;
    private Thread thread4;
    private Thread thread5;

    @GetMapping("/")
    public String indexGET(Model model) {
        HttpResponse<String> serverStatusStringResponse = GetServerDetails.getServerDetails();
        model.addAttribute("statusCode", serverStatusStringResponse.statusCode());

        var response = GetAllMonsters.getAllMonsters();
        List<String> monsterCode = new ArrayList<>();
        var dataArray = response.getJSONArray("data");
        for (int i = 0; i < dataArray.length(); i++) {
            monsterCode.add(dataArray.getJSONObject(i).getString("code"));
        }
        model.addAttribute("monsterCode", monsterCode);

        response = GetAllResources.getAllResources();
        List<String> resourceCode = new ArrayList<>();
        dataArray = response.getJSONArray("data");
        for (int i = 0; i < dataArray.length(); i++) {
            resourceCode.add(dataArray.getJSONObject(i).getString("code"));
        }
        model.addAttribute("resourceCode", resourceCode);

        model.addAttribute("characterNames", List.of(
                getWarrior(),
                getMiner(),
                getLumberjack(),
                getChef(),
                getAlchemist()
        ));
        return "index";
    }

    @PostMapping("/")
    public String indexPOST(
            @RequestParam(value = "slot") int slot,
            @RequestParam(value = "characterName", required = false) String character,
            @RequestParam(value = "monsterCode", required = false) String monster,
            @RequestParam(value = "resourceCode", required = false) String resource,
            @RequestParam(value = "action", required = false) String action) {

        var hasMonsterSelected = monster != null && !monster.isBlank();
        var hasResourceSelected = resource != null && !resource.isBlank();


        if ("start".equals(action)) {
            if (hasMonsterSelected == hasResourceSelected) {
                return "redirect:/";
            }

            if (slot == 1) {
                thread1 = new Thread(() -> {
                    try {
                        if (hasMonsterSelected) {
                            Fighting.fight(character, monster);
                        } else {
                            Gathering.gather(character, resource);
                        }
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                });
                thread1.setDaemon(true);
                thread1.start();

            } else if (slot == 2) {
                thread2 = new Thread(() -> {
                    try {
                        Gathering.gather(character, resource);
                    } catch (InterruptedException threadException) {
                        Thread.currentThread().interrupt();
                    }
                });
                thread2.setDaemon(true);
                thread2.start();

            } else if (slot == 3) {
                thread3 = new Thread(() -> {
                    try {
                        Gathering.gather(character, resource);
                    } catch (InterruptedException threadException) {
                        Thread.currentThread().interrupt();
                    }
                });
                thread3.setDaemon(true);
                thread3.start();

            } else if (slot == 4) {
                thread4 = new Thread(() -> {
                    try {
                        Gathering.gather(character, resource);
                    } catch (InterruptedException threadException) {
                        Thread.currentThread().interrupt();
                    }
                });
                thread4.setDaemon(true);
                thread4.start();

            } else if (slot == 5) {
                thread5 = new Thread(() -> {
                    try {
                        Gathering.gather(character, resource);
                    } catch (InterruptedException threadException) {
                        Thread.currentThread().interrupt();
                    }
                });
                thread5.setDaemon(true);
                thread5.start();
            }
        } else if ("kill".equals(action)) {
            if (slot == 1 && thread1 != null) {
                System.out.println("Thread killed");
                thread1.interrupt();
            }
            if (slot == 2 && thread2 != null) {
                System.out.println("Thread killed");
                thread2.interrupt();
            }
            if (slot == 3 && thread3 != null) {
                System.out.println("Thread killed");
                thread3.interrupt();
            }
            if (slot == 4 && thread4 != null) {
                System.out.println("Thread killed");
                thread4.interrupt();
            }
            if (slot == 5 && thread5 != null) {
                System.out.println("Thread killed");
                thread5.interrupt();
            }
        }
        return "redirect:/";
    }
}
