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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TouristController.class)
class VedIkkeRigtigOmDetHerErBedreEllerDårligereMenNuEksistereDet {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private TouristService touristService;

    private final List<TouristAttraction> attractions = new ArrayList<>();
    private TouristAttraction attraction;

    @Test
    void getAll() throws Exception {
        when(touristService.getAllAttractions()).thenReturn(attractions);
        mockMvc.perform(get("/attractions"))
                .andExpect(status().isOk())
                .andExpect(view().name("attractions-list"))
                .andExpect(model().attributeExists("attractions"))
                .andExpect(model().attribute("attractions", attractions));
    }

    @Test
    void getByName() throws Exception {
        when(touristService.findAttractionsByName(attraction.getName())).thenReturn(attraction);
        mockMvc.perform(get("/attractions/{name}", attraction.getName()))
                .andExpect(status().isOk())
                .andExpect(view().name("attraction"))
                .andExpect(model().attributeExists("attraction"))
                .andExpect(model().attribute("attraction", attraction));
    }

    @Test
    void getAttractionTagsByName() throws Exception {
        when(touristService.findAttractionsByName(attraction.getName())).thenReturn(attraction);
        mockMvc.perform(get("/attractions/{name}/tags", attraction.getName()))
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
        when(touristService.findAttractionsByName(attraction.getName())).thenReturn(attraction);
        mockMvc.perform(get("/attractions/{name}/edit", attraction.getName()))
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
        when(touristService.createAttraction(any(TouristAttraction.class))).thenReturn(attraction);
        mockMvc.perform(post("/attractions/save")
                        .param("name", "Tivoli")
                        .param("description", "Forlystelsespark")
                        .param("city", Cities.KØBENHAVN.name())
                        .param("tags", "UDENDØRS", "OPLEVELSE", "UNDERHOLDNING"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/attractions"));

        ArgumentCaptor<TouristAttraction> captor = ArgumentCaptor.forClass(TouristAttraction.class);
        verify(touristService).createAttraction(captor.capture());

        var saved = captor.getValue();
        assertEquals(attraction.getName(), saved.getName());
        assertEquals(attraction.getDescription(), saved.getDescription());
        assertEquals(attraction.getCity(), saved.getCity());
        assertEquals(attraction.getTags(), saved.getTags());
    }

    @Test
    void updateAttraction() throws Exception {
        when(touristService.updateAttraction(any(TouristAttraction.class))).thenReturn(attraction);
        mockMvc.perform(post("/attractions/update")
                        .param("name", attraction.getName())
                        .param("description", attraction.getDescription())
                        .param("city", attraction.getCity().name())
                        .param("tags", "UDENDØRS", "OPLEVELSE", "UNDERHOLDNING"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/attractions/Tivoli"));

        ArgumentCaptor<TouristAttraction> captor = ArgumentCaptor.forClass(TouristAttraction.class);
        verify(touristService).updateAttraction(captor.capture());

        var saved = captor.getValue();
        assertEquals(attraction.getName(), saved.getName());
        assertEquals(attraction.getDescription(), saved.getDescription());
        assertEquals(attraction.getCity(), saved.getCity());
        assertEquals(attraction.getTags(), saved.getTags());
    }

    @Test
    void deleteAttraction() throws Exception {
        when(touristService.deleteAttraction(attraction.getName())).thenReturn(attraction);
        mockMvc.perform(post("/attractions/delete/{name}", attraction.getName()))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/attractions"));

        verify(touristService).deleteAttraction(attraction.getName());
    }

    @BeforeEach
    void setUp() {
        attraction = new TouristAttraction("Tivoli", "Forlystelsespark", Cities.KØBENHAVN, List.of(Tags.UDENDØRS, Tags.OPLEVELSE, Tags.UNDERHOLDNING));
        attractions.add(new TouristAttraction("Tivoli", "Forlystelsespark", Cities.KØBENHAVN, List.of(Tags.UDENDØRS, Tags.OPLEVELSE, Tags.UNDERHOLDNING)));
        attractions.add(new TouristAttraction("Mash", "Spisested", Cities.KØBENHAVN, List.of(Tags.DYRT, Tags.OPLEVELSE)));
        attractions.add(new TouristAttraction("EK", "Ehvervesakademi", Cities.KØBENHAVN, List.of(Tags.BØRNEVENLIG, Tags.SKOLE, Tags.GRATIS)));
    }

    @AfterEach
    void tearDown() {
    }
}