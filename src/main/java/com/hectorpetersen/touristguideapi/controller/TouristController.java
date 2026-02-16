package com.hectorpetersen.touristguideapi.controller;

import com.hectorpetersen.touristguideapi.model.TouristAttraction;
import com.hectorpetersen.touristguideapi.service.TouristService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
@RequestMapping("/attractions")
public class TouristController {

    private final TouristService service;

    public TouristController(TouristService service) {
        this.service = service;
    }

    @GetMapping("/attractions")
    public String GetAll(Model model) {
        List<TouristAttraction> attractions = service.getAllAttractions();
        model.addAttribute("attractions", attractions);
        return "attractionsList";

    }

    @GetMapping("/{name}")
    public String getByName(@PathVariable String name, Model model) {
        TouristAttraction attractions = service.findAttractionsByName(name);
        model.addAttribute("name", attractions);
        return "tags";

    }

    @PostMapping("/add")
    public ResponseEntity<TouristAttraction> addNewAttraction(@RequestBody TouristAttraction attraction) {
        TouristAttraction createAttraction = service.createAttraction(attraction);
        if (createAttraction == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(createAttraction, HttpStatus.CREATED);
    }

    @GetMapping("/{name}/edit")
    public String editAttraction(@PathVariable String name, Model model) {
        TouristAttraction foundAttraction = service.findAttractionsByName(name);
        if (foundAttraction == null) {
            return "redirect:attractions/error";
        }
        model.addAttribute("attraction", foundAttraction);
        model.addAttribute("tags", Tags.values());
        return "edit-attraction";
    }

    @PostMapping("/update")
    public String updateAttraction(@ModelAttribute TouristAttraction attraction) {
        TouristAttraction updatedAttraction = service.updateAttraction(attraction);
        if (updatedAttraction == null) {
            return "redirect:attractions/error";
        }
        return "redirect:/attractions/" + updatedAttraction.getName();
    }

    @PostMapping("/delete/{name}") //@Deletemapping kunne også bruges
    public ResponseEntity<TouristAttraction> deleteAttraction(@PathVariable String name) {
        TouristAttraction deletedAttraction = service.deleteAttraction(name);
        if (deletedAttraction == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(deletedAttraction, HttpStatus.OK);
    }

    @GetMapping("/error")
    public String error(@RequestBody String message, Model model) {
        //model.addAttribute("error", message); // vi kunne tilføje en error besked når vi redirecter, og så vise den på error siden
        return "error";
    }
}