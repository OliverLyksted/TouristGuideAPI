CREATE TABLE City (
                      id INT AUTO_INCREMENT PRIMARY KEY,
                      name VARCHAR(255) NOT NULL
);

CREATE TABLE Tag (
                     id INT AUTO_INCREMENT PRIMARY KEY,
                     name VARCHAR(255) NOT NULL
);

CREATE TABLE TouristAttraction (
                                   tourist_id INT AUTO_INCREMENT PRIMARY KEY,
                                   name VARCHAR(255) NOT NULL,
                                   description TEXT NOT NULL,
                                   city_id INT NOT NULL,
                                   FOREIGN KEY (city_id) REFERENCES City(id)
);

CREATE TABLE TouristAttractionTag (
                                      tourist_attraction_id INT,
                                      tag_id INT,
                                      FOREIGN KEY (tourist_attraction_id) REFERENCES TouristAttraction(tourist_id),
                                      FOREIGN KEY (tag_id) REFERENCES Tag(id),
                                      PRIMARY KEY (tourist_attraction_id, tag_id)
);