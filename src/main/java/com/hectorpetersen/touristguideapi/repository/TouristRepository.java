package com.hectorpetersen.touristguideapi.repository;
import com.hectorpetersen.touristguideapi.model.TouristAttraction;
import org.springframework.stereotype.Repository;
import java.util.*;

@Repository
public class TouristRepository {

    private final List<TouristAttraction> attractions = new ArrayList<>();
    private String attractions =;

    public TouristAttractionRepository() { populateAttractions();}

    private void populateAttractions(){
    while (attractionId <= 5) {
        attractions.add(new TouristAttraction(attractionId, "tivoli er god", +))
    }
    }

    //CreateTouristAttraction

    //DeleteTouristAtrraction

    //UpdateTouristAttraction

}
