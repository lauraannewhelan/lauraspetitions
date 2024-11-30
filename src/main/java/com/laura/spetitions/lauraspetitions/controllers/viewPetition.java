package com.laura.spetitions.lauraspetitions.controllers;

import ch.qos.logback.core.model.Model;
import com.laura.spetitions.lauraspetitions.models.Petition;
import com.laura.spetitions.lauraspetitions.services.PetitionService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

public class viewPetition {

    @GetMapping("/petition/{id}")
    public String viewPetition(@PathVariable int id, Model model) {
        PetitionService petitionService = null;
        Petition petition = petitionService.getPetitionById(id);
        if (petition != null) {
            model.addText("petition");
            return "view-petition";
        }
        return "redirect:/petitions";
    }

}
