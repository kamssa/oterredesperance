package com.operantic.contactqueryservice.config;


import com.operantic.contactqueryservice.config.jwt.JwtEntrypoint;
import com.operantic.contactqueryservice.config.jwt.JwtFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    JwtFilter jwtFilter;

    @Autowired
    JwtEntrypoint jwtEntrypoint;

   @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable()
                .authorizeRequests()
                .antMatchers("/public").permitAll() // Endpoint public
                .antMatchers("/CONTACT-QUERY-SERVICE/api/**").hasRole("ADMIN") // Exige le rôle ADMIN
                .antMatchers("/donnateur/**").hasRole("DONNATEUR") // Exige le rôle DONNATEUR
                .anyRequest().authenticated()
                .and()
                .exceptionHandling().authenticationEntryPoint(jwtEntrypoint)
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
    }
}

