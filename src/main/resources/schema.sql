
DROP TABLE IF EXISTS userDevice;
DROP TABLE IF EXISTS userTag;
DROP TABLE IF EXISTS userPreference;
DROP TABLE IF EXISTS userEvent;
DROP TABLE IF EXISTS eventInvitation;
DROP TABLE IF EXISTS matches;
DROP TABLE IF EXISTS attachment;
DROP TABLE IF EXISTS message;
DROP TABLE IF EXISTS notification;
DROP TABLE IF EXISTS reportedUser;
DROP TABLE IF EXISTS paymentInfo;
DROP TABLE IF EXISTS profile;
DROP TABLE IF EXISTS user;
DROP TABLE IF EXISTS event;
DROP TABLE IF EXISTS role;
DROP TABLE IF EXISTS preference;
DROP TABLE IF EXISTS location;
DROP TABLE IF EXISTS reaction;
DROP TABLE IF EXISTS appTerms;
DROP TABLE IF EXISTS report;

CREATE TABLE IF NOT EXISTS appTerms (
    versionId int AUTO_INCREMENT,
    context longText NOT NULL,
    PRIMARY KEY (versionId)
) ENGINE=InnoDB DEFAULT CHARSET=utf8MB4;

CREATE TABLE IF NOT EXISTS report (
    reportId int AUTO_INCREMENT,
    reportContent longText NOT NULL,
    PRIMARY KEY (reportId)
) ENGINE=InnoDB DEFAULT CHARSET=utf8MB4;

CREATE TABLE IF NOT EXISTS role (
    roleId tinyInt AUTO_INCREMENT,
    roleName varchar(20) NOT NULL UNIQUE,
    PRIMARY KEY (roleId)
) ENGINE=InnoDB DEFAULT CHARSET=utf8MB4;

CREATE TABLE IF NOT EXISTS reaction (
    reactionId tinyint AUTO_INCREMENT,
    reactionType varchar(10) NOT NULL,
    PRIMARY KEY (reactionId)
) ENGINE=InnoDB DEFAULT CHARSET=utf8MB4;

CREATE TABLE IF NOT EXISTS preference (
    preferenceId bigint AUTO_INCREMENT,
    content varchar(255) NOT NULL,
    PRIMARY KEY (preferenceId)
) ENGINE=InnoDB DEFAULT CHARSET=utf8MB4;

CREATE TABLE IF NOT EXISTS location (
    locationId bigint AUTO_INCREMENT,
    lat decimal(13,10),
    lon decimal(13,10),
    country varchar(50),
    province varchar(50),
    city varchar(50),
    street varchar(255),
    postcode varchar(10),
    PRIMARY KEY (locationId)
) ENGINE=InnoDB DEFAULT CHARSET=utf8MB4;

CREATE TABLE IF NOT EXISTS event (
    eventId bigint AUTO_INCREMENT,
    eventName varchar(255) NOT NULL,
    eventTime datetime NOT NULL,
    locationId bigint NOT NULL,
    eventDescription mediumText NOT NULL,
    eventLink varchar(255) NOT NULL,
    PRIMARY KEY (eventId),
    FOREIGN KEY (locationId) REFERENCES location (locationId)
) ENGINE=InnoDB DEFAULT CHARSET=utf8MB4;

CREATE TABLE IF NOT EXISTS user (
    userId bigint AUTO_INCREMENT,
    fireId varchar(255),
    email varchar(255),
    phone varchar(20),
    password varchar(255),
    startDate date,
    loginTime datetime,
    roleId tinyInt NOT NULL,
    isActive boolean NOT NULL,
    isSuspended boolean NOT NULL,
    PRIMARY KEY (userId),
    FOREIGN KEY (roleId) REFERENCES role (roleId)
) ENGINE=InnoDB DEFAULT CHARSET=utf8MB4;

CREATE TABLE IF NOT EXISTS profile (
    userId bigint,
    avatarId bigint,
    profileImgAmt int(1),
    firstname varchar(255) NOT NULL,
    lastname varchar(255) NOT NULL,
    birthday Date NOT NULL,
    gender varchar(10) NOT NULL,
    showMe varchar(10) NOT NULL,
    description text NOT NULL,
    company varchar(255),
    jobTitle varchar(255),
    school varchar(255),
    locationId bigint,
    PRIMARY KEY (userId),
    FOREIGN KEY (userId) REFERENCES user (userId) ON DELETE CASCADE,
    FOREIGN KEY (locationId) REFERENCES location (locationId)
) ENGINE=InnoDB DEFAULT CHARSET=utf8MB4;

