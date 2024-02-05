package org.example.touristguideapi.repository;


import org.example.touristguideapi.model.TouristAttraction;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class TouristRepository {
    private List<TouristAttraction> touristAttractions;

    public TouristRepository(){
        touristAttractions = new ArrayList<>();
        touristAttractions.add(new TouristAttraction("Zoo", "A place where you can see many different animals"));
        touristAttractions.add(new TouristAttraction("Tivoli", "A very popular Amusement park"));
    }

    public List<TouristAttraction> getTouristAttractions(){
        return touristAttractions;
    }

}
