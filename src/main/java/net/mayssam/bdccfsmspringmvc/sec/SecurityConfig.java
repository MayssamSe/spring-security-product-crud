package net.mayssam.bdccfsmspringmvc.sec;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public InMemoryUserDetailsManager inMemoryUserDetailsManager() {
        PasswordEncoder encoder = passwordEncoder();
        return new InMemoryUserDetailsManager(
                User.withUsername("user1").password(passwordEncoder().encode("1234")).roles("USER").build(),
                User.withUsername("user2").password(passwordEncoder().encode("1234")).roles("USER").build(),
                User.withUsername("admin").password(passwordEncoder().encode("1234")).roles("USER","ADMIN").build()
        );
    }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .formLogin(fl ->fl.loginPage("/login").permitAll())
                .authorizeHttpRequests(ar ->ar.requestMatchers("/user/**").hasRole("USER"))
                .authorizeHttpRequests(ar ->ar.requestMatchers("/admin/**").hasRole("ADMIN"))
                .authorizeHttpRequests(ar ->ar.requestMatchers("/public/**","/webjar/**").permitAll())
                .authorizeHttpRequests(ar ->ar.anyRequest().authenticated())
                .exceptionHandling(eh ->eh.accessDeniedPage("/notAuthorized"))
                .build();
    }


}
