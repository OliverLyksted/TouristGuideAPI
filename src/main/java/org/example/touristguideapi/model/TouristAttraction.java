package org.example.touristguideapi.model;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

public class TouristAttraction {
    private String name;
    private String description;
    private String city;
    private  List<String> tags = new ArrayList<>();

    public TouristAttraction(String name, String description, String city, List<String> tags){
        this.name=name;
        this.description=description;
        this.city = city;
        this.tags = tags;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public List<String> getTags() {
        return tags;
    }
}
