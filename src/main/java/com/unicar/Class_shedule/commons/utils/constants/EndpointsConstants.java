package com.unicar.Class_shedule.commons.utils.constants;

public class EndpointsConstants {

    private EndpointsConstants() {
        throw new IllegalStateException("Utility class");
    }

    public static final String ENDPOINT_BASE_API = "/api/v1";
    public static final String ENDPOINT_LOGIN = ENDPOINT_BASE_API + "/login";
    public static final String ENDPOINT_SIGNUP = ENDPOINT_BASE_API + "/signup";
    public static final String ENDPOINT_COURSES = ENDPOINT_BASE_API + "/courses";
    public static final String ENDPOINT_STUDENT = ENDPOINT_BASE_API + "/student";
    public static final String ENDPOINT_TEACHER = ENDPOINT_BASE_API + "/teacher";


}
