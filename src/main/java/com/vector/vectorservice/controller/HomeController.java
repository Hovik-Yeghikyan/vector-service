package com.vector.vectorservice.controller;

import com.vector.vectorservice.security.CurrentUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@Slf4j
@RequiredArgsConstructor
public class HomeController {


    @GetMapping("/")
    public String mainPage(@AuthenticationPrincipal CurrentUser currentUser, ModelMap modelMap) {
        modelMap.addAttribute("currentUser", currentUser);
        return "home";
    }

    @GetMapping("/loginPage")
    public String loginPage(@AuthenticationPrincipal CurrentUser currentUser,
                            ModelMap modelMap, @RequestParam(value = "msg", required = false) String msg) {
        if (msg != null && !msg.isEmpty()) {
            modelMap.addAttribute("msg", msg);
        }
        if (currentUser != null) {
            return "redirect:/";
        }
        return "loginPage";
    }

}
