package com.hectorpetersen.touristguideapi.controller;

import com.hectorpetersen.touristguideapi.model.TouristAttraction;
import com.hectorpetersen.touristguideapi.service.TouristService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @GetMapping
    public ResponseEntity<List<TouristAttraction>> GetAll() {
        List<TouristAttraction> attractions = service.getAllAttractions();
        return new ResponseEntity<>(attractions, HttpStatus.CREATED);

    }

    @GetMapping("/{name}")
    public ResponseEntity<TouristAttraction> getByName(@PathVariable String name) {
        TouristAttraction found = service.findAttractionsByName(name);
        if (found == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(found, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<TouristAttraction> addNewAttraction(@RequestBody TouristAttraction attraction) {
        TouristAttraction createAttractioj = service.createAttraction(attraction);
        return new ResponseEntity<>(createAttractioj, HttpStatus.OK);
    }

    @PostMapping("/update")
    public ResponseEntity<TouristAttraction> updateAttraction(@RequestBody String name, String newDescription){
        service.updateAttraction(name, newDescription);
        return new ResponseEntity<>(HttpStatus.OK);
    }
//aa..a
//    @PostMapping("/delete/{name}")
//    public ResponseEntity<TouristAttraction> deleteByName(){
//
//    }

}