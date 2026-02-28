package com.hectorpetersen.touristguideapi.repository;

import com.hectorpetersen.touristguideapi.model.Cities;
import com.hectorpetersen.touristguideapi.model.Tags;
import com.hectorpetersen.touristguideapi.model.TouristAttraction;
import org.springframework.stereotype.Repository;
import org.springframework.util.LinkedCaseInsensitiveMap;

import java.util.List;
import java.util.Map;

@Repository
public class TouristRepository {

    private final Map<String, TouristAttraction> attractions = new LinkedCaseInsensitiveMap<>();
    private int nextId = 1;

    public TouristRepository() {
        populate();
        attractions.forEach((String, TouristAttraction) -> System.out.println(TouristAttraction));
    }

    private void populate() {
        attractions.put("EK", new TouristAttraction("EK", "Ehvervesakademi", Cities.KØBENHAVN, List.of(Tags.BØRNEVENLIG, Tags.SKOLE, Tags.GRATIS)));
        attractions.put("Mash", new TouristAttraction("Mash", "Spisested", Cities.KØBENHAVN, List.of(Tags.DYRT, Tags.OPLEVELSE)));
        attractions.put("Tivoli", new TouristAttraction("Tivoli", "Forlystelsespark", Cities.KØBENHAVN, List.of(Tags.UDENDØRS, Tags.OPLEVELSE, Tags.UNDERHOLDNING)));
    }

    public List<TouristAttraction> getAllAttractions() {
        return attractions.values().stream().toList();
    }

    public TouristAttraction getAttractionsByName(String name) {
        return attractions.get(name);
    }

    public TouristAttraction createNewAttraction(TouristAttraction attraction) {
        attraction.setAttractionId(nextId++);
        attractions.put(attraction.getName(), attraction);
        return attraction;
    }

    public TouristAttraction deleteAttraction(String name) {
        return attractions.remove(name);
    }

    public TouristAttraction updateAttraction(TouristAttraction touristAttraction) {
        attractions.put(touristAttraction.getName(), touristAttraction);
        return touristAttraction;
    }
}