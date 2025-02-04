package com.elira.springSecurityBasic.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    /**
     * Chain of filters for security
     * CSRF (Cross-Site Request Forgery). Useful only for APIs REST without forms
     * Avoid to use session -> STATELESS
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                // Configure manually without Annotations with @EnableMethodSecurity
                .authorizeHttpRequests(authorize ->
                        authorize
                                // Configure any public endpoints
                                .requestMatchers("/v1/hello").permitAll()
                                // Configure any private endpoints
                                .requestMatchers("/v1/hello-secured").hasAuthority("CREATE")
                                // Configure rest endpoints without specific
                                .anyRequest().authenticated()
                )
                .httpBasic(Customizer.withDefaults());
        return httpSecurity.build();
    }

    /**
     * Manage the Authentication
     */
    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration authenticationConfiguration
    ) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    /**
     * Provider of authentication
     * DaoAuthenticationProvider is for getting from the Database
     */
    @Bean
    public AuthenticationProvider authenticationProvide(
            PasswordEncoder passwordEncoder,
            UserDetailsService userDetailsService
    ) {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setPasswordEncoder(passwordEncoder);
        authenticationProvider.setUserDetailsService(userDetailsService);
        return authenticationProvider;
    }

    /**
     * NoOpPasswordEncode return a PasswordEncoder that do not encrypt data
     * PasswordEncoderFactories allows to manage multiple algorithms to encrypt data
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        // Simulate that the database has this user
        List<UserDetails> userDetailsList = new ArrayList<>();
        userDetailsList.add(
                User.builder()
                        .username("root")
                        .password("root")
                        .roles("ADMIN")
                        .authorities("READ", "CREATE")
                        .build()
        );
        userDetailsList.add(
                User.builder()
                        .username("root1")
                        .password("root1")
                        .roles("USER")
                        .authorities("READ")
                        .build()
        );

        return new InMemoryUserDetailsManager(userDetailsList);
    }
}
