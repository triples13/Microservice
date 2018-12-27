package com.xylem.hackathon.config;

import io.jsonwebtoken.*;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import com.xylem.hackathon.service.UserPrincipal;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;


@Component
public class JwtTokenProvider {

    private static final Logger logger = LoggerFactory.getLogger(JwtTokenProvider.class);
    
    @Autowired
    private JwtSettings settings;

    


    /**
     * @param authentication
     * @return
     */
    public String generateToken(Authentication authentication) {
    	
    	UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
    	LocalDateTime currentTime = LocalDateTime.now();
        Claims claims=Jwts.claims().setSubject(userPrincipal.getUsername());
        claims.put("scopes", userPrincipal.getAuthorities().stream().map(s -> s.toString()).collect(Collectors.toList()));

        return Jwts.builder()
               .setClaims(claims).setIssuer(settings.getTokenIssuer())
               .setIssuedAt(Date.from(currentTime.atZone(ZoneId.systemDefault()).toInstant()))
               .setExpiration(Date.from(currentTime
                       .plusMinutes(settings.getTokenExpirationTime())
                       .atZone(ZoneId.systemDefault()).toInstant()))
            		.signWith(SignatureAlgorithm.HS512, settings.getTokenSigningKey())
            	        .compact();
        
               
    }
    /*getting the calims and sigining key and refresh expiration and create a jwt access token using  these
     */
    public String createRefreshToken(Authentication authentication) {
    	UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        if (StringUtils.isBlank(userPrincipal.getUsername())) {
            throw new IllegalArgumentException("Cannot create JWT Token without username");
        }

        LocalDateTime currentTime = LocalDateTime.now();

        Claims claims = Jwts.claims().setSubject(userPrincipal.getUsername());
        claims.put("scopes", Arrays.asList(Scopes.REFRESH_TOKEN.authority()));

        return Jwts.builder()
          .setClaims(claims)
          .setIssuer(settings.getTokenIssuer())
          .setId(UUID.randomUUID().toString())
          .setIssuedAt(Date.from(currentTime.atZone(ZoneId.systemDefault()).toInstant()))
          .setExpiration(Date.from(currentTime
                  .plusMinutes(settings.getRefreshTokenExpTime())
                  .atZone(ZoneId.systemDefault()).toInstant()))
          .signWith(SignatureAlgorithm.HS512, settings.getTokenSigningKey())
        .compact();

        
    }
    /*getting the calims and sigining key and token expiration and create a jwt access token using  these
     */
public String createAccessJwtToken(String username, Collection<? extends GrantedAuthority> collection) {
    	
    	
	LocalDateTime currentTime = LocalDateTime.now();
        Claims claims=Jwts.claims().setSubject(username);
        claims.put("scopes", collection);

        return Jwts.builder()
               .setClaims(claims).setIssuer(settings.getTokenIssuer())
               .setIssuedAt(Date.from(currentTime.atZone(ZoneId.systemDefault()).toInstant()))
               .setExpiration(Date.from(currentTime
                       .plusMinutes(settings.getTokenExpirationTime())
                       .atZone(ZoneId.systemDefault()).toInstant()))
            		.signWith(SignatureAlgorithm.HS512, settings.getTokenSigningKey())
            	        .compact();
        
               
    }
//get user id from claims 
 public String getUserIdFromJWT(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(settings.getTokenSigningKey())
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();
    }
/*
 * validate jwt token Jwts parser 
 */
    public boolean validateToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(settings.getTokenSigningKey()).parseClaimsJws(authToken);
            return true;
        } catch (SignatureException ex) {
            logger.error("Invalid JWT signature");
        } catch (MalformedJwtException ex) {
            logger.error("Invalid JWT token");
        } catch (ExpiredJwtException ex) {
            logger.error("Expired JWT token");
        } catch (UnsupportedJwtException ex) {
            logger.error("Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
            logger.error("JWT claims string is empty.");
        }
        return false;
    }
}
