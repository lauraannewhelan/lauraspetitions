package com.laura.spetitions.lauraspetitions.controllers;

import com.laura.spetitions.lauraspetitions.models.Petition;
import com.laura.spetitions.lauraspetitions.models.Signature;
import com.laura.spetitions.lauraspetitions.services.PetitionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class PetitionController {

    @Autowired
    private PetitionService petitionService;

    @GetMapping("/")
    public String home() {
        return "index";
    }

    @GetMapping("/create-petition")
    public String createPetitionForm(Model model) {
        model.addAttribute("petition", new Petition(0, "", ""));
        return "create-petition";
    }

    @PostMapping("/create-petition")
    public String createPetition(@ModelAttribute Petition petition) {
        int id = petitionService.getAllPetitions().size() + 1;
        petitionService.addPetition(new Petition(id, petition.getTitle(), petition.getDescription()));
        return "redirect:/petitions";
    }

    @GetMapping("/petitions")
    public String viewAllPetitions(Model model) {
        model.addAttribute("petitions", petitionService.getAllPetitions());
        return "view-petitions";
    }

    @GetMapping("/petition/{id}")
    public String viewPetition(@PathVariable int id, Model model) {
        Petition petition = petitionService.getPetitionById(id);
        if (petition != null) {
            model.addAttribute("petition", petition);
            return "view-petition";
        }
        return "redirect:/petitions";
    }

    @PostMapping("/petition/{id}/sign")
    public String signPetition(@PathVariable int id, @RequestParam String name, @RequestParam String email) {
        Petition petition = petitionService.getPetitionById(id);
        if (petition != null) {
            petition.addSignature(new Signature(name, email));
        }
        return "redirect:/petition/" + id;
    }

    @GetMapping("/search")
    public String searchForm() {
        return "search";
    }

    @PostMapping("/search")
    public String searchResults(@RequestParam String query, Model model) {
        model.addAttribute("results", petitionService.searchPetitions(query));
        return "search-results";
    }
}
