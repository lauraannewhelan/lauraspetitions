package com.laura.spetitions.lauraspetitions.controllers;

import ch.qos.logback.core.model.Model;
import com.laura.spetitions.lauraspetitions.services.PetitionService;
import org.springframework.web.bind.annotation.GetMapping;

public class viewAllPetitions {

    @GetMapping("/petitions")
    public String viewAllPetitions(Model model) {
        PetitionService petitionService = null;
        model.addText("petitions");
        return "view-petitions";
    }

}
