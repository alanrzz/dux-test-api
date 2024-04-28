package com.dux.software.futbol.api.security;

import static io.jsonwebtoken.SignatureAlgorithm.HS256;
import static io.jsonwebtoken.io.Decoders.BASE64;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JwtService {

  @Value("${spring.jwt.secretKey}")
  private String secretKey;

  @Value("${spring.jwt.tokenExpirationMillis}")
  private long tokenExpirationMillis;

  public String getToken(UserDetails userDetails) {
    return getToken( new HashMap<>(), userDetails );
  }

  private String getToken(Map<String, Object> extractClaims, UserDetails userDetails) {
    return Jwts.builder()
        .setClaims( extractClaims )
        .setSubject( userDetails.getUsername() )
        .setIssuedAt( new Date(System.currentTimeMillis()) )
        .setExpiration( new Date(System.currentTimeMillis() + tokenExpirationMillis) )
        .signWith( getKey(), HS256 )
        .compact();
  }

  private Key getKey() {
    byte[] keyBytes = BASE64.decode( String.valueOf( secretKey ) );
    return Keys.hmacShaKeyFor( keyBytes );
  }

  public String getUsernameFromToken(String token) {
    return getClaim( token, Claims::getSubject );
  }

  public boolean isTokenValid(String token, UserDetails userDetails) {
    String username = getUsernameFromToken( token );
    return username.equals( userDetails.getUsername() ) && !isTokenExpired( token );
  }

  private Claims getAllClaims(String token) {
    return Jwts
        .parserBuilder()
        .setSigningKey( getKey() )
        .build()
        .parseClaimsJws( token )
        .getBody();
  }

  public <T> T getClaim(String token, Function<Claims, T> claimsTFunction) {
    Claims claims = getAllClaims( token );
    return claimsTFunction.apply( claims );
  }

  private Date getExpiration(String token) {
    return getClaim( token, Claims::getExpiration );
  }

  private boolean isTokenExpired(String token) {
    return getExpiration( token ).before( new Date() );
  }

}
