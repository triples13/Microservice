package com.xylem.hackathon;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;



@EnableZuulProxy
@SpringBootApplication
@EnableDiscoveryClient
public class SpringOauth2JwtApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringOauth2JwtApplication.class, args);
	}

}

