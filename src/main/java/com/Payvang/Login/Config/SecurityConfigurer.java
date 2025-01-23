package com.Payvang.Login.Config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.Payvang.Login.Filters.JwtRequestFilter;




@Configuration
public class SecurityConfigurer {

    private final JwtRequestFilter jwtRequestFilter;
    
    @Value("${mobile.sms.api}")
    private String simulation;
    
    public SecurityConfigurer(JwtRequestFilter jwtRequestFilter) {
        this.jwtRequestFilter = jwtRequestFilter;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
            throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
    

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    	System.out.println(simulation);
    	//change the configuration as per the simulation
    	if ("yes".equalsIgnoreCase(simulation))  {
            http.csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(authz -> authz
                    .requestMatchers("/auth/merchantSignUp","api/auth/login","api/auth/merchant","api/auth/random-password").permitAll() // Allow public access
                    .requestMatchers("/api/other").hasRole("ADMIN") // Allow only ADMIN1 to access this endpoint          
                    .anyRequest().authenticated()) 
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .exceptionHandling(handler -> handler.authenticationEntryPoint(new AuthEntryPoint()));

            http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
    		
    		
    		
    	}
    	else {

            http.csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(authz -> authz
                    .requestMatchers("/auth/merchantSignUp","api/auth/login","/auth/otp/generate","/auth/otp/verify","api/auth/merchant","/auth/otp/email").permitAll() // Allow public access
                    .requestMatchers("/api/other").hasRole("ADMIN") // Allow only ADMIN1 to access this endpoint          
                    .anyRequest().authenticated()) 
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .exceptionHandling(handler -> handler.authenticationEntryPoint(new AuthEntryPoint()));

            http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
    		
    	}
    	
    	
    	

        return http.build();
}

}
