package com.notibook.notibookbackend.global.config;

import com.notibook.notibookbackend.domain.user.repository.UserRepository;
import com.notibook.notibookbackend.global.security.JwtSecurityFilter;
import com.notibook.notibookbackend.global.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    private String[] swaggerPatterns() {
        return new String[]{
                "/swagger-ui/**", "/v2/api-docs", "/swagger-resources/**"
        };
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();

        CorsConfiguration configuration = new CorsConfiguration().applyPermitDefaultValues();
        configuration.addAllowedMethod("PATCH");
        configuration.addAllowedMethod("DELETE");

        http.cors().configurationSource(request -> configuration);

        http.authorizeHttpRequests()
                .antMatchers(swaggerPatterns()).permitAll()
                .antMatchers("/auth/**").permitAll()
                .antMatchers("/books/**").authenticated()
                .antMatchers("/user/**").authenticated();

        http.addFilterBefore(new JwtSecurityFilter(jwtUtil, userRepository),
                UsernamePasswordAuthenticationFilter.class);
    }
}
