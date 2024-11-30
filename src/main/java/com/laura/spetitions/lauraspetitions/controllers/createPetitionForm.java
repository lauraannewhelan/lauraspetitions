package com.laura.spetitions.lauraspetitions.controllers;

import ch.qos.logback.core.model.Model;
import com.laura.spetitions.lauraspetitions.models.Petition;
import org.springframework.web.bind.annotation.GetMapping;

public class createPetitionForm {
    @GetMapping("/create-petition")
    public String createPetitionForm(Model model) {
        model.addText("petition");
        return "create-petition";
    }

}
