package com.lg.uc.utils;

import com.lg.uc.entity.Member;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.util.StringUtils;

import java.util.Date;

/**
 * @author helen
 * @since 2019/3/16
 */
public class JwtUtils {


	public static final String APPSECRET = "guli123456";

	/**
	 * 生成jwt
	 * @param member
	 * @return
	 */
	public static String genJsonWebToken(Member member){

	if(member == null
			|| StringUtils.isEmpty(member.getId())
			|| StringUtils.isEmpty(member.getNickname())
			|| StringUtils.isEmpty(member.getAvatar())){
		return null;
	}

		String token = Jwts.builder().setSubject("guli")
				.claim("id", member.getId())
				.claim("nickname", member.getNickname())
				.claim("avatar", member.getAvatar())
				.setIssuedAt(new Date())
				.setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 30))
				.signWith(SignatureAlgorithm.HS256, APPSECRET).compact();

		return token;
	}

	/**
	 * 校验jwt
	 * @param token
	 * @return
	 */
	public static Claims checkJwt(String token){
		Claims claims = Jwts.parser().setSigningKey(APPSECRET).parseClaimsJws(token).getBody();
		return claims;
	}

	/**
	 * 测试jwt的生成
	 */
	private static void testGenJsonWebToken(){
		Member member = new Member();
		member.setId("999");
		member.setNickname("helen");
		member.setAvatar("http://hahaha/hahah.png");

		String token = JwtUtils.genJsonWebToken(member);
		System.out.println(token);
	}


	/**
	 * 测试jwt的校验
	 */
	public static void testCheckJwt(){

		String token = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJndWxpIiwiaWQiOiI5OTkiLCJuaWNrbmFtZSI6ImhlbGVuIiwiYXZhdGFyIjoiaHR0cDovL2hhaGFoYS9oYWhhaC5wbmciLCJpYXQiOjE1NTYzNzA3ODMsImV4cCI6MTU1NjM3MjU4M30.Rf2IGwRl9S8lxw1QgfkmLjC_q8bn_62J_HW4NNoGijA";

		Claims claims = JwtUtils.checkJwt(token);
		String id = (String)claims.get("id");
		String nickname = (String)claims.get("nickname");
		String avatar = (String)claims.get("avatar");
		System.out.println(id);
		System.out.println(nickname);
		System.out.println(avatar);
	}


	public static void main(String[] args){
	    testGenJsonWebToken();

	    testCheckJwt();
	}
}
