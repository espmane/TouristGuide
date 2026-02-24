package com.hectorpetersen.touristguideapi.controller;

import com.hectorpetersen.touristguideapi.model.Tags;
import com.hectorpetersen.touristguideapi.model.TouristAttraction;
import com.hectorpetersen.touristguideapi.service.TouristService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TouristController.class)
class TouristControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private TouristService touristService;

    @Test
    void getAll() throws Exception {
        TouristAttraction attraction1 = new TouristAttraction("Tivoli", "Forlystelsespark", "København", List.of(Tags.UDENDØRS, Tags.OPLEVELSE, Tags.UNDERHOLDNING));
        TouristAttraction attraction2 = new TouristAttraction("Mash", "Spisested", "København", List.of(Tags.DYRT, Tags.OPLEVELSE));
        TouristAttraction attraction3 = new TouristAttraction("EK", "Ehvervesakademi", "København", List.of(Tags.BØRNEVENLIG, Tags.SKOLE, Tags.GRATIS));

        List<TouristAttraction> attractions = new ArrayList<>();

        attractions.add(attraction1);
        attractions.add(attraction2);
        attractions.add(attraction3);

        when(touristService.getAllAttractions()).thenReturn(attractions);

        mockMvc.perform(get("/attractions"))
                .andExpect(status().isOk())
                .andExpect(view().name("attractions-list"))
                .andExpect(model().attributeExists("attractions"))
                .andExpect(model().attribute("attractions", attractions));

    }

    @Test
    void getByName() {
    }

    @Test
    void addNewAttraction() {
    }

    @Test
    void updateAttraction() throws Exception {
        TouristAttraction attraction = new TouristAttraction("Tivoli", "Forlystelsespark", "København", List.of(Tags.UDENDØRS, Tags.OPLEVELSE, Tags.UNDERHOLDNING));

        when(touristService.updateAttraction(attraction)).thenReturn(attraction);

        mockMvc.perform(post("/update")
                .param("description", "Forlystelsespark")
                .param("city", "København")
                .param("tags", String.valueOf(List.of(Tags.UDENDØRS, Tags.OPLEVELSE, Tags.UNDERHOLDNING))))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/attractions/tivoli"));

    }

    @Test
    void deleteAttraction() {
    }

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }
}