package com.convertino.hire.utils.routes;

public class InterviewRoutes {
    public static final String BASE = ApiRoutes.BASE + "/interviews";

    public static final String SAVE = BASE;
    public static final String FIND_ALL_BY_USER = BASE + "/user";
    public static final String FIND_BY_ID = BASE + "/{id}";
    public static final String FIND_BY_JOB_POSITION_ID = BASE + "/job-position/{jobPositionId}";
    public static final String ALL = BASE + "/**";
}
