package org.example.touristguideapi.service;


import org.example.touristguideapi.model.TouristAttraction;
import org.example.touristguideapi.repository.TouristRepository;
import org.springframework.stereotype.Service;

@Service
public class TouristService {
private TouristRepository touristRepository = new TouristRepository();

public List<TouristAttraction> getAttraction(){
    return touristRepository.getTouristAttractions();
}

public void addAttration(TouristAttraction touristAttraction){
    touristRepository.addTouristAttraction(touristAttraction);
}
public void updateAttraction(TouristAttraction touristAttraction){
touristRepository.updateTouristAttraction(touristAttraction);
}
public void deleteAttraction(TouristAttraction touristAttraction){
    touristRepository.deleteAttraction(touristAttraction);
}

}




