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
        model.addAttribute("touristAttraction", new TouristAttraction());
        return "addAttraction";
    }

    @PutMapping("/update")
    public ResponseEntity<Void> updateAttraction(@RequestBody TouristAttraction touristAttraction) {
        touristService.updateAttraction(touristAttraction);
        return new ResponseEntity<>(HttpStatus.OK);

    }

    /* @DeleteMapping("/delete/{name}")
     public ResponseEntity<Void> deleteAttraction(@PathVariable String name) {
         TouristAttraction touristAttraction = new TouristAttraction(name, "","",);
         touristService.deleteAttraction(touristAttraction);
         return new ResponseEntity<>(HttpStatus.OK);
     }

     */
    @GetMapping("/{name}/tags")
    public String showAttractionTags(@PathVariable String name, Model model) {
        List<String> tags = touristService.getTagsForAttraction(name);
        model.addAttribute("tags", tags);
        return "tags";
    }


}





