package com.hectorpetersen.touristguideapi.service;


import com.hectorpetersen.touristguideapi.Validation.Validation;
import com.hectorpetersen.touristguideapi.exception.AttractionNotFoundException;
import com.hectorpetersen.touristguideapi.exception.DatabaseOperationException;
import com.hectorpetersen.touristguideapi.exception.DuplicateAttractionException;
import com.hectorpetersen.touristguideapi.model.TouristAttraction;
import com.hectorpetersen.touristguideapi.repository.TouristRepository;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TouristService {
    private final TouristRepository repository;
    private final Validation validator;

    public TouristService(TouristRepository repository, Validation validator) {
        this.repository = repository;
        this.validator = validator;
    }

    public List<TouristAttraction> getAllAttractions() {
        try {
            return repository.getAllAttractions();
        } catch (DataAccessException ex) {
            throw new DatabaseOperationException("Failed to get attractions.");
        }
    }

    public TouristAttraction findAttractionsByName(String name) {
        validator.validateName(name);
        return repository.findAttractionsByName(name);
    }

    public TouristAttraction createAttraction(TouristAttraction attraction) {
        validator.validateName(attraction.getName());
        if (repository.attractionExists(attraction.getName())) {
            throw new DuplicateAttractionException("Attraction already exist");
        }
        return repository.createNewAttraction(attraction);
    }

    public TouristAttraction deleteAttraction(String name) {
        return repository.deleteAttraction(name);
    }

    public TouristAttraction updateAttraction(TouristAttraction attraction) {
        if (!repository.attractionExists(attraction.getName())) {
            throw new AttractionNotFoundException("Attraction doesn't exist");
        }
        return repository.updateAttraction(attraction);
    }
}