package com.convertino.hire.utils.routes;

public class WebAuthRoutes {
    public static final String BASE = ApiRoutes.BASE + "/web/auth";

    public static final String REGISTER = BASE + "/sign-up";
    public static final String REGISTER_GUEST = BASE + "/sign-up-guest";
    public static final String LOGIN = BASE + "/sign-in";
    public static final String ALL = BASE + "/**";
}
