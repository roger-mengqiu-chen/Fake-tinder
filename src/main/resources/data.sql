INSERT INTO role VALUES (1, "ADMIN");
INSERT INTO role VALUES (2, "FREE_USER");
INSERT INTO role VALUES (3, "PREMIUM_USER");

INSERT INTO reaction VALUES(1, "ACCEPT");
INSERT INTO reaction VALUES(2, "REJECT");
INSERT INTO reaction VALUES(3, "PEND");

INSERT INTO user (userId, roleId, isActive, isSuspended) VALUES (1,2,true, false);
INSERT INTO user (userId, roleId, isActive, isSuspended) VALUES (2,2,true, false);

INSERT INTO profile (userId, firstname, lastname, birthday, gender, showMe, description)
    VALUES (1, "Michael", "M", "1980-1-1", "MALE", "FEMALE", "This is test male user");

INSERT INTO profile (userId, firstname, lastname, birthday, gender, showMe, description)
    VALUES (2, "Rose", "R", "1980-1-1", "FEMALE", "MALE", "This is test female user");

INSERT INTO location (country, province, city, street, postcode)
    VALUES ('Canada', 'Alberta', 'Calgary', '2000 10 Ave', 'T2M 0A0');
INSERT INTO location (country, province, city, street, postcode)
    VALUES ('Canada', 'British Columbia', 'Vancouver', '1501 13 St', 'B2C 1D3');

INSERT INTO event (eventName, eventTime, locationId, eventDescription, eventLink)
    VALUES ('Business chat', '2020-10-10 10:10:10',1,'Simple event','no link');
INSERT INTO event (eventName, eventTime, locationId, eventDescription, eventLink)
    VALUES ('Business chat', '2020-11-11 11:11:11',2,'Simple event','no link');