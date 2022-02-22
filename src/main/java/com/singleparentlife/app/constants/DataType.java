package com.singleparentlife.app.constants;


public enum DataType {

    /* Object types */
    PREFERENCE,
    PREFERENCE_WITH_ERROR,
    TAG,
    TAG_WITH_ERROR,
    LOCATION,
    MESSAGE,
    USER,
    REPORTED_USER,
    PROFILE,
    MATCH,
    REACTION,
    ATTACHMENT,
    ATTACHMENT_IDS,
    EVENT_INVITATION,
    LIST_OF_EVENT_INVITATION,
    PREFERENCE_IDS,
    STATUS_MESSAGE,
    NOTIFICATION,

    /* Error types */
    SERVER_ERROR,
    INVALID_INPUT,

    PREFERENCE_EXISTED,
    PREFERENCE_NOT_FOUND,

    USER_NOT_FOUND,
    INVALID_USER,

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

}

