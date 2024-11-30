package com.laura.spetitions.lauraspetitions.controllers;

import com.laura.spetitions.lauraspetitions.models.Petition;
import com.laura.spetitions.lauraspetitions.services.PetitionService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.*;

public class signPetition {

    @PostMapping("/petition/{id}/sign")
    public String signPetition(@PathVariable int id, @RequestParam String name, @RequestParam String email) {
        PetitionService petitionService = null;
        Petition petition = petitionService.getPetitionById(id);
        if (petition != null) {
            petition.addSignature(new Signature(name) {
                @Override
                protected void engineInitVerify(PublicKey publicKey) throws InvalidKeyException {

                }

                @Override
                protected void engineInitSign(PrivateKey privateKey) throws InvalidKeyException {

                }

                @Override
                protected void engineUpdate(byte b) throws SignatureException {

                }

                @Override
                protected void engineUpdate(byte[] b, int off, int len) throws SignatureException {

                }

                @Override
                protected byte[] engineSign() throws SignatureException {
                    return new byte[0];
                }

                @Override
                protected boolean engineVerify(byte[] sigBytes) throws SignatureException {
                    return false;
                }

                @Override
                protected void engineSetParameter(String param, Object value) throws InvalidParameterException {

                }

                @Override
                protected Object engineGetParameter(String param) throws InvalidParameterException {
                    return null;
                }
            });
        }
        return "redirect:/petition/" + id;
    }

}