CREATE TABLE IF NOT EXISTS paymentInfo (
    cardNumber varchar (16) NOT NULL,
    expireDate smallint NOT NULL,
    CCV smallint NOT NULL,
    userId bigint NOT NULL,
    locationId bigint NOT NULL,
    PRIMARY KEY (cardNumber),
    FOREIGN KEY (locationId) REFERENCES location (locationId),
    FOREIGN KEY (userId) REFERENCES user (userId) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8MB4;

CREATE TABLE IF NOT EXISTS reportedUser (
    userId bigint,
    reporterId bigint,
    reason text NOT NULL,
    reportTime datetime,
    PRIMARY KEY (userId, reporterId),
    FOREIGN KEY (userId) REFERENCES user (userId) ON DELETE CASCADE,
    FOREIGN KEY (reporterId) REFERENCES user (userId) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8MB4;

CREATE TABLE IF NOT EXISTS notification (
    notificationId bigint AUTO_INCREMENT,
    userId bigint NOT NULL,
    topic varchar(255) NOT NULL,
    title varchar(255) NOT NULL,
    content text NOT NULL,
    time datetime,
    isRead boolean,
    PRIMARY KEY (notificationId),
    FOREIGN KEY (userId) REFERENCES user (userId) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8MB4;

CREATE TABLE IF NOT EXISTS message (
    messageId bigint AUTO_INCREMENT,
    senderId bigint NOT NULL,
    receiverId bigint NOT NULL,
    attachmentId bigint,
    time datetime NOT NULL,
    content text NOT NULL,
    PRIMARY KEY (messageId),
    FOREIGN KEY (senderId) REFERENCES user (userId) ON DELETE CASCADE,
    FOREIGN KEY (receiverId) REFERENCES user (userId) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8MB4;

CREATE TABLE IF NOT EXISTS attachment (
    attachmentId bigint AUTO_INCREMENT,
    messageId bigint,
    userId bigint,
    attachmentType varchar(255) NOT NULL,
    attachmentContent mediumBlob NOT NULL,
    PRIMARY KEY (attachmentId),
    FOREIGN KEY (messageId) REFERENCES message (messageId) ON DELETE CASCADE,
    FOREIGN KEY (userId) REFERENCES profile (userId) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8MB4;

CREATE TABLE IF NOT EXISTS matches (
    userId bigint,
    targetId bigint,
    reactionId tinyint,
    PRIMARY KEY (userId, targetId),
    FOREIGN KEY (userId) REFERENCES user (userId) ON DELETE CASCADE,
    FOREIGN KEY (targetId) REFERENCES user (userId) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8MB4;

CREATE TABLE IF NOT EXISTS eventInvitation (
    eventInvitationId bigint AUTO_INCREMENT,
    eventId bigint,
    targetUserId bigint,
    reactionId tinyint,
    PRIMARY KEY (eventInvitationId),
    FOREIGN KEY (eventId) REFERENCES event (eventId) ON DELETE CASCADE,
    FOREIGN KEY (targetUserId) REFERENCES user (userId) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8MB4;

CREATE TABLE IF NOT EXISTS userEvent (
    userId bigint,
    eventId bigint,
    PRIMARY KEY (userId, eventId),
    FOREIGN KEY (userId) REFERENCES user (userId) ON DELETE CASCADE,
    FOREIGN KEY (eventId) REFERENCES event (eventId) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8MB4;

CREATE TABLE IF NOT EXISTS userPreference (
    userId bigint,
    preferenceId bigint,
    PRIMARY KEY (userId, preferenceId),
    FOREIGN KEY (userId) REFERENCES user (userId) ON DELETE CASCADE,
    FOREIGN KEY (preferenceId) REFERENCES preference (preferenceId) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8MB4;

CREATE TABLE IF NOT EXISTS userTag (
    userId bigint,
    preferenceId bigint,
    PRIMARY KEY (userId, preferenceId),
    FOREIGN KEY (userId) REFERENCES user (userId) ON DELETE CASCADE,
    FOREIGN KEY (preferenceId) REFERENCES preference (preferenceId) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8MB4;

CREATE TABLE IF NOT EXISTS userDevice (
    deviceId bigint,
    userId bigint,
    deviceToken varchar(255),
    registerTime datetime,
    PRIMARY KEY (deviceToken),
    FOREIGN KEY (userId) REFERENCES user (userId) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8MB4;