package com.company.todos.security;

import com.company.todos.AppContext;
import com.company.todos.AppProperties;

public class Constants {
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String AUTH_HEADER_PREFIX = "Authorization";
    public static final String USERID_HEADER_PREFIX = "UserId";
    public static final String SIGN_UP_URL = "/users";
    public static final String USER_LOGIN_URL = "/users/login";
    public static final String UNAUTHETICATED_MSG = "Please provide token to access";


    public static String getTokenSecret() {
        AppProperties appProperties = (AppProperties) AppContext.getBean("appProperties");
        return appProperties.getTokenSecret().orElse("23geb7sdhsd5sd4");
    }

    public static long getTokenExpirationTimeInSeconds() {
        AppProperties appProperties = (AppProperties) AppContext.getBean("appProperties");
        return Long.parseLong(appProperties.getTokenExpirationTime().
                orElse("86400")); //seconds, 10 days
    }
}
