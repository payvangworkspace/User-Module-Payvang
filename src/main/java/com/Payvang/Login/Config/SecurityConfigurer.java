package com.Payvang.Login.Config;

import java.util.List;

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
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.Payvang.Login.Filters.JwtRequestFilter;




@Configuration
public class SecurityConfigurer {

//    private final JwtRequestFilter jwtRequestFilter;
//
//    public SecurityConfigurer(JwtRequestFilter jwtRequestFilter) {
//        this.jwtRequestFilter = jwtRequestFilter;
//    }
//
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//
//    @Bean
//    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
//            throws Exception {
//        return authenticationConfiguration.getAuthenticationManager();
//    }
//
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(authz -> authz
                .requestMatchers("/auth/merchantSignUp","/auth/otp/generate","/auth/otp/verify","/auth/otp/email","api/**").permitAll() // Allow public access
                .requestMatchers("/auth/other").hasRole("ADMIN") // Allow only ADMIN1 to access this endpoint          
                .anyRequest().authenticated()) 
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .exceptionHandling(handler -> handler.authenticationEntryPoint(new AuthEntryPoint()));

        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
}
	
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
    
    
    
    //OLD Configuration
    

//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
////    	System.out.println(simulation);
//    	//change the configuration as per the simulation
//    	if ("yes".equalsIgnoreCase(simulation))  {
//            http.csrf(csrf -> csrf.disable())
//                .authorizeHttpRequests(authz -> authz
//                    .requestMatchers("/auth/merchantSignUp","api/auth/login","api/auth/merchant","api/auth/random-password").permitAll() // Allow public access
//                    .requestMatchers("/api/other").hasRole("ADMIN") // Allow only ADMIN1 to access this endpoint          
//                    .anyRequest().authenticated()) 
//                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//                .exceptionHandling(handler -> handler.authenticationEntryPoint(new AuthEntryPoint()));
//
//            http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);	
//    	}
//    	else {
////,"/api/auth/changepassword"
//            http.csrf(csrf -> csrf.disable())
//                .authorizeHttpRequests(authz -> authz
//                    .requestMatchers("/auth/merchantSignUp","api/auth/login","/auth/otp/generate","/auth/otp/verify","api/auth/merchant","/auth/otp/email","/api/notification/preferences/**","/branding/**","/api/auth/changepassword").permitAll() // Allow public access
//                    .requestMatchers("/api/other").hasAnyRole("ADMIN","MERCHANT") // Allow only ADMIN1 to access this endpoint          
//                    .anyRequest().authenticated()) 
//                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//                .exceptionHandling(handler -> handler.authenticationEntryPoint(new AuthEntryPoint()));
//
//            http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
//    		
//    	}
//    	
//        return http.build();
//}
    
  
    
    //NEW Configuration after Scopes
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
////    	System.out.println(simulation);
//    	//change the configuration as per the simulation
//    	if ("yes".equalsIgnoreCase(simulation))  {
//            http.csrf(csrf -> csrf.disable())
//                .authorizeHttpRequests(authz -> authz
//                    .requestMatchers("api/auth/login","api/auth/random-password").permitAll() // Allow public access
//                    .requestMatchers("/api/other").hasAnyRole("ADMIN","MERCHANT","RESELLER") // Allow only ADMIN1 to access this endpoint     
//                    .requestMatchers("/auth/merchantSignUp").hasRole("ADMIN")
//                    
//                    
//                    .anyRequest().authenticated()) 
//                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//                .exceptionHandling(handler -> handler.authenticationEntryPoint(new AuthEntryPoint()));
//
//            http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);	
//    	}
//    	else {
////,"/api/auth/changepassword"
//            http.csrf(csrf -> csrf.disable())
//                .authorizeHttpRequests(authz -> authz
//                    .requestMatchers("api/auth/login","/auth/otp/verify","/auth/otp/email","api/auth/merchant").permitAll() // Allow public access
//                    .requestMatchers("/api/other").hasAnyRole("ADMIN","MERCHANT","RESELLER") // Allow only ADMIN1 to access this endpoint  
//                    .requestMatchers("/auth/merchantSignUp").hasRole("ADMIN")
////                    .requestMatchers("/auth/merchantSignUp").hasRole("ADMIN")
//                    .anyRequest().authenticated()) 
//                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//                .exceptionHandling(handler -> handler.authenticationEntryPoint(new AuthEntryPoint()));
//
//            http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
//    		
//    	}
//    	
//        return http.build();
//}
//   
    
	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
	    CorsConfiguration configuration = new CorsConfiguration();
	    configuration.setAllowedOrigins(List.of("http://localhost:3000"));
	    configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
	    configuration.setAllowedHeaders(List.of("Authorization", "Content-Type"));
	    configuration.setAllowCredentials(true);
	    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
	    source.registerCorsConfiguration("/**", configuration);
	    return source;
	}
    
    
	
	
	
	
	
	
	
	

}
