package com.xylem.hackathon.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.xylem.hackathon.config.JwtAuthenticationResponse;
import com.xylem.hackathon.config.JwtTokenProvider;
import com.xylem.hackathon.config.LoginRequest;
import com.xylem.hackathon.config.RegisterNewUser;
import com.xylem.hackathon.domain.Role;
import com.xylem.hackathon.domain.RoleName;
import com.xylem.hackathon.domain.User;
import com.xylem.hackathon.repository.RoleRepository;
import com.xylem.hackathon.repository.UserRepository;



import javax.validation.Valid;
import java.net.URI;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/api/auth")
//@CrossOrigin(origins = {"http://localhost:4200","http://ec2-18-233-36-59.compute-1.amazonaws.com:8080"})
public class AuthController {

	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	UserRepository userRepository;

	@Autowired
	RoleRepository roleRepository;

	@Autowired
	JwtTokenProvider tokenProvider;

	@Autowired
	PasswordEncoder passwordEncoder;

	@PostMapping("/signin")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

		Authentication authentication = authenticationManager.authenticate(

				new UsernamePasswordAuthenticationToken(loginRequest.getUsernameOrEmail(), loginRequest.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);

		String jwt = tokenProvider.generateToken(authentication);
		String refreshToken = tokenProvider.createRefreshToken(authentication);
		
		
		return ResponseEntity.ok(new JwtAuthenticationResponse(jwt, refreshToken));
	}

	@PostMapping("/registerNewUser")
	@PreAuthorize("hasRole('ASSET_MANAGER')")
	public ResponseEntity<?> registerUser(@Valid @RequestBody RegisterNewUser signUpRequest) {
		if (userRepository.existsByUsername(signUpRequest.getUsername())) {
			return new ResponseEntity(new ApiResponse(false, "Username is already taken!"), HttpStatus.BAD_REQUEST);
		}

		if (userRepository.existsByEmail(signUpRequest.getEmail())) {
			return new ResponseEntity(new ApiResponse(false, "Email Address already in use!"), HttpStatus.BAD_REQUEST);
		}

		// Creating user's account
		User user = new User(signUpRequest.getFirstname(),signUpRequest.getLastname(), signUpRequest.getUsername(), signUpRequest.getEmail(),
				signUpRequest.getPassword());

		user.setPassword(passwordEncoder.encode(user.getPassword()));

		Set<Role> userRoles = new HashSet<Role>();
		signUpRequest.getRoles().forEach(item -> {
		
			if (item.equalsIgnoreCase("ADMIN")) {
				Role userRole = roleRepository.findByName(RoleName.ROLE_ADMIN).orElseThrow(()->
				new RoleNotSetException("Asset Manager Role not set"));
				userRoles.add(userRole);
			}
			if (item.equalsIgnoreCase("USER")) {
				Role userRole = roleRepository.findByName(RoleName.ROLE_USER).orElseThrow(()->
				new RoleNotSetException("Application User Role Not Set"));
				userRoles.add(userRole);
			}

		}

		);

		user.setRoles(userRoles);

		User result = userRepository.save(user);

		URI location = ServletUriComponentsBuilder.fromCurrentContextPath().path("/users/{username}")
				.buildAndExpand(result.getUsername()).toUri();

		return ResponseEntity.created(location).body(new ApiResponse(true, "User registered successfully"));
	}
}
