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
    }
        /*
        touristAttractions.add(new TouristAttraction(1, "Zoo", "The Zoo in Copenhagen is one of the oldest culture institutions in Denmark. The origin of the Zoo dates all the way back to 1859 where it was one of the first zoo's in all of Europe! Today the Zoo is one of the most attractive places for a fun and educational day with either family, friends or even alone. The Zoo has around 1.3 million guests every year, and with more than 4000 different animals and 250 different sorts of animals, it is impossible not to have fun when visiting", "København", List.of("Underholdning", "Familie", "Restaurant")));

        touristAttractions.add(new TouristAttraction(2, "Tivoli", "A very popular Amusement park", "København", List.of("Underholdning", "Restaurant", "Koncert")));
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

    public TouristAttraction getAttractionByName(String name) {
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

                        return new TouristAttraction(id, cityId, name, description, city, tags);
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }


    public List<String> getTagsForAttraction(int attractionId) {
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
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public String getCityById(int cityId) {
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
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return city;
    }

    public void deleteAttraction(String name) {
        String sql = "DELETE FROM TouristAttraction WHERE name = ?";
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://turistguidedb.mysql.database.azure.com/touristguide_db", "touristguideTEO", "Database1")) {
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, name);
                int rowsAffected = statement.executeUpdate();
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateTouristAttraction(TouristAttraction updatedAttraction) {
        String sql = "UPDATE TouristAttraction SET description = ?, tags = ?, city = ? WHERE name = ?";
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://turistguidedb.mysql.database.azure.com/touristguide_db", "touristguideTEO", "Database1")) {
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, updatedAttraction.getDescription());
                statement.setString(2, String.join(",", updatedAttraction.getTags()));
                statement.setString(3, updatedAttraction.getCity());
                statement.setString(4, updatedAttraction.getName());
                int rowsAffected = statement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }



    public void addTouristAttraction(TouristAttraction touristAttraction) {
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://turistguidedb.mysql.database.azure.com/touristguide_db", "touristguideTEO", "Database1")) {
            String sql = "INSERT INTO TouristAttraction (name, description, city_id) VALUES (?, ?, ?)";
            try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                statement.setString(1, touristAttraction.getName());
                statement.setString(2, touristAttraction.getDescription());
                statement.setInt(3, touristAttraction.getCityId());

                int rowsAffected = statement.executeUpdate();
                if (rowsAffected == 1) {
                    try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                        if (generatedKeys.next()) {
                            int generatedId = generatedKeys.getInt(1);
                            touristAttraction.setTouristId(generatedId);
                        }
                    }
                } else {
                    throw new SQLException("Insertion failed, no rows affected.");
                }
            }


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int getAttractionIdByName(String name) {
        String sql = "SELECT tourist_id FROM TouristAttraction WHERE name = ?";
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://turistguidedb.mysql.database.azure.com/touristguide_db", "touristguideTEO", "Database1")) {
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, name);
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        return resultSet.getInt("tourist_id");
                    }
                }
            }
            throw new IllegalArgumentException("Turistattraktion med navnet " + name + " blev ikke fundet.");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public List<TouristAttraction> getTouristAttractions() {
        List<TouristAttraction> attractions = new ArrayList<>();
        String sql = "SELECT tourist_id, name, description, city_id FROM TouristAttraction";
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://turistguidedb.mysql.database.azure.com/touristguide_db", "touristguideTEO", "Database1")) {
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        int id = resultSet.getInt("tourist_id");
                        String name = resultSet.getString("name");
                        String description = resultSet.getString("description");
                        int cityId = resultSet.getInt("city_id");
                        String cityName = getCityById(cityId);
                        List<String> tags = getTagsForAttraction(id);
                        TouristAttraction attraction = new TouristAttraction(id, cityId, name, description, cityName, tags);
                        attractions.add(attraction);
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return attractions;
    }

    public List<String> getTags() {
        List<String> tags = new ArrayList<>();
        String sql = "SELECT name FROM Tag";
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://turistguidedb.mysql.database.azure.com/touristguide_db", "touristguideTEO", "Database1")) {
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        String tagName = resultSet.getString("name");
                        tags.add(tagName);
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return tags;
    }

    public List<String> getCities() {
        List<String> cities = new ArrayList<>();
        String sql = "SELECT name FROM City";
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://turistguidedb.mysql.database.azure.com/touristguide_db", "touristguideTEO", "Database1")) {
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        String cityName = resultSet.getString("name");
                        cities.add(cityName);
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return cities;
    }


}







