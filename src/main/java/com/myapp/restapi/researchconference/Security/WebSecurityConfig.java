
package com.myapp.restapi.researchconference.Security;

import com.myapp.restapi.researchconference.Filter.JwtRequestFilter;
import com.myapp.restapi.researchconference.Restservice.Impl.MyUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

@EnableWebSecurity
@Configuration
public class WebSecurityConfig {

    MyUserDetails userRestService;
    JwtRequestFilter jwtRequestFilter;

    @Autowired
    public WebSecurityConfig(MyUserDetails userRestService, JwtRequestFilter jwtRequestFilter) {
        this.userRestService = userRestService;
        this.jwtRequestFilter = jwtRequestFilter;
    }
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.authorizeHttpRequests((request) ->
                        request.requestMatchers("/api/auth/**").permitAll()
                        .requestMatchers("/").permitAll()
                                .requestMatchers("/api/users/**").hasAuthority("ADMIN")
                                .requestMatchers("api/papers/**").hasAuthority("AUTHOR, REVIEWER, CONFERENCE")
                                .requestMatchers("api/bids/**").hasAuthority("REVIEWER, CONFERENCE")
                                .requestMatchers("api/reviews/**").hasAuthority("AUTHOR, REVIEWER, CONFERENCE")
                                .requestMatchers("api/blacklist/**").hasAuthority("REVIEWER")
                                .anyRequest().authenticated())
                .cors().configurationSource(corsConfigurationSource())
                .and()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        ;
        httpSecurity.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
        return httpSecurity.build();
    }

//    @Bean
//    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
//        httpSecurity.authorizeHttpRequests((request) ->
//                        request.requestMatchers("/**").authenticated())
//                .cors().configurationSource(corsConfigurationSource())
//                .and()
//                .csrf().disable()
//                .formLogin()
//                .loginProcessingUrl("/processLogin")
//        ;
//        return httpSecurity.build();
//    }

//    @Bean
//    AuthenticationManager authenticationManager(){
//        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
//        authenticationProvider.setUserDetailsService(userRestService);
//        authenticationProvider.setPasswordEncoder(bCryptPasswordEncoder);
//
//        return new ProviderManager(authenticationProvider);
//    }

    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("*"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "PATCH"));
        configuration.setAllowedHeaders(Arrays.asList("Content-Type", "Authorization", "Content-Disposition", "Access-Control-Expose-Headers"));
        configuration.setExposedHeaders(Arrays.asList("Content-Disposition"));
        configuration.addAllowedOrigin("*");

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}