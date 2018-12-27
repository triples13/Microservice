package com.xylem.hackathon.security;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.context.annotation.Bean;

import org.springframework.context.annotation.Configuration;

import org.springframework.core.annotation.Order;

import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;

import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import org.springframework.security.config.http.SessionCreationPolicy;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration

@Order(1)
public class SpringSecurityAdminFilter extends WebSecurityConfigurerAdapter {

	@Autowired

	public void configureGlobalSecurity(AuthenticationManagerBuilder auth) throws Exception {

		String password = passwordEncoder().encode("admin@springboot");

		auth.inMemoryAuthentication().passwordEncoder(passwordEncoder()).withUser("springbootadmin").password(password)
				.roles("ADMIN");

	}

	@Bean

	public BCryptPasswordEncoder passwordEncoder() {

		return new BCryptPasswordEncoder();

	}

	@Override

	protected void configure(HttpSecurity http) throws Exception {

		http.csrf().disable()

				

				.antMatcher("/actuator/**").authorizeRequests().anyRequest().hasRole("ADMIN")

				.and().httpBasic().and().authorizeRequests().antMatchers("/miss/**").authenticated()

				.and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);// We don't need
																									// sessions to be
																									// created.
	}

}