package com.convertino.hire.utils.routes;

public class SkillRoutes {
    public static final String BASE = ApiRoutes.BASE + "/skills";

    public static final String SAVE = BASE;
    public static final String FIND_BY_ID = BASE + "/{id}";
    public static final String FIND_BY_JOB_POSITION_ID = BASE + "/job-position/{id}";
    public static final String UPDATE = BASE + "/{id}";
    public static final String DELETE = BASE + "/{id}";
    public static final String ALL = BASE + "/**";
}
