package com.hectorpetersen.touristguideapi.controller;

import com.hectorpetersen.touristguideapi.model.Cities;
import com.hectorpetersen.touristguideapi.model.Tags;
import com.hectorpetersen.touristguideapi.model.TouristAttraction;
import com.hectorpetersen.touristguideapi.service.TouristService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @GetMapping()
    public String getAll(Model model) {
        List<TouristAttraction> attractions = service.getAllAttractions();
        model.addAttribute("attractions", attractions);
        return "attractions-list";

    }

    @GetMapping("/{name}")
    public String getByName(@PathVariable String name, Model model) {
        TouristAttraction attractions = service.findAttractionsByName(name);
        model.addAttribute("attraction", attractions);
        return "attraction";
    }

    @GetMapping("/{name}/tags")
    public String getAttractionTagsByName(@PathVariable String name, Model model) {
        TouristAttraction foundAttraction = service.findAttractionsByName(name);
        model.addAttribute("attraction", foundAttraction);
        return "attraction-tags";
    }

    @GetMapping("/add")
    public String addAttraction(Model model) {
        model.addAttribute("attraction", new TouristAttraction());
        model.addAttribute("tags", Tags.values());
        model.addAttribute("cities", Cities.values());
        return "add-attraction";
    }

    @GetMapping("/{name}/edit")
    public String editAttraction(@PathVariable String name, Model model) {
        TouristAttraction foundAttraction = service.findAttractionsByName(name);
        model.addAttribute("attraction", foundAttraction);
        model.addAttribute("tags", Tags.values());
        model.addAttribute("cities", Cities.values());
        return "edit-attraction";
    }

    @PostMapping("/save")
    public String saveAttraction(@ModelAttribute TouristAttraction attraction) {
        TouristAttraction newAttraction = service.createAttraction(attraction);
        if (newAttraction == null) {
            return null;
        }
        return "redirect:/attractions";
    }

    @PostMapping("/update")
    public String updateAttraction(@ModelAttribute TouristAttraction attraction) {
        TouristAttraction updatedAttraction = service.updateAttraction(attraction);
        return "redirect:/attractions/" + updatedAttraction.getName();
    }

    @PostMapping("/delete/{name}")
    public String deleteAttraction(@PathVariable String name) {
        service.deleteAttraction(name);
        return "redirect:/attractions";
    }
}