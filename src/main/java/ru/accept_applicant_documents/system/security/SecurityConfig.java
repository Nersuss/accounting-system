package ru.accept_applicant_documents.system.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import ru.accept_applicant_documents.system.enums.Roles;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    MyUserDetailsService myUserDetailsService;

    @Bean
    public UserDetailsService users() {
        UserDetails user = User.builder()
                .username("1")
                .password(bCryptPasswordEncoder().encode("1"))
                .roles("USER")
                .build();
        UserDetails admin = User.builder()
                .username("2")
                .password(bCryptPasswordEncoder().encode("2"))
                .roles("USER", "ADMIN")
                .build();
        return new InMemoryUserDetailsManager(user, admin);
    }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> {csrf.disable();
                })
                .cors(cors -> cors.disable())
                .authorizeHttpRequests(auth -> {
                    auth.requestMatchers("/login", "/register", "/").permitAll();
                    auth.requestMatchers("/admin/**").hasAuthority(Roles.ADMIN.name());
                    auth.requestMatchers("/applicant/**").hasAuthority(Roles.APPLICANT.name());
                    //auth.anyRequest().authenticated();
                })
                .formLogin(form -> form
                        .defaultSuccessUrl("/applicant/lk", true)
                        .permitAll()
                )
                .userDetailsService(myUserDetailsService)
                .httpBasic(Customizer.withDefaults());

        return http.build();
    }


    @Bean
    BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

}