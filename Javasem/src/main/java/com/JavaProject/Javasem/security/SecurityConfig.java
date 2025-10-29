package com.JavaProject.Javasem.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails admin = User.withDefaultPasswordEncoder()
                .username("admin")
                .password("admin123")
                .roles("ADMIN")
                .build();

        UserDetails faculty = User.withDefaultPasswordEncoder()
                .username("faculty")
                .password("faculty123")
                .roles("FACULTY")
                .build();

        UserDetails student = User.withDefaultPasswordEncoder()
                .username("student")
                .password("student123")
                .roles("STUDENT")
                .build();

        return new InMemoryUserDetailsManager(admin, faculty, student);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // Optional: disable CSRF for testing (you can enable later)
                .csrf(csrf -> csrf.disable())

                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/css/**", "/login").permitAll()
                        .requestMatchers("/attendance/**").hasAnyRole("FACULTY", "ADMIN")
                        .requestMatchers("/performance/**").hasAnyRole("FACULTY", "ADMIN", "STUDENT")
                        .requestMatchers("/feedback/**").hasAnyRole("FACULTY", "ADMIN")
                        .anyRequest().authenticated()
                )

                .formLogin(login -> login
                        .loginPage("/login")                // your custom login page
                        .loginProcessingUrl("/login")       // <--- important for form submission
                        .defaultSuccessUrl("/", true)       // redirect to index.html on success
                        .permitAll()
                )

                .logout(logout -> logout
                        .logoutSuccessUrl("/login?logout")
                        .permitAll()
                );

        return http.build();
    }
}
