package com.artifacts.controller.webcontroller;
//import com.artifacts.game.logic.activity.fighting.Fighting;
import com.artifacts.game.endpoints.resources.GetAllResources;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.List;
import org.json.JSONObject;

import static com.artifacts.game.endpoints.resources.GetAllResources.getAllResourcesAsList;

@Controller
public class WebController {
    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("resources", getAllResourcesAsList());
        return "index";
    }
}

//@Controller
//public class WebController {
//    private volatile Thread thread1;
//    private volatile Thread thread2;
//    private volatile Thread thread3;
//    private volatile Thread thread4;
//    private volatile Thread thread5;
//
//    @GetMapping("/")
//    public String indexGET(Model model) {
//        HttpResponse<String> serverStatusStringResponse = GetServerDetails.getServerDetails();
//        model.addAttribute("statusCode", serverStatusStringResponse.statusCode());
//
//        var response = GetAllMonsters.getAllMonsters();
//        List<String> monsterCode = new ArrayList<>();
//        var dataArray = response.getJSONArray("data");
//        for (int i = 0; i < dataArray.length(); i++) {
//            monsterCode.add(dataArray.getJSONObject(i).getString("code"));
//        }
//        model.addAttribute("monsterCode", monsterCode);
//
//        response = GetAllResources.getAllResources();
//        List<String> resourceCode = new ArrayList<>();
//        dataArray = response.getJSONArray("data");
//        for (int i = 0; i < dataArray.length(); i++) {
//            resourceCode.add(dataArray.getJSONObject(i).getString("code"));
//        }
//        model.addAttribute("resourceCode", resourceCode);
//
//        model.addAttribute("characterNames", List.of(
//                getWarrior(),
//                getMiner(),
//                getLumberjack(),
//                getChef(),
//                getAlchemist()
//        ));
//        return "index";
//    }
//
//    @PostMapping("/")
//    public String indexPOST(
//            @RequestParam(value = "slot") int slot,
//            @RequestParam(value = "characterName", required = false) String character,
//            @RequestParam(value = "monsterCode", required = false) String monster,
//            @RequestParam(value = "resourceCode", required = false) String resource,
//            @RequestParam(value = "action", required = false) String action) {
//
//        var hasMonsterSelected = monster != null && !monster.isBlank();
//        var hasResourceSelected = resource != null && !resource.isBlank();
//
//        String threadName = nameForSlot(slot);
//
//        if ("start".equals(action)) {
//            if (hasMonsterSelected == hasResourceSelected) {
//                return "redirect:/";
//            }
//
//            Runnable r = () -> {
//                try {
//                    if (hasMonsterSelected) {
//                        Fighting2.fight(character, monster);
//                    } else {
//                        Gathering.gather(character, resource);
//                    }
//                } catch (InterruptedException e) {
//                    Thread.currentThread().interrupt();
//                }
//            };
//
//            Thread t = new Thread(r, threadName);
//            t.setDaemon(true);
//            t.start();
//
//            if (slot == 1) {
//                thread1 = new Thread(() -> {
//                    try {
//                        if (hasMonsterSelected) {
//                            Fighting.fight(character, monster);
//                        } else {
//                            Gathering.gather(character, resource);
//                        }
//                    } catch (InterruptedException e) {
//                        Thread.currentThread().interrupt();
//                    }
//                });
//                thread1.setDaemon(true);
//                thread1.start();
//
//            } else if (slot == 2) {
//                thread2 = new Thread(() -> {
//                    try {
//                        Gathering.gather(character, resource);
//                    } catch (InterruptedException threadException) {
//                        Thread.currentThread().interrupt();
//                    }
//                });
//                thread2.setDaemon(true);
//                thread2.start();
//
//            } else if (slot == 3) {
//                thread3 = new Thread(() -> {
//                    try {
//                        Gathering.gather(character, resource);
//                    } catch (InterruptedException threadException) {
//                        Thread.currentThread().interrupt();
//                    }
//                });
//                thread3.setDaemon(true);
//                thread3.start();
//
//            } else if (slot == 4) {
//                thread4 = new Thread(() -> {
//                    try {
//                        Gathering.gather(character, resource);
//                    } catch (InterruptedException threadException) {
//                        Thread.currentThread().interrupt();
//                    }
//                });
//                thread4.setDaemon(true);
//                thread4.start();
//
//            } else if (slot == 5) {
//                thread5 = new Thread(() -> {
//                    try {
//                        Gathering.gather(character, resource);
//                    } catch (InterruptedException threadException) {
//                        Thread.currentThread().interrupt();
//                    }
//                });
//                thread5.setDaemon(true);
//                thread5.start();
//            }
//
//        } else if ("kill".equals(action)) {
//            // interrupt first matching thread by name
//            for (Thread t : Thread.getAllStackTraces().keySet()) {
//                if (threadName.equals(t.getName())) {
//                    t.interrupt();
//                    break;
//                }
//            }
//        }
//
//        } else if ("kill".equals(action)) {
//            if (slot == 1 && thread1 != null) {
//                System.out.println("Thread killed");
//                thread1.interrupt();
//            }
//            if (slot == 2 && thread2 != null) {
//                System.out.println("Thread killed");
//                thread2.interrupt();
//            }
//            if (slot == 3 && thread3 != null) {
//                System.out.println("Thread killed");
//                thread3.interrupt();
//            }
//            if (slot == 4 && thread4 != null) {
//                System.out.println("Thread killed");
//                thread4.interrupt();
//            }
//            if (slot == 5 && thread5 != null) {
//                System.out.println("Thread killed");
//                thread5.interrupt();
//            }
//        }
//        return "redirect:/";
//    }
//    private String nameForSlot(int slot) {
//        switch (slot) {
//            case 1:  return "warrior";
//            case 2:  return "miner";
//            case 3:  return "alchemist";
//            case 4:  return "lumberjack";
//            case 5:  return "chef";
//            default: throw new IllegalArgumentException("Invalid slot: " + slot);
//        }
//    }
//}


