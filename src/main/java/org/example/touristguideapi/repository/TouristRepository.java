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


    public TouristAttraction getAttractionByName(String name) {
        String sql = "SELECT * FROM TouristAttraction WHERE name = ?";
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://touristguidedb.mysql.database.azure.com/touristguide_db", "touristguideTEO", "Database1")) {
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, name);
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        int id = resultSet.getInt("tourist_id");
                        String description = resultSet.getString("description");
                        int cityId = resultSet.getInt("city_id");

                        String city = getCityById(cityId);

                        List<String> tags = getTagsForAttraction(id);

                        return new TouristAttraction(id, name, description,cityId, city, tags);
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public void addTouristAttraction(TouristAttraction touristAttraction) {
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://touristguidedb.mysql.database.azure.com/touristguide_db", "touristguideTEO", "Database1")) {
            String sql = "INSERT INTO TouristAttraction (name, description, city_id) VALUES (?, ?, ?)";
            try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                statement.setString(1, touristAttraction.getName());
                statement.setString(2, touristAttraction.getDescription());
                statement.setInt(3, getCityIdByName(touristAttraction.getCity()));
                int rowsAffected = statement.executeUpdate();
                if (rowsAffected == 1) {
                    try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                        if (generatedKeys.next()) {
                            int generatedId = generatedKeys.getInt(1);
                            touristAttraction.setTouristId(generatedId);
                            insertTagsForAttraction(generatedId, touristAttraction.getTags());
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

    public void deleteAttraction(String name) {
        String deleteTagsSql = "DELETE FROM TouristAttractionTag WHERE tourist_attraction_id = ?";
        String deleteAttractionSql = "DELETE FROM TouristAttraction WHERE name = ?";

        try (Connection connection = DriverManager.getConnection("jdbc:mysql://touristguidedb.mysql.database.azure.com/touristguide_db", "touristguideTEO", "Database1")) {
            int attractionId = getAttractionIdByName(name);
            try (PreparedStatement deleteTagsStatement = connection.prepareStatement(deleteTagsSql)) {
                deleteTagsStatement.setInt(1, attractionId);
                deleteTagsStatement.executeUpdate();
            }

            try (PreparedStatement deleteAttractionStatement = connection.prepareStatement(deleteAttractionSql)) {
                deleteAttractionStatement.setString(1, name);
                deleteAttractionStatement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void updateTouristAttraction(TouristAttraction updatedAttraction) {
        String sql = "UPDATE TouristAttraction SET description = ?, city_id = ? WHERE name = ?";
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://touristguidedb.mysql.database.azure.com/touristguide_db", "touristguideTEO", "Database1")) {
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, updatedAttraction.getDescription());
                statement.setInt(2, getCityIdByName(updatedAttraction.getCity()));
                statement.setString(3, updatedAttraction.getName());
                int rowsAffected = statement.executeUpdate();
                if (rowsAffected > 0) {
                    updateTagsForAttraction(updatedAttraction.getTouristId(), updatedAttraction.getTags());
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }




    public List<String> getTagsForAttraction(int attractionId) {
        List<String> tags = new ArrayList<>();
        String sql = "SELECT name FROM Tag WHERE id IN (SELECT tag_id FROM TouristAttractionTag WHERE tourist_attraction_id = ?)";
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://touristguidedb.mysql.database.azure.com/touristguide_db", "touristguideTEO", "Database1")) {
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
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://touristguidedb.mysql.database.azure.com/touristguide_db", "touristguideTEO", "Database1")) {
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



    public int getAttractionIdByName(String name) {
        String sql = "SELECT tourist_id FROM TouristAttraction WHERE name = ?";
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://touristguidedb.mysql.database.azure.com/touristguide_db", "touristguideTEO", "Database1")) {
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
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://touristguidedb.mysql.database.azure.com/touristguide_db", "touristguideTEO", "Database1")) {
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        int id = resultSet.getInt("tourist_id");
                        String name = resultSet.getString("name");
                        String description = resultSet.getString("description");
                        int cityId = resultSet.getInt("city_id");
                        String cityName = getCityById(cityId);
                        List<String> tags = getTagsForAttraction(id);
                        TouristAttraction attraction = new TouristAttraction(id, name, description,cityId,cityName, tags);

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
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://touristguidedb.mysql.database.azure.com/touristguide_db", "touristguideTEO", "Database1")) {
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
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://touristguidedb.mysql.database.azure.com/touristguide_db", "touristguideTEO", "Database1")) {
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

    public int getCityIdByName(String cityName) {
        String sql = "SELECT id FROM City WHERE name = ?";
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://touristguidedb.mysql.database.azure.com/touristguide_db", "touristguideTEO", "Database1")) {
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, cityName);
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        return resultSet.getInt("id");
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        throw new IllegalArgumentException("City with name " + cityName + " not found.");
    }
    public int getTagIdByName(String tagName) {
        String sql = "SELECT id FROM Tag WHERE name = ?";
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://touristguidedb.mysql.database.azure.com/touristguide_db", "touristguideTEO", "Database1")) {
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, tagName);
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        return resultSet.getInt("id");
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        throw new IllegalArgumentException();
    }



    public void updateTagsForAttraction(int attractionId, List<String> tags) {
        String deleteTagsSql = "DELETE FROM TouristAttractionTag WHERE tourist_attraction_id = ?";
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://touristguidedb.mysql.database.azure.com/touristguide_db", "touristguideTEO", "Database1")) {
            try (PreparedStatement deleteTagsStatement = connection.prepareStatement(deleteTagsSql)) {
                deleteTagsStatement.setInt(1, attractionId);
                deleteTagsStatement.executeUpdate();
            }
            String insertTagsSql = "INSERT INTO TouristAttractionTag (tourist_attraction_id, tag_id) VALUES (?, ?)";
            try (PreparedStatement insertTagsStatement = connection.prepareStatement(insertTagsSql)) {
                for (String tag : tags) {
                    int tagId = getTagIdByName(tag);
                    insertTagsStatement.setInt(1, attractionId);
                    insertTagsStatement.setInt(2, tagId);
                    insertTagsStatement.addBatch();
                }
                insertTagsStatement.executeBatch();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }



    public void insertTagsForAttraction(int attractionId, List<String> tags) {
        String sql = "INSERT INTO TouristAttractionTag (tourist_attraction_id, tag_id) VALUES (?, ?)";
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://touristguidedb.mysql.database.azure.com/touristguide_db", "touristguideTEO", "Database1")) {
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                for (String tag : tags) {
                    int tagId = getTagIdByName(tag);
                    statement.setInt(1, attractionId);
                    statement.setInt(2, tagId);
                    statement.addBatch();
                }
                statement.executeBatch();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }





}







