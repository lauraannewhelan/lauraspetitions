package com.laura.spetitions.lauraspetitions.controllers;

import org.springframework.web.bind.annotation.GetMapping;

public class home {

    @GetMapping("/")
    public String home() {
        return "index";
    }

}
