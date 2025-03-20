package com.convertino.hire.utils.routes;

public class AuthRoutes {
    public static final String BASE = ApiRoutes.BASE + "/auth";

    public static final String REGISTER = BASE + "/sign-up";
    public static final String REGISTER_GUEST = BASE + "/sign-up-guest";
    public static final String LOGIN = BASE + "/sign-in";
    public static final String USER = BASE + "/user";
    public static final String ALL = BASE + "/**";
}
