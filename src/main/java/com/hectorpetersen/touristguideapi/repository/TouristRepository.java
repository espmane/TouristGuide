package com.hectorpetersen.touristguideapi.repository;

import com.hectorpetersen.touristguideapi.model.Tags;
import com.hectorpetersen.touristguideapi.model.TouristAttraction;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class TouristRepository {

    private final List<TouristAttraction> attractions = new ArrayList<>();
    private final JdbcTemplate jdbcTemplate;

    public TouristRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private List<Tags> getTagsForAttraction(int attractionId) {
        final String sql = """
                SELECT t.Name
                        FROM Tags t
                        JOIN Attraction_tags at ON t.Tags_ID = at.Tags_ID
                        WHERE at.Attractions_id = ?
                """;
        return jdbcTemplate.query(sql,
                (rs, rowNum) -> Tags.valueOf(rs.getString("Name").toUpperCase()), attractionId);
    }

    public List<TouristAttraction> getAllAttractions() {
        final String sql = """
                SELECT a.Attractions_id AS ID,
                       a.Name,
                       a.Description,
                       c.Name AS City
                FROM Attractions a
                    JOIN City c ON a.City_ID = c.City_ID;
                """;

        return jdbcTemplate.query(sql, (rs, rowNum) -> (new TouristAttraction(
                rs.getInt("ID"),
                rs.getString("Name"),
                rs.getString("Description"),
                rs.getString("City"),
                getTagsForAttraction(rs.getInt("ID"))
        )));
    }

    public TouristAttraction findAttractionsByName(String name) {
        final String sql = """
                SELECT a.Attractions_id AS ID,
                       a.Name           AS Name,
                       a.Description    AS Description,
                       c.Name           AS CityName
                FROM Attractions a
                         JOIN City c ON a.City_ID = c.City_ID
                WHERE a.Name = ?
                """;

        return jdbcTemplate.queryForObject(sql, (rs, rowNum) -> new TouristAttraction(
                rs.getInt("ID"),
                rs.getString("Name"),
                rs.getString("Description"),
                rs.getString("CityName"),
                getTagsForAttraction(rs.getInt("ID"))), name
        );
    }

    /*public TouristAttraction createNewAttraction(TouristAttraction attraction) {
        if (findAttractionsByName(attraction.getName()) != null) {
            return null;
        }
        attraction.setAttractionId(1);
        attractions.add(attraction);
        return attraction;
    }
    */

    public TouristAttraction createNewAttraction(TouristAttraction attraction) {

        try {
            TouristAttraction existing = findAttractionsByName(attraction.getName());
            if (existing != null) {
                return null;
            }
        } catch (Exception ignored) {

        }

        final String insertSql = """
            
                INSERT INTO Attractions (Name, Description, City_ID)
                            VALUES (?, ?, (SELECT City_ID FROM City WHERE Name = ?))
            """;

        jdbcTemplate.update(insertSql,
                attraction.getName(),
                attraction.getDescription(),
                attraction.getCity()
        );

        final String idSql = "SELECT Attractions_id FROM Attractions WHERE Name = ?";
        Integer attractionId = jdbcTemplate.queryForObject(idSql, Integer.class, attraction.getName());

        final String tagSql =
                        """
            INSERT INTO Attractio
                                    Attractions_id, 
                                VALUES (?, (SELECT
                                    FROM Tags WHERE Na
                                """;

        for (Tags tag : attraction.getTags()) {
            jdbcTemplate.update(tagSql, attractionId, tag.name());
        }

        return new TouristAttraction(
                attractionId,
                attraction.getName(),
                attraction.getDescription(),
                attraction.getCity(),
                attraction.getTags()
        );
    }

    public TouristAttraction deleteAttraction(String name) {
        TouristAttraction deleteAttraction = findAttractionsByName(name);

        if (deleteAttraction == null){
            return null;
        }

        jdbcTemplate.update(
                "DELETE FROM Attraction_tags WHERE attraction_id = ?"
        );

        jdbcTemplate.update(
                "DELETE FROM Attractions WHERE attraction_id = ?"
        );

        return deleteAttraction;
    }

    public TouristAttraction updateAttraction(TouristAttraction touristAttraction) {
        //Opdater attraktionens "descr" og "city" baseret på navnet
        String updateSql = "UPDATE Attractions SET Description = ?, City_ID = (SELECT City_ID FROM City WHERE Name = ?) WHERE Name = ?";
        int rows = jdbcTemplate.update(updateSql,
                touristAttraction.getDescription(),
                touristAttraction.getCity(),
                touristAttraction.getName()
        );

        //hvis ingen rækker bliver opdateret stopper den her1
        if (rows == 0) {
            return 0;
        }

        //sletter tags for attraktionen via name
        String deleteTagsSql = "DELETE FROM Attraction_tags WHERE Attractions_id = (SELECT Attractions_id FROM Attractions WHERE Name = ?)";
        jdbcTemplate.update(deleteTagsSql, touristAttraction.getName());

        //nye tags
        String insertTagSql = "INSERT INTO Attraction_tags (Attractions_id, Tags_ID) VALUES ((SELECT Attractions_id FROM Attractions WHERE Name = ?), (SELECT Tags_ID FROM Tags WHERE Name = ?))";
        if (touristAttraction.getTags() != null) {
            for (Tags tag : touristAttraction.getTags()) {
                jdbcTemplate.update(insertTagSql, touristAttraction.getName(), tag.name());
            }
        }
        return
    }
    }