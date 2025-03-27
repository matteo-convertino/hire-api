package com.convertino.hire.utils.routes;

public class MessageRoutes {
    public static final String WS_BASE = "/messages";
    public static final String API_BASE = ApiRoutes.BASE + "/messages";

    public static final String FIND_ALL_BY_INTERVIEW_ID = API_BASE + "/interview/{interviewId}";
    public static final String SAVE = WS_BASE + "/interview/{interviewId}";
    public static final String SAVE_REPLIES = WebSocketRoutes.QUEUE_INTERVIEWS + "{interviewId}";
    public static final String ALL = API_BASE + "/**";
}
