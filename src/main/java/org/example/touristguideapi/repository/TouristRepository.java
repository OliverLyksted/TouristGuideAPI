package org.example.touristguideapi.repository;


import org.example.touristguideapi.model.TouristAttraction;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

@Repository
public class TouristRepository {
    private List<TouristAttraction> touristAttractions;

    public TouristRepository() {
        touristAttractions = new ArrayList<>();
        touristAttractions.add(new TouristAttraction(1,"Zoo", "The Zoo in Copenhagen is one of the oldest culture institutions in Denmark. The origin of the Zoo dates all the way back to 1859 where it was one of the first zoo's in all of Europe! Today the Zoo is one of the most attractive places for a fun and educational day with either family, friends or even alone. The Zoo has around 1.3 million guests every year, and with more than 4000 different animals and 250 different sorts of animals, it is impossible not to have fun when visiting", "København", List.of("Underholdning", "Familie", "Restaurant")));
        touristAttractions.add(new TouristAttraction(2,"Tivoli", "A very popular Amusement park", "København", List.of("Underholdning", "Restaurant", "Koncert")));
    }
    /*

    public List<TouristAttraction> getTouristAttractions() {
        return touristAttractions;
    }

    public void addTouristAttraction(TouristAttraction touristAttraction) {
        touristAttractions.add(touristAttraction);

    }

    public TouristAttraction getAttractionByName(String name) {
        int i = 0;
        while (i < touristAttractions.size()) {
            if (name.equals(touristAttractions.get(i).getName())) {
                return touristAttractions.get(i);
            }
            i++;
        }
        return null;
    }




    public void updateTouristAttraction(TouristAttraction updatedAttraction) {

        for (TouristAttraction touristAttraction : touristAttractions) {
            if (touristAttraction.getName().equals(updatedAttraction.getName())) {
                touristAttraction.setDescription(updatedAttraction.getDescription());
                touristAttraction.setTags(updatedAttraction.getTags());
                touristAttraction.setCity(updatedAttraction.getCity());
            }


        }

    }

    public void deleteAttraction(String name) {
        int i = 0;
        TouristAttraction Attraction;
        while (i < touristAttractions.size()) {
            if (name.equals(touristAttractions.get(i).getName())) {
                Attraction = touristAttractions.get(i);
                touristAttractions.remove(Attraction);


            }
            i++;
        }
    }

    public List<String> getTagsForAttraction(String name) {
        TouristAttraction attraction = getAttractionByName(name);
        if (attraction != null) {
            return attraction.getTags();
        } else
            return null;

    }

    public List<String> getCities() {
        return List.of("Copenhagen", "Aarhus", "Odense", "Aalborg");
    }

    public List<String> getTags() {
        return List.of("Underholdning", "Familie", "Restaurant", "Koncert", "Gratis", "Kunst", "Museum", "Natur");

    }
*/

    public TouristAttraction getAttractionByName(String name) throws SQLException {
        String sql = "SELECT * FROM TouristAttraction WHERE name = ?";
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://turistguidedb.mysql.database.azure.com/touristguide_db", "touristguideTEO", "Database1")) {
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, name);
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        int id = resultSet.getInt("tourist_id");
                        String description = resultSet.getString("description");
                        int cityId = resultSet.getInt("city_id");

                        String city = getCityById(cityId);

                        List<String> tags = getTagsForAttraction(id);

                        return new TouristAttraction(id, name, description, city, tags);
                    }
                }
            }
        }
        return null;
    }


    public List<String> getTagsForAttraction(int attractionId) throws SQLException {
        List<String> tags = new ArrayList<>();
        String sql = "SELECT name FROM Tag WHERE id IN (SELECT tag_id FROM TouristAttractionTags WHERE tourist_attraction_id = ?)";
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://turistguidedb.mysql.database.azure.com/touristguide_db", "touristguideTEO", "Database1")) {
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setInt(1, attractionId);
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        String tagName = resultSet.getString("name");
                        tags.add(tagName);
                    }
                }
            }
            return tags;
        }
    }
    public String getCityById(int cityId) throws SQLException {
        String city = null;
        String sql = "SELECT name FROM City WHERE id = ?";
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://turistguidedb.mysql.database.azure.com/touristguide_db", "touristguideTEO", "Database1")) {
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setInt(1, cityId);
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        city = resultSet.getString("name");
                    }
                }
            }
        }
        return city;
    }
}







