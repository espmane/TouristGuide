package com.hectorpetersen.touristguideapi.controller;

import com.hectorpetersen.touristguideapi.model.Cities;
import com.hectorpetersen.touristguideapi.model.Tags;
import com.hectorpetersen.touristguideapi.model.TouristAttraction;
import com.hectorpetersen.touristguideapi.service.TouristService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
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
        List<TouristAttraction> attractions = new ArrayList<>();
        attractions.add(new TouristAttraction("Tivoli", "Forlystelsespark", "København", List.of(Tags.UDENDØRS, Tags.OPLEVELSE, Tags.UNDERHOLDNING)));
        attractions.add(new TouristAttraction("Mash", "Spisested", "København", List.of(Tags.DYRT, Tags.OPLEVELSE)));
        attractions.add(new TouristAttraction("EK", "Ehvervesakademi", "København", List.of(Tags.BØRNEVENLIG, Tags.SKOLE, Tags.GRATIS)));

        when(touristService.getAllAttractions()).thenReturn(attractions);
        mockMvc.perform(get("/attractions"))
                .andExpect(status().isOk())
                .andExpect(view().name("attractions-list"))
                .andExpect(model().attributeExists("attractions"))
                .andExpect(model().attribute("attractions", attractions));
    }

    @Test
    void getByName() throws Exception {
        String name = "Tivoli";
        var attraction = new TouristAttraction("Tivoli", "Udendørs forlystelsespark", "København", List.of(Tags.BØRNEVENLIG, Tags.SKOLE, Tags.GRATIS));

        when(touristService.findAttractionsByName(name)).thenReturn(attraction);
        mockMvc.perform(get("/attractions/{name}", name))
                .andExpect(status().isOk())
                .andExpect(view().name("attraction"))
                .andExpect(model().attributeExists("attraction"))
                .andExpect(model().attribute("attraction", attraction));
    }

    @Test
    void getAttractionTagsByName() throws Exception {
        String name = "Tivoli";
        var attraction = new TouristAttraction("Tivoli", "Udendørs forlystelsespark", "København", List.of(Tags.BØRNEVENLIG, Tags.SKOLE, Tags.GRATIS));

        when(touristService.findAttractionsByName(name)).thenReturn(attraction);
        mockMvc.perform(get("/attractions/{name}/tags", name))
                .andExpect(status().isOk())
                .andExpect(view().name("attraction-tags"))
                .andExpect(model().attributeExists("attraction"))
                .andExpect(model().attribute("attraction", attraction));
    }

    @Test
    void addAttraction() throws Exception {
        mockMvc.perform(get("/attractions/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("add-attraction"))
                .andExpect(model().attributeExists("attraction"))
                .andExpect(model().attributeExists("tags"))
                .andExpect(model().attributeExists("cities"))
                .andExpect(model().attribute("tags", Tags.values()))
                .andExpect(model().attribute("cities", Cities.values()));
    }

    @Test
    void editAttraction() throws Exception {
        String name = "Tivoli";
        var attraction = new TouristAttraction("Tivoli", "Udendørs forlystelsespark", "København", List.of(Tags.BØRNEVENLIG, Tags.SKOLE, Tags.GRATIS));

        when(touristService.findAttractionsByName(name)).thenReturn(attraction);
        mockMvc.perform(get("/attractions/{name}/edit", name))
                .andExpect(status().isOk())
                .andExpect(view().name("edit-attraction"))
                .andExpect(model().attributeExists("attraction"))
                .andExpect(model().attributeExists("tags"))
                .andExpect(model().attributeExists("cities"))
                .andExpect(model().attribute("attraction", attraction))
                .andExpect(model().attribute("tags", Tags.values()))
                .andExpect(model().attribute("cities", Cities.values()));
    }

    @Test
    void saveAttraction() throws Exception {
        var attraction = new TouristAttraction("Tivoli", "Forlystelsespark", "København", List.of(Tags.UDENDØRS, Tags.OPLEVELSE, Tags.UNDERHOLDNING));

        when(touristService.createAttraction(any(TouristAttraction.class))).thenReturn(attraction);
        mockMvc.perform(post("/attractions/save")
                        .param("name", "Tivoli")
                        .param("description", "Forlystelsespark")
                        .param("city", "København")
                        .param("tags", Tags.UDENDØRS.name(), Tags.OPLEVELSE.name(), Tags.UNDERHOLDNING.name()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/attractions"));

        ArgumentCaptor<TouristAttraction> captor = ArgumentCaptor.forClass(TouristAttraction.class);
        verify(touristService).createAttraction(captor.capture());

        var saved = captor.getValue();
        assertEquals("Tivoli", saved.getName());
        assertEquals("Forlystelsespark", saved.getDescription());
        assertEquals("København", saved.getCity());
        assertEquals(List.of(Tags.UDENDØRS, Tags.OPLEVELSE, Tags.UNDERHOLDNING), saved.getTags());
    }

    @Test
    void updateAttraction() throws Exception {
        var attraction = new TouristAttraction("Tivoli", "Forlystelsespark", "København", List.of(Tags.UDENDØRS, Tags.OPLEVELSE, Tags.UNDERHOLDNING));

        when(touristService.updateAttraction(any(TouristAttraction.class))).thenReturn(attraction);
        mockMvc.perform(post("/attractions/update")
                        .param("name", "Tivoli")
                        .param("description", "Forlystelsespark")
                        .param("city", "København")
                        .param("tags", Tags.UDENDØRS.name(), Tags.OPLEVELSE.name(), Tags.UNDERHOLDNING.name()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/attractions/Tivoli"));

        ArgumentCaptor<TouristAttraction> captor = ArgumentCaptor.forClass(TouristAttraction.class);
        verify(touristService).updateAttraction(captor.capture());

        var saved = captor.getValue();
        assertEquals("Tivoli", saved.getName());
        assertEquals("Forlystelsespark", saved.getDescription());
        assertEquals("København", saved.getCity());
        assertEquals(List.of(Tags.UDENDØRS, Tags.OPLEVELSE, Tags.UNDERHOLDNING), saved.getTags());
    }

    @Test
    void deleteAttraction() throws Exception {
        String name = "EK";
        var attraction = new TouristAttraction("EK", "Ehvervesakademi", "København", List.of(Tags.BØRNEVENLIG, Tags.SKOLE, Tags.GRATIS));

        when(touristService.deleteAttraction(name)).thenReturn(attraction);
        mockMvc.perform(post("/attractions/delete/EK"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/attractions"));

        verify(touristService).deleteAttraction(name);
    }

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }
}