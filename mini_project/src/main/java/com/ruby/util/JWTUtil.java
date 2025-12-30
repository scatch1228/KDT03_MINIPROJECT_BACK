package com.ruby.util;

import java.util.Date;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

public class JWTUtil {

	private static final long ACCESS_TOKEN_MSEC = 24*60*60*1000;
	private static final String JWT_KEY = "key";
	private static final String prefix = "Bearer ";
	private static final String usernameClaim = "username";	
	
	private static String getJWTSource(String token) {
		if(token.startsWith(prefix)) return token.replace(prefix, "");
		return token;
	}
	
	public static String getJWT(String username) {
		String src = JWT.create()
				.withClaim(usernameClaim, username)
				.withExpiresAt(new Date(System.currentTimeMillis()+ACCESS_TOKEN_MSEC))
				.sign(Algorithm.HMAC256(JWT_KEY));
		return prefix+src;
	}
	
	public static boolean isExpired(String token) {
		String tok = getJWTSource(token);
		return JWT.require(Algorithm.HMAC256(JWT_KEY)).build()
				.verify(tok).getExpiresAt().before(new Date());
	}
}
