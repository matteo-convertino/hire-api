package com.convertino.hire.utils.routes;

public class MessageRoutes {
    public static final String BASE = "/messages";

    public static final String SAVE = BASE + "/interview/{interviewId}";
    public static final String SAVE_REPLIES = WebSocketRoutes.QUEUE_INTERVIEWS + "{interviewId}";
//    public static final String ALL = BASE + "/**";
}
