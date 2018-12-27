package com.xylem.hackathon.refreshTokenendpoint;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.xylem.hackathon.config.JwtAccessTokenResponse;
import com.xylem.hackathon.config.JwtAuthenticationResponse;
import com.xylem.hackathon.config.JwtSettings;
import com.xylem.hackathon.config.JwtTokenProvider;
import com.xylem.hackathon.domain.User;
import com.xylem.hackathon.service.CustomUserDetailsService;
import com.xylem.hackathon.service.UserPrincipal;

@RestController
public class RefreshTokenEndpoint {
	@Autowired
	private JwtTokenProvider tokenFactory;
	@Autowired
	private JwtSettings jwtSettings;
	@Autowired
	private CustomUserDetailsService userService;
	@Autowired
	private TokenVerifier tokenVerifier;


	private JwtAccessTokenResponse  authenticationRespone;

	@Autowired
	@Qualifier("jwtHeaderTokenExtractor")
	private TokenExtractor tokenExtractor;

	@RequestMapping(value = "/api/auth/token", method = RequestMethod.GET, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public @ResponseBody JwtAccessTokenResponse refreshToken(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		String tokenPayload = tokenExtractor.extract(request.getHeader("Authorization"));

		RawAccessJwtToken rawToken = new RawAccessJwtToken(tokenPayload);
		RefreshToken refreshToken = RefreshToken.create(rawToken, jwtSettings.getTokenSigningKey())
				.orElseThrow(() -> new InvalidJwtToken());

		String jti = refreshToken.getJti();
		if (!tokenVerifier.verify(jti)) {
			throw new InvalidJwtToken();
		}

		String subject = refreshToken.getSubject();

		UserDetails user = (UserPrincipal) userService.loadUserByUsername(subject);
		

		
		 authenticationRespone=new JwtAccessTokenResponse(tokenFactory.createAccessJwtToken(user.getUsername(), user.getAuthorities()));
		
		
		return authenticationRespone;
	}
}