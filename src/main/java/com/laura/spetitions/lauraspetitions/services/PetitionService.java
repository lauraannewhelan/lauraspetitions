package com.laura.spetitions.lauraspetitions.services;

import com.laura.spetitions.lauraspetitions.models.Petition;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PetitionService {
    private final List<Petition> petitions = new ArrayList<>();

    public PetitionService() {
        petitions.add(new Petition(1, "Save the Environment", "Join us to protect nature and wildlife."));
        petitions.add(new Petition(2, "Support Education Funding", "Let's increase resources for schools."));
    }

    public List<Petition> getAllPetitions() {
        return petitions;
    }

    public Petition getPetitionById(int id) {
        return petitions.stream().filter(p -> p.getId() == id).findFirst().orElse(null);
    }

    public void addPetition(Petition petition) {
        petitions.add(petition);
    }

    public List<Petition> searchPetitions(String query) {
        query = query.toLowerCase();
        List<Petition> results = new ArrayList<>();
        for (Petition petition : petitions) {
            if (petition.getTitle().toLowerCase().contains(query) || petition.getDescription().toLowerCase().contains(query)) {
                results.add(petition);
            }
        }
        return results;
    }
}
