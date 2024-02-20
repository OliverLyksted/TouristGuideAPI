package org.example.touristguideapi.controller;

import org.example.touristguideapi.model.TouristAttraction;
import org.example.touristguideapi.service.TouristService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/attractions")
public class TouristController {
    private TouristService touristService;

    public TouristController(TouristService touristService) {
        this.touristService = touristService;
    }

    @GetMapping()
    public String showAttractionList(Model model) {
        List<TouristAttraction> attractions = touristService.getAttraction();
        model.addAttribute("attractions", attractions);
        return "attractionList";
    }

    @GetMapping("/{name}")
    public ResponseEntity<TouristAttraction> getAttractionName(@PathVariable String name) {
        TouristAttraction oneAttraction = touristService.getAttractionByName(name);
        return new ResponseEntity<>(oneAttraction, HttpStatus.OK);
    }

    @GetMapping("/add")
    public String addTouristAttraction(Model model) {
        TouristAttraction touristAttraction = new TouristAttraction();

        List<String> cities = touristService.getCities();
        List<String> tags = touristService.getTags();

        model.addAttribute("cities", cities);
        model.addAttribute("tags", tags);
        model.addAttribute("touristAttraction", touristAttraction);

        return "addAttraction";
    }

    @GetMapping("/{name}/edit")
    public String editAttraction(@PathVariable String name, Model model) {
        TouristAttraction attraction = touristService.getAttractionByName(name);
        model.addAttribute("touristAttraction", attraction);
        return "updateAttraction";
    }

    @PostMapping("/update")
    public String updateAttraction(@ModelAttribute TouristAttraction touristAttraction) {
        touristService.updateAttraction(touristAttraction);
        return "redirect:/attractions";
    }


    @PostMapping("/{name}/delete")
    public String deleteAttraction(@PathVariable String name) {
        touristService.deleteAttractionByName(name);
        return "redirect:/attractions";
    }


    @GetMapping("/{name}/tags")
    public String showAttractionTags(@PathVariable String name, Model model) {
        List<String> tags = touristService.getTagsForAttraction(name);
        model.addAttribute("tags", tags);
        return "tags";
    }

    @PostMapping("/save")
    public String saveTouristAttraction(@ModelAttribute TouristAttraction touristAttraction, Model model) {
        touristService.addAttraction(touristAttraction);

        List<TouristAttraction> attractions = touristService.getAttraction();
        model.addAttribute("attractions", attractions);

        return "attractionList";

    }
}





