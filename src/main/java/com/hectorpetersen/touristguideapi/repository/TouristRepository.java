package com.hectorpetersen.touristguideapi.repository;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.hectorpetersen.touristguideapi.model.TouristAttraction;

@Repository
public class TouristRepository {

    private final JdbcTemplate jdbcTemplate;

    public TouristRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<TouristAttraction> getAllAttractions() {
        // final String tagSQL = """
        // SELECT a.Name, t.Name
        // FROM Attractions a
        // JOIN Attraction_tags at ON a.Attractions_id = at.Attractions_id
        // JOIN Tags t ON at.Tags_ID = t.Tags_ID""";
        // jdbcTemplate.query(tagSQL, rs -> rs.getString(0) + rs.getString(1));

        // Tags er ikke med
        final String attractionSQL = """
                SELECT a.Attractions_id AS ID,
                       a.Name AS Name,
                       a.Description AS Description,
                       c.Name AS City
                FROM Attractions a
                JOIN City c ON a.City_ID = c.City_ID;""";

        return jdbcTemplate.query(attractionSQL, (rs, rowNum) -> (new TouristAttraction(
                rs.getInt("ID"),
                rs.getString("Name"),
                rs.getString("Description"),
                rs.getString("City"))));
    }

    public TouristAttraction findAttractionsByName(String name) {
        return null;
    }

    public TouristAttraction createNewAttraction(TouristAttraction attraction) {
        return null;
    }

    public TouristAttraction deleteAttraction(String name) {
        return null;
    }

    public TouristAttraction updateAttraction(TouristAttraction touristAttraction) {
        return null;
    }
}
