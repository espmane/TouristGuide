INSERT INTO Tags(Name) VALUES ('Gratis');
INSERT INTO Tags(Name) VALUES ('Billig');
INSERT INTO Tags(Name) VALUES ('Dyrt');
INSERT INTO Tags(Name) VALUES ('Indendørs');
INSERT INTO Tags(Name) VALUES ('Udendørs');
INSERT INTO Tags(Name) VALUES ('Skole');
INSERT INTO Tags(Name) VALUES ('Børnevenlig');
INSERT INTO Tags(Name) VALUES ('Museum');
INSERT INTO Tags(Name) VALUES ('Kunst');
INSERT INTO Tags(Name) VALUES ('Oplevelse');
INSERT INTO Tags(Name) VALUES ('Aktivitet');
INSERT INTO Tags(Name) VALUES ('Underholdning');
INSERT INTO Tags(Name) VALUES ('Dyreliv');
INSERT INTO Tags(Name) VALUES ('Historie');
INSERT INTO Tags(Name) VALUES ('Videnskab');


INSERT INTO City(Name) VALUES ('København');
INSERT INTO City(Name) VALUES ('Århus');
INSERT INTO City(Name) VALUES ('Odense');
INSERT INTO City(Name) VALUES ('Ålborg');
INSERT INTO City(Name) VALUES ('Holstebro');
INSERT INTO City(Name) VALUES ('Frederiksberg');
INSERT INTO City(Name) VALUES ('Horsens');
INSERT INTO City(Name) VALUES ('Hillerød');
INSERT INTO City(Name) VALUES ('Roskilde');
INSERT INTO City(Name) VALUES ('Kastrup');
INSERT INTO City(Name) VALUES ('Hornbæk');
INSERT INTO City(Name) VALUES ('Nørrebro');


INSERT INTO Attractions(Name, Description, City_ID)
VALUES ('EK', 'Erhvervsakademi',
(SELECT City_ID FROM City WHERE Name ='København'));

INSERT INTO Attraction_tags(Attractions_ID, Tags_ID)
SELECT attraction.Attractions_ID, tag.Tags_ID
FROM Attractions attraction, Tags tag
WHERE attraction.name = 'EK' AND tag.name IN ('Skole', 'Gratis', 'Børnevenlig');

INSERT INTO Attractions(Name, Description, City_ID)
VALUES ('Mash', 'Spisested',
(SELECT City_ID FROM City WHERE Name ='København'));

INSERT INTO Attraction_tags(Attractions_ID, Tags_ID)
SELECT attraction.Attractions_ID, tag.Tags_ID
FROM Attractions attraction, Tags tag
WHERE attraction.name = 'Mash' AND tag.name IN ('Indendørs', 'Oplevelse', 'Dyrt');

INSERT INTO Attractions(Name, Description, City_ID)
VALUES ('Tivoli', 'Forlystelsespark',
(SELECT City_ID FROM City WHERE Name='København'));

INSERT INTO Attraction_tags(Attractions_ID, Tags_ID)
SELECT attraction.Attractions_ID, tag.Tags_ID
FROM Attractions attraction, Tags tag
WHERE attraction.name = 'Tivoli' AND tag.name IN ('Oplevelse', 'Underholdning', 'Udendørs');

INSERT INTO Attractions(Name, Description, City_ID)
VALUES ('Hornbæk Skole', 'Ikke den i Randers',
(SELECT City_ID FROM City WHERE Name='Hornbæk'));

INSERT INTO Attraction_tags(Attractions_ID, Tags_ID)
SELECT attraction.Attractions_ID, tag.Tags_ID
FROM Attractions attraction, Tags tag
WHERE attraction.name = 'Hornbæk Skole' AND tag.name IN ('Dyreliv');
