package com.laura.spetitions.lauraspetitions.controllers;

import org.springframework.web.bind.annotation.GetMapping;

public class searchForm {

    @GetMapping("/search")
    public String searchForm() {
        return "search";
    }

}
