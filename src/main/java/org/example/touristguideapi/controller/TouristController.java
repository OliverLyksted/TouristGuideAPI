package org.example.touristguideapi.controller;

import org.example.touristguideapi.model.TouristAttraction;
import org.example.touristguideapi.service.TouristService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/attractions")
public class TouristController {
    private TouristService touristService = new TouristService();

    @GetMapping()
    public ResponseEntity<List<TouristAttraction>> getAttraction() {
    List<TouristAttraction> attractions = touristService.getAttraction();
    return new ResponseEntity<>(attractions, HttpStatus.OK);
    }

    @GetMapping("/{name}")
    public ResponseEntity<TouristAttraction> getAttractionName(@PathVariable String name) {
        TouristAttraction oneAttraction = touristService.getAttractionByName(name);
        return new ResponseEntity<>(oneAttraction, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<Void> addTouristAttraction(@RequestBody TouristAttraction touristAttraction) {
        touristService.addAttraction(touristAttraction);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity<Void> updateAttraction(@RequestBody TouristAttraction touristAttraction) {
        touristService.updateAttraction(touristAttraction);
        return new ResponseEntity<>(HttpStatus.OK);

    }

    @DeleteMapping("/delete/{name}")
    public ResponseEntity<Void> deleteAttraction(@PathVariable String name) {
        TouristAttraction touristAttraction = new TouristAttraction(name, "123123123");
        touristService.deleteAttraction(touristAttraction);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}





