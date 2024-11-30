package com.laura.spetitions.lauraspetitions.controllers;

import ch.qos.logback.core.model.Model;
import com.laura.spetitions.lauraspetitions.services.PetitionService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

public class searchResults {

    @PostMapping("/search")
    public String searchResults(@RequestParam String query, Model model) {
        PetitionService petitionService = null;
        model.addText("results");
        return "search-results";
    }

}
