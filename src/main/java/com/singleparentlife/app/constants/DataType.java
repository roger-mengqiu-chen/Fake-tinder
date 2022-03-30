package com.singleparentlife.app.constants;


public enum DataType {

    /* Object types */
    PREFERENCE,
    PREFERENCE_WITH_ERROR,
    LIST_OF_PREFERENCE,

    TAG,
    TAG_WITH_ERROR,
    LIST_OF_TAGS,

    LOCATION,
    MESSAGE,
    USER,
    REPORTED_USER,
    PROFILE,
    LIST_OF_PROFILE,
    MATCH,
    LIST_OF_MATCH,
    REACTION,
    ATTACHMENT,
    ATTACHMENT_IDS,
    ATTACHMENT_LINKS,
    PREFERENCE_IDS,

    NOTIFICATION,
    EVENT,
    LIST_OF_EVENT,
    EVENT_INVITATION,
    LIST_OF_EVENT_INVITATION,
    IS_MATCH,

    /* Error types */
    SERVER_ERROR,
    INVALID_INPUT,
    INVALID_TOKEN,

    PREFERENCE_EXISTED,
    PREFERENCE_NOT_FOUND,

    USER_NOT_FOUND,

    DEVICE_NOT_FOUND,

    LOCATION_EXISTED,
    LOCATION_NOT_FOUND,

    MESSAGE_NOT_FOUND,

    PROFILE_NOT_FOUND,

    REACTION_NOT_FOUND,

    ATTACHMENT_NOT_FOUND,
    TOO_MANY_PROFILE_IMG,
    INVALID_IMAGE,

    NOTIFICATION_NOT_FOUND,

    MATCH_NOT_FOUND,

    EVENT_NOT_FOUND,

    EVENT_INVITATION_NOT_FOUND,

}

