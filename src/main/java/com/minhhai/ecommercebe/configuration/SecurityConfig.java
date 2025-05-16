package com.minhhai.ecommercebe.configuration;

import com.minhhai.ecommercebe.configuration.filter.JwtFilter;
import com.minhhai.ecommercebe.configuration.securityCustom.CustomAuthEntryPoint;
import com.minhhai.ecommercebe.configuration.securityModel.PublicUrl;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.minhhai.ecommercebe.service.JpaUserDetailsService;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
@EnableMethodSecurity
@Getter
public class SecurityConfig {
    private final JpaUserDetailsService userDetailsService;
    private final JwtFilter jwtFilter;
    private final CustomAuthEntryPoint authEntryPoint;

    @Bean
    public SecurityFilterChain configure(@NonNull HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(
                        authorizeRequests -> authorizeRequests
                                .requestMatchers(PublicUrl.WHITE_LIST.toArray(new String[0])).permitAll()
                                .anyRequest().authenticated())

                .sessionManagement(
                        manager -> manager
                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(provider())
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling(exception -> exception
                        .authenticationEntryPoint(authEntryPoint)
                );

        return http.build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return webSecurity -> webSecurity.ignoring()
                .requestMatchers("/actuator/**", "/v3/**", "/webjars/**", "/swagger-ui*/*swagger-initializer.js",
                        "/swagger-ui*/**");
    }

    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public AuthenticationProvider provider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(getPasswordEncoder());
        return provider;
    }
}
