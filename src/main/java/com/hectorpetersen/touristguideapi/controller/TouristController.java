package com.hectorpetersen.touristguideapi.controller;

import com.hectorpetersen.touristguideapi.model.TouristAttraction;
import com.hectorpetersen.touristguideapi.service.TouristService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;


@Controller
@RequestMapping("/attractions")
public class TouristController {

    private final TouristService service;

    public TouristController(TouristService service) {
        this.service = service;
    }

    @GetMapping //http://localhost:8080/attractions
    public String GetAll(Model model) {
        model.addAttribute("attractions", service.getAllAttractions());
        return "attractionList";
    }

    @GetMapping("/{name}")
    public ResponseEntity<TouristAttraction> getByName(@PathVariable String name) {
        TouristAttraction found = service.findAttractionsByName(name);
        if (found == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(found, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<TouristAttraction> addNewAttraction(@RequestBody TouristAttraction attraction) {
        TouristAttraction createAttraction = service.createAttraction(attraction);
        if (createAttraction == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(createAttraction, HttpStatus.CREATED);
    }

    @PostMapping("/update")
    public ResponseEntity<TouristAttraction> updateAttraction(@RequestBody TouristAttraction attraction) {
        TouristAttraction updatedAttraction = service.updateAttraction(attraction);
        if (updatedAttraction == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(updatedAttraction, HttpStatus.OK);
    }

    @PostMapping("/delete/{name}") //@Deletemapping kunne også bruges
    public ResponseEntity<TouristAttraction> deleteAttraction(@PathVariable String name) {
        TouristAttraction deletedAttraction = service.deleteAttraction(name);
        if (deletedAttraction == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(deletedAttraction, HttpStatus.OK);
    }
}