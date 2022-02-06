package com.ssafy.challympic.jwt;

public interface JwtProperties {
    String SECRET = "challympic"; // 우리 서버만 아는 비밀 값
    int EXPORATION_TIME = 60000 * 10; // 10일
    String TOKEN_PREFIX = "Bearer ";
    String HEADER_STRING = "Authorization";
}
