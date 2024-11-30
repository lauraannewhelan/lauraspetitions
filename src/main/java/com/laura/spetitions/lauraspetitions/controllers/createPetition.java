package com.laura.spetitions.lauraspetitions.controllers;

import com.laura.spetitions.lauraspetitions.models.Petition;
import com.laura.spetitions.lauraspetitions.services.PetitionService;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

public class createPetition {

    @PostMapping("/create-petition")
    public String createPetition(@ModelAttribute Petition petition) {
        PetitionService petitionService = null;
        int id = petitionService.getAllPetitions().size() + 1;
        petitionService.addPetition(new Petition(id, petition.getTitle(), petition.getDescription()));
        return "redirect:/petitions";
    }

}
