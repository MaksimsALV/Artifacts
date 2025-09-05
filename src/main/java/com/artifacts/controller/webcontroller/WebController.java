package com.artifacts.controller.webcontroller;
import com.artifacts.game.endpoints.serverdetails.GetServerDetails;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.Map;

@Controller
public class WebController {

    @GetMapping("/")
    public String index(Model model) {
        HttpResponse<String> response = GetServerDetails.getServerDetails();
        model.addAttribute("statusCode", response.statusCode());
        return "index";
    }
}
