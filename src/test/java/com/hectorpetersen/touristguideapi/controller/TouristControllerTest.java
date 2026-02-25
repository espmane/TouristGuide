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

import org.mockito.Mock;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;

@WebMvcTest(TouristController.class)
class TouristControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private TouristService touristService;

    @Test
    void getAll() throws Exception {
        TouristAttraction attraction1 = new TouristAttraction("Tivoli", "Udendørs forlystelsespark", "København", List.of(Tags.BØRNEVENLIG, Tags.SKOLE, Tags.GRATIS) );
        TouristAttraction attraction2 = new TouristAttraction("Mash", "Spisested", "København", List.of(Tags.DYRT, Tags.OPLEVELSE));
        TouristAttraction attraction3 = new TouristAttraction("Tivoli", "Forlystelsespark", "København", List.of(Tags.UDENDØRS, Tags.OPLEVELSE, Tags.UNDERHOLDNING));

        List<TouristAttraction> attractions = new ArrayList<>();

        attractions.add(attraction1);
        attractions.add(attraction2);
        attractions.add(attraction3);

        when(touristService.getAllAttractions()).thenReturn(attractions);

        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("attractions-list"))
                .andExpect(model().attributeExists("attractions"))
                .andExpect(model().attribute("attractions", attractions));

    }

    @Test
    void getByName() throws Exception{
        String name = "Tivoli";
        TouristAttraction attraction = new TouristAttraction("Tivoli", "Udendørs forlystelsespark", "København", List.of(Tags.BØRNEVENLIG, Tags.SKOLE, Tags.GRATIS) );

        when(touristService.findAttractionsByName(name)).thenReturn(attraction);

        mockMvc.perform(get("/attractions/{name}", name))
                .andExpect(status().isOk())
                .andExpect(view().name("attraction"))
                .andExpect(model().attributeExists("attraction"))
                .andExpect(model().attribute("attraction", attraction));

    }

    @Test
    void addNewAttraction() {
    }

    @Test
    void updateAttraction() {
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