package com.hectorpetersen.touristguideapi.service;


import com.hectorpetersen.touristguideapi.Validation.Validation;
import com.hectorpetersen.touristguideapi.exception.DatabaseOperationException;
import com.hectorpetersen.touristguideapi.model.TouristAttraction;
import com.hectorpetersen.touristguideapi.repository.TouristRepository;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TouristService {
    private final TouristRepository repository;

    TouristService(TouristRepository repository) {
        this.repository = repository;
    }

    public List<TouristAttraction> getAllAttractions() {
        try {
            return repository.getAllAttractions();
        } catch (DataAccessException ex) {
            throw new DatabaseOperationException("Failed to get profiles.");
        }
    }

    public TouristAttraction findAttractionsByName(String name) {
        Validation.ValidateName(name);
        return repository.findAttractionsByName(name);
    }

    public TouristAttraction createAttraction(TouristAttraction attraction) {
        return repository.createNewAttraction(attraction);
    }

    public TouristAttraction deleteAttraction(String name) {
        return repository.deleteAttraction(name);
    }

    public TouristAttraction updateAttraction(TouristAttraction attraction) {
        return repository.updateAttraction(attraction);
    }
}