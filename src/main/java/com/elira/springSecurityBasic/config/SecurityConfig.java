package com.elira.springSecurityBasic.config;

import com.elira.springSecurityBasic.service.UserDetailServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

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
                                // Configure any public endpoints permitAll()
                                // Configure any private endpoints hasAuthority("CREATE")
                                // Configure rest endpoints without specific authenticated()

                                // BASE ON ROLES
                                // .requestMatchers(HttpMethod.GET, "/v1/auth/get").hasRole("ADMIN")

                                // BASED ON AUTHORITIES
                                .requestMatchers(HttpMethod.GET, "/v1/auth/get").hasAuthority("READ")
                                .requestMatchers(HttpMethod.POST, "/v1/auth/post").hasAnyAuthority("CREATE", "DELETE")
                                .requestMatchers(HttpMethod.PUT, "/v1/auth/put").hasAnyAuthority("CREATE")
                                .requestMatchers(HttpMethod.DELETE, "/v1/auth/delete").hasAuthority("DELETE")
                                .requestMatchers(HttpMethod.PATCH, "/v1/auth/patch").hasAuthority("UPDATE")

                                // OTHER CASE DANY ACCESS
                                .anyRequest().denyAll()
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
            UserDetailServiceImpl userDetailsService
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
        return new BCryptPasswordEncoder();
    }
}
