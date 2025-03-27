package com.convertino.hire.utils.routes;

public class ReportRoutes {
    public static final String BASE = ApiRoutes.BASE + "/reports";

    public static final String FIND_BY_ID = BASE + "/{id}";
    public static final String FIND_BY_INTERVIEW_ID = BASE + "/interview/{interviewId}";
    public static final String FIND_BY_USER = BASE + "/user";
    public static final String ALL = BASE + "/**";
}
