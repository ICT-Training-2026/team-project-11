package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AtMenuController {

    @GetMapping("/AtMenu")
    public String showAtMenu() {
        return "AtMenu"; // ← templates/AtMenu.html に対応
    }
}
