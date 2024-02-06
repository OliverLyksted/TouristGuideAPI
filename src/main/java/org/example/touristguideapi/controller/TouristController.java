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


    @GetMapping("/{name}")
    public ResponseEntity<List<TouristAttraction>> getAttractionName() {
        List<TouristAttraction> attraction = touristService.getAttraction();
        return new ResponseEntity<>(attraction, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<Void> addTouristAttraction(@RequestBody TouristAttraction touristAttraction) {
        touristService.addAttration(touristAttraction);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @PutMapping("/update")
    public ResponseEntity<Void> updateAttraction(@RequestBody TouristAttraction touristAttraction){
        touristService.updateAttraction(touristAttraction);
        return new ResponseEntity<>(HttpStatus.OK);

    }
    @DeleteMapping("/delete/{name}")
    public ResponseEntity<Void> deleteAttraction(@RequestBody TouristAttraction touristAttraction){
        touristService.deleteAttraction(touristAttraction);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}


