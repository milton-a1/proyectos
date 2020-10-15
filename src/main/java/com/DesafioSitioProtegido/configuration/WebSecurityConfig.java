package com.DesafioSitioProtegido.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	private AuthenticationSuccessHandler authenticationSuccessHandler;

	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	public WebSecurityConfig(AuthenticationSuccessHandler authenticationSuccessHandler) {
		System.out.println("websecurity");
		
		this.authenticationSuccessHandler = authenticationSuccessHandler;
	}

	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
//		System.out.println("this is to see if it matches: "+auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder()));

		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String existingPassword = ""; // Password entered by user
		String dbPassword = ""; // Load hashed DB password
		
		if (passwordEncoder.matches(existingPassword, dbPassword)) {
			System.out.println("passwords valid");
		} else {
			// Report error 
		}

	
	}

	@Override
	public void configure(HttpSecurity http) throws Exception {
		http.csrf().disable().authorizeRequests().antMatchers("/admin/**").hasAuthority("ADMIN").antMatchers("/client/**")
				.hasAuthority("CLIENT").antMatchers("/login").permitAll().anyRequest().authenticated().and().formLogin()
				.loginPage("/login").successHandler(authenticationSuccessHandler).failureUrl("/login?error=true")
				.usernameParameter("email").passwordParameter("password").and()
				.exceptionHandling().accessDeniedPage("/recurso-prohibido");
	}


	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}