INSERT INTO Tag (name) VALUES ('Underholdning'), ('Familie'), ('Restaurant'), ('Koncert');

SELECT id FROM Tag WHERE name IN ('Underholdning', 'Familie', 'Restaurant', 'Koncert');

INSERT INTO City (name) VALUES ('Copenhagen'), ('Aarhus'), ('Odense'), ('Aalborg');

SELECT id FROM City;

INSERT INTO TouristAttraction (name, description, city_id)
VALUES
    ('Zoo', 'The Zoo in Copenhagen is one of the oldest culture institutions in Denmark...', 1),
    ('Tivoli', 'A very popular Amusement park', 1);


SELECT id FROM Tag WHERE name IN ('Underholdning', 'Familie', 'Restaurant', 'Koncert');

INSERT INTO TouristAttractionTag (tourist_attraction_id, tag_id)
VALUES
    (1, 1), (1, 2), (1, 3),
    (2, 1), (2, 3), (2, 4);
