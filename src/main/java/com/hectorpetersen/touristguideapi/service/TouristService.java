package com.hectorpetersen.touristguideapi.service;


import com.hectorpetersen.touristguideapi.model.TouristAttraction;
import com.hectorpetersen.touristguideapi.repository.TouristRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TouristService {
    private final TouristRepository repository;

    TouristService(TouristRepository repository) {
        this.repository = repository;
    }

    public List<TouristAttraction> getAllAttractions() {
        return repository.getAllAttractions();
    }

    public TouristAttraction findAttractionsByName(String name) {
        TouristAttraction touristAttraction = repository.findAttractionsByName(name);

        if (name != null && name.equals(touristAttraction.getName())) {
            return touristAttraction;
        }
        return null;
    }

    public TouristAttraction createAttraction(TouristAttraction attraction) {
        return repository.createNewAttraction(attraction);
    }

    public void deleteAttraction(String name) {
        repository.deleteAttraction(name);
    }

    public void updateAttraction(String name, String description) {
        repository.updateAttraction(name, description);
    }


}
//aa