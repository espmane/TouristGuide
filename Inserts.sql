INSERT INTO Tags(Name)
VALUES ('Gratis'),('Billig'),('Dyrt'),
('Indendørs'),('Udendørs'),('Skole'),
('Børnevenlig'),('Museum'),('Kunst'),
('Oplevelse'),('Aktivitet'),('Underholdning'),
('Dyreliv'),('Historie'),('Videnskab');

INSERT INTO City (Name)
VALUES ('København'),('Aarhus'),('Odense'),
('Aalborg'),('Esbjerg'),('Randers'),
('Kolding'),('Horsens'),('Roskilde'),
('Silkeborg'),('Næstved'),('Hillerød'),
('Helsingør'),('Fredericia'),('Hornbæk');


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
