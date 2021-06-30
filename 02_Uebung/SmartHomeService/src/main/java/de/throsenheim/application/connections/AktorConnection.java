package de.throsenheim.application.connections;

import de.throsenheim.domain.models.Aktor;
import de.throsenheim.persistence.repositories.AktorsRepository;
import de.throsenheim.presentation.controller.exceptions.IdAlreadyExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Connecting the frontend of Aktor with the repositories
 */
@Service
public class AktorConnection {

    @Autowired
    AktorsRepository aktorsRepository;


    /**
     * Get an Aktor with a certain ID
     * @param id the Id of the Aktor you want to get
     * @return The Aktor with this specific ID
     */
    public Aktor getAktor(Integer id){
        var aktorOpt = aktorsRepository.findById(id);

        return aktorOpt.orElse(null);

    }


    /**
     * Gives back all the Aktors in the database
     * @return A list of all the Aktors in the database
     */
    public List<Aktor> getAktorList(){
        List<Aktor> result = new ArrayList<>();

        aktorsRepository.findAll().forEach(result::add);

        return result;
    }


    /**
     * Saves an Aktor in the database
     * @param aktor Aktor to be saved
     * @return the Aktor that is saved
     * @throws IdAlreadyExistsException If an Aktor with this ID already exists
     */
    public Aktor save(Aktor aktor) throws IdAlreadyExistsException{
        if(aktorsRepository.existsById(aktor.getId())) throw new IdAlreadyExistsException();

        aktor.setDate(new Date());
        aktor.setStatus("closed");
        aktorsRepository.save(aktor);

        return aktor;
    }
}
