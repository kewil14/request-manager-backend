package org.ms.requestmanager.security;

public class SecurityParams {
    public static final String AUTH_HEADER = "Authorization";
    public static final String AUTH_HEADER_PREFIX="Bearer ";
    public static final String SECRET = "bk@#$*ùml_1234";
    public static final long EXPIRE_ACCESS_TOKEN = 6 * 60 * 60 * 1000; //6*60 min = 6 h
    public static final long EXPIRE_ACCESS_REFRESH_TOKEN = 24 * 60 * 60 * 1000; // 24*60 min = 24 h
    public static final String REFRESH_TOKEN_LINK = "/api/refreshToken";
}
