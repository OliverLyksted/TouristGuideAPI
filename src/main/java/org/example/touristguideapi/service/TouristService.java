package org.example.touristguideapi.service;


import org.example.touristguideapi.TouristGuideApiApplication;
import org.example.touristguideapi.model.TouristAttraction;
import org.example.touristguideapi.repository.TouristRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TouristService {
    private TouristRepository touristRepository;

    public TouristService(TouristRepository touristRepository) {
        this.touristRepository = touristRepository;
    }

    public List<TouristAttraction> getAttraction() {
        return touristRepository.getTouristAttractions();
    }

    public TouristAttraction getAttractionByName(String name) {
        return touristRepository.getAttractionByName(name);
    }

    public void addAttraction(TouristAttraction touristAttraction) {
        touristRepository.addTouristAttraction(touristAttraction);
    }

    public void updateAttraction(TouristAttraction touristAttraction) {
        touristRepository.updateTouristAttraction(touristAttraction);
    }

    public void deleteAttraction(TouristAttraction touristAttraction) {
        touristRepository.deleteAttraction(touristAttraction);
    }
    public List<String> getTagsForAttraction(String name){
        return touristRepository.getTagsForAttraction(name);
    }

}




