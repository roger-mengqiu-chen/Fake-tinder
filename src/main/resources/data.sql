INSERT INTO role VALUES (1, "ADMIN");
INSERT INTO role VALUES (2, "FREE_USER");
INSERT INTO role VALUES (3, "PREMIUM_USER");

INSERT INTO reaction VALUES(1, "ACCEPT");
INSERT INTO reaction VALUES(2, "REJECT");
INSERT INTO reaction VALUES(3, "PENDING");

INSERT INTO user (userId, roleId, isActive, isSuspended) VALUES (1,2,true, false);
INSERT INTO user (userId, roleId, isActive, isSuspended) VALUES (2,2,true, false);

INSERT INTO profile (userId, firstname, lastname, age, gender, description)
    VALUES (1, "Michael", "M", 30, "M", "This is test male user");

INSERT INTO profile (userId, firstname, lastname, age, gender, description)
    VALUES (2, "Rose", "R", 30, "F", "This is test female user");