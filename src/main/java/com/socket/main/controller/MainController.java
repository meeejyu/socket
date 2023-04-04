package com.socket.main.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.socket.main.service.MainService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class MainController {
    
    private final MainService mainService;

    @GetMapping("/main")
    public String chat() {

        return "chat";
    }
}
