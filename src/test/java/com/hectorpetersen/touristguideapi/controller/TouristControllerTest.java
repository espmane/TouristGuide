package com.hectorpetersen.touristguideapi.controller;

import com.hectorpetersen.touristguideapi.service.TouristService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(TouristControllerTest.class)
class TouristControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private TouristService touristService;

    @Test
    void getAll() {
    }

    @Test
    void getByName() {
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