package org.example.touristguideapi.model;

import java.util.ArrayList;
import java.util.List;

public class TouristAttraction {
    private int touristId;
    private String name;
    private String description;
    private String city;
    private int cityId;
    private List<String> tags = new ArrayList<>();

    public TouristAttraction(int touristId, int cityId, String name, String description, String city, List<String> tags) {
        this.touristId = touristId;
        this.cityId = cityId;
        this.name = name;
        this.description = description;
        this.city = city;
        this.tags = tags;
    }

    public TouristAttraction() {
        this.touristId = 0;
        this.cityId = 0;
        this.name = "";
        this.description = "";
        this.city = "";
        this.tags = new ArrayList<>();
    }
    public int getTouristId() {
        return touristId;
    }

    public void setTouristId(int touristId) {
        this.touristId = touristId;
    }

    public int getCityId() {
        return cityId;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
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

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
