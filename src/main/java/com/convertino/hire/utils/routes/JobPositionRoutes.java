package com.convertino.hire.utils.routes;

public class JobPositionRoutes {
    public static final String BASE = ApiRoutes.BASE + "/job-positions";

    public static final String SAVE = BASE;
    public static final String FIND_ALL = BASE;
    public static final String FIND_BY_ID = BASE + "/{id}";
    public static final String UPDATE = BASE + "/{id}";
    public static final String DELETE = BASE + "/{id}";
    public static final String ALL = BASE + "/**";
}
