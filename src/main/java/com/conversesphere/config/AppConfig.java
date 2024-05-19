package com.conversesphere.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.Arrays;
import java.util.Collections;
import jakarta.servlet.http.HttpServletRequest;

@Configuration
@EnableWebSecurity
public class AppConfig {

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.sessionManagement(sessionManagement -> {
			sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
			sessionManagement.invalidSessionUrl("/login");
		});
		http.csrf(AbstractHttpConfigurer::disable);
		http.authorizeHttpRequests(
				Authorize -> Authorize.requestMatchers("/api/**").authenticated().anyRequest().permitAll())
				.httpBasic(Customizer.withDefaults());
		http.addFilterBefore(new JwtTokenValidator(), BasicAuthenticationFilter.class);
		http.cors(cors -> cors.configurationSource(CorsConfigurationSource()));
		http.formLogin(form -> form.loginPage("/login").permitAll());

		return http.build();
	}

	private CorsConfigurationSource CorsConfigurationSource() {
		// TODO Auto-generated method stub
		return new CorsConfigurationSource() {

			@Override
			public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
				// TODO Auto-generated method stub
				CorsConfiguration cfg = new CorsConfiguration();
				cfg.setAllowedOrigins(Arrays.asList("http://localhost:5173"));
				cfg.setAllowedMethods(Collections.singletonList("*"));
				cfg.setAllowCredentials(true);
				cfg.setAllowedHeaders(Collections.singletonList("*"));
				cfg.setExposedHeaders(Arrays.asList("Authorization"));
				cfg.setMaxAge(3600L);
				return cfg;
			}
		};
	}

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

}
