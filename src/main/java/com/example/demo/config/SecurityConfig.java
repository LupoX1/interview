package com.example.demo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
				.antMatchers(HttpMethod.GET, "/").permitAll().and()
			.authorizeRequests()
				.antMatchers(HttpMethod.GET, "/products").hasAnyRole("generic", "employee")
				.antMatchers(HttpMethod.GET, "/products/**").hasAnyRole("generic", "employee")
				.antMatchers(HttpMethod.POST, "/products").hasRole("employee")
				.antMatchers(HttpMethod.DELETE, "/products/**").hasRole("employee").and()
			.cors().and()
			.csrf().disable();
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		
		PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
		auth.inMemoryAuthentication()
			.withUser("user").password(encoder.encode("password")).roles("generic")
		.and()
			.withUser("employee").password(encoder.encode("password")).roles("employee");
	}
	
}
