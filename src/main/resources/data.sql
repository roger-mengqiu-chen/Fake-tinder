INSERT INTO user (userId, roleId, isActive, isSuspended) VALUES (1,2,true, false);
INSERT INTO user (userId, roleId, isActive, isSuspended) VALUES (2,2,true, false);

INSERT INTO profile (userId, firstname, lastname, age, gender, description)
    VALUES (1, "Michael", "M", 30, "M", "This is test male user");

INSERT INTO profile (userId, firstname, lastname, age, gender, description)
    VALUES (2, "Rose", "R", 30, "F", "This is test female user");