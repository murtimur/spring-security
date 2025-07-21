package com.muratyildirim.spring_security.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@Configuration
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration {

	TokenFilter tokenFilter;

	public SecurityConfiguration(TokenFilter tokenFilter) {
		this.tokenFilter = tokenFilter;
	}

	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.cors(cors -> cors.disable()).csrf(csrf -> csrf.disable()).headers(headers -> headers.disable())
				.authorizeHttpRequests(auth -> auth
						.requestMatchers(PERMIT_PATHS).permitAll()
						.anyRequest().authenticated())
				.httpBasic(httpBasic -> httpBasic.authenticationEntryPoint(new AuthEntryPoint(null)));
		http.addFilterBefore(tokenFilter, UsernamePasswordAuthenticationFilter.class);

		return http.build();
	}

	public static final String[] PERMIT_PATHS = { 
			"/swagger-ui/**", "/v3/api-docs/**", "/swagger-ui.html", "/api/1.0/users", "/api/1.0/auth"
	};

}
