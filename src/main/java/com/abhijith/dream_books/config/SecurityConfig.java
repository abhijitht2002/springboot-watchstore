package com.abhijith.dream_books.config;

import com.abhijith.dream_books.service.UserService;
import com.abhijith.dream_books.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig{

    private UserDetailsService theUserDetailsService;

//    private PasswordEncoderConfig thePasswordEncoderConfig;
    private BCryptPasswordEncoder theBCryptPasswordEncoder;

    public SecurityConfig(UserDetailsService theUserDetailsService, BCryptPasswordEncoder theBCryptPasswordEncoder) {
        this.theUserDetailsService = theUserDetailsService;
//        this.thePasswordEncoderConfig = thePasswordEncoderConfig;
        this.theBCryptPasswordEncoder = theBCryptPasswordEncoder;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{

        http.authorizeHttpRequests(
                auth -> auth
                        .requestMatchers("/","/login","/shop","/shop/filter","/product","/css/**","/js/**","/assets/**","/uploads/**").permitAll()
                        .requestMatchers("/register","/wishlist-page","/cart-page","/User-dashboard").hasRole("USER")
                        .anyRequest().authenticated()
        ).formLogin(
                form -> form
                        .loginProcessingUrl("/login")
//                        .defaultSuccessUrl("/", true)
                        .permitAll()
        ).logout(
                logout -> logout.logoutUrl("/logout")
                        .permitAll()
        );

        return http.build();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
        auth.setUserDetailsService(theUserDetailsService);
//        auth.setPasswordEncoder(thePasswordEncoderConfig.passwordEncoder());
        auth.setPasswordEncoder(theBCryptPasswordEncoder);
        return auth;
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder =
                http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.authenticationProvider(authenticationProvider());
        return authenticationManagerBuilder.build();
    }
}
