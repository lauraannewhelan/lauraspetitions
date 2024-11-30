package com.laura.spetitions.lauraspetitions.controllers;

import com.laura.spetitions.lauraspetitions.models.Petition;
import com.laura.spetitions.lauraspetitions.models.Signature;
import com.laura.spetitions.lauraspetitions.services.PetitionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
class petitionController {

    @Autowired
    private PetitionService petitionService;

    // Home Page
    @GetMapping("/")
    public String home() {
        return "index";
    }

    // Display Form to Create a Petition
    @GetMapping("/create-petition")
    public String createPetitionForm(Model model) {
        model.addAttribute("petition", new Petition());
        return "create-petition";
    }

    // Handle Petition Creation
    @PostMapping("/create-petition")
    public String createPetition(@ModelAttribute Petition petition) {
        // Generate unique id for the petition
        int id = petitionService.getAllPetitions().size() + 1;
        petition.setId(id); // Set the id before adding it to the service
        petitionService.addPetition(petition);
        return "redirect:/petitions";
    }

    // View All Petitions
    @GetMapping("/petitions")
    public String viewAllPetitions(Model model) {
        model.addAttribute("petitions", petitionService.getAllPetitions());
        return "view-petitions";
    }

    // View a Specific Petition
    @GetMapping("/petition/{id}")
    public String viewPetition(@PathVariable int id, Model model) {
        Petition petition = petitionService.getPetitionById(id);
        if (petition != null) {
            model.addAttribute("petition", petition);
            return "view-petition";
        }
        return "redirect:/petitions"; // Redirect if petition not found
    }

    // Sign a Petition
    @PostMapping("/petition/{id}/sign")
    public String signPetition(@PathVariable int id, @RequestParam String name, @RequestParam String email) {
        Petition petition = petitionService.getPetitionById(id);
        if (petition != null) {
            petition.addSignature(new Signature(name, email));
        }
        return "redirect:/petition/" + id; // Redirect back to petition view
    }

    // Display Search Form
    @GetMapping("/search")
    public String searchForm() {
        return "search";
    }

    // Display Search Results
    @PostMapping("/search")
    public String searchResults(@RequestParam String query, Model model) {
        model.addAttribute("results", petitionService.searchPetitions(query));
        return "search-results";
    }
}
