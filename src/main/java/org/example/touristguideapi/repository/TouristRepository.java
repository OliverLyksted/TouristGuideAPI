package org.example.touristguideapi.repository;


import org.example.touristguideapi.model.TouristAttraction;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class TouristRepository {
    private List<TouristAttraction> touristAttractions;

    public TouristRepository() {
        touristAttractions = new ArrayList<>();
        touristAttractions.add(new TouristAttraction("zoo", "A place where you can see many different animals"));
        touristAttractions.add(new TouristAttraction("tivoli", "A very popular Amusement park"));
    }

    public List<TouristAttraction> getTouristAttractions() {
        return touristAttractions;
    }

    public void addTouristAttraction(TouristAttraction touristAttraction) {
        touristAttractions.add(touristAttraction);

    }

    public TouristAttraction getAttractionByName(String name) {
        int i = 0;
        while (i < touristAttractions.size()) {
            if (name.equals(touristAttractions.get(i).getName())){
                return touristAttractions.get(i);
            }
            i++;
        }
       return null;
    }


    public void updateTouristAttraction(TouristAttraction updatedAttraction) {

        for (TouristAttraction touristAttraction : touristAttractions) {
            if (touristAttraction.getDescription().equals(updatedAttraction.getDescription())) {
                touristAttraction.setDescription(updatedAttraction.getDescription());
            }
            break;

        }

    }

    public void deleteAttraction(TouristAttraction deleteAttraction) {
        int foundIndex = -1;

        for (int i = 0; i < touristAttractions.size(); i++) {
            if (deleteAttraction.getName().equals(touristAttractions.get(i).getName())) {
                foundIndex = i;

                if (foundIndex != -1) {
                    touristAttractions.remove(foundIndex);
                }
            }

        }
    }
}



