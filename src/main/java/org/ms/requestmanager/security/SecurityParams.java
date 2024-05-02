package org.ms.requestmanager.security;

public class SecurityParams {
    public static final String AUTH_HEADER = "Authorization";
    public static final String AUTH_HEADER_PREFIX="Bearer ";
    public static final String SECRET = "bk@#$*ùml_1234";
    public static final long EXPIRE_ACCESS_TOKEN = 120*1*60*1000; //120 min = 2 h
    public static final long EXPIRE_ACCESS_REFRESH_TOKEN = 360*1*60*1000; // 360 min = 6 h
    public static final String REFRESH_TOKEN_LINK = "/api/refreshToken";
}
