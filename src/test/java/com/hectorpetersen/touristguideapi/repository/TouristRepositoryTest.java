package com.hectorpetersen.touristguideapi.repository;

import com.hectorpetersen.touristguideapi.model.Cities;
import com.hectorpetersen.touristguideapi.model.Tags;
import com.hectorpetersen.touristguideapi.model.TouristAttraction;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;

@SpringBootTest
@ActiveProfiles("test")
@Sql(scripts = "classpath:h2init.sql", executionPhase = BEFORE_TEST_METHOD)
class TouristRepositoryTest {

    @Autowired
    private TouristRepository repo;

    @Test
    void getAllAttractions() {
        List<TouristAttraction> all = repo.getAllAttractions();

        assertThat(all.size()).isEqualTo(4);

        TouristAttraction ek = all.get(0);
        assertThat(ek.getName()).isEqualTo("EK");
        assertThat(ek.getDescription()).isEqualTo("Erhvervsakademi");
        assertThat(ek.getCity().toUpperCase()).isEqualTo(Cities.KØBENHAVN.name());
        assertThat(ek.getTags()).containsExactlyInAnyOrder(Tags.SKOLE, Tags.GRATIS, Tags.BØRNEVENLIG);

        TouristAttraction mash = all.get(1);
        assertThat(mash.getName()).isEqualTo("Mash");
        assertThat(mash.getDescription()).isEqualTo("Spisested");
        assertThat(mash.getCity().toUpperCase()).isEqualTo(Cities.KØBENHAVN.name());
        assertThat(mash.getTags()).containsExactlyInAnyOrder(Tags.INDENDØRS, Tags.OPLEVELSE, Tags.DYRT);

        TouristAttraction tivoli = all.get(2);
        assertThat(tivoli.getName()).isEqualTo("Tivoli");
        assertThat(tivoli.getDescription()).isEqualTo("Forlystelsespark");
        assertThat(tivoli.getCity().toUpperCase()).isEqualTo(Cities.KØBENHAVN.name());
        assertThat(tivoli.getTags()).containsExactlyInAnyOrder(Tags.OPLEVELSE, Tags.UNDERHOLDNING, Tags.UDENDØRS);

        TouristAttraction hornbæk = all.get(3);
        assertThat(hornbæk.getName()).isEqualTo("Hornbæk Skole");
        assertThat(hornbæk.getDescription()).isEqualTo("Ikke den i Randers");
        assertThat(hornbæk.getCity().toUpperCase()).isEqualTo(Cities.HORNBÆK.name());
        assertThat(hornbæk.getTags()).containsExactlyInAnyOrder(Tags.DYRELIV);
    }

    @Test
    void getAttractionByName() {
        final var name = "Hornbæk Skole";
        var attraction = repo.findAttractionsByName(name);

        assertThat(attraction.getName().equals(name));
        assertThat(attraction.getDescription()).isEqualTo("Ikke den i Randers");
        assertThat(attraction.getCity().equals(Cities.HORNBÆK.getDisplayName()));
        assertThat(attraction.getTags()).containsExactlyInAnyOrder(Tags.DYRELIV);
    }

    @Test
    void createNewAttraction() {
    }

    @Test
    void deleteAttraction() {
    }

    @Test
    void updateAttraction() {
    }
}