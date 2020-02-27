package com.epam.esm.config;

import com.epam.esm.config.filter.JwtRequestFilter;
import com.epam.esm.controller.handler.RestAccessDeniedHandler;
import com.epam.esm.controller.handler.RestAuthenticationEntryPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@PropertySource("classpath:path.properties")
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    @Value("${path.certificate}")
    private String certificatesPath;
    @Value("${path.tag}")
    private String tagsPath;
    @Value("${path.purchase}")
    private String purchasePath;
    @Value("${role.admin}")
    private String roleAdmin;

    private final UserDetailsService userDetailsService;
    private final JwtRequestFilter jwtRequestFilter;

    public SecurityConfiguration(UserDetailsService userDetailsService, JwtRequestFilter jwtRequestFilter) {
        this.userDetailsService = userDetailsService;
        this.jwtRequestFilter = jwtRequestFilter;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers(HttpMethod.GET, certificatesPath, "/certificates/*").permitAll()
                .antMatchers(HttpMethod.POST, certificatesPath).hasRole(roleAdmin)
                .antMatchers(HttpMethod.PUT, "/certificates/*").hasRole(roleAdmin)
                .antMatchers(HttpMethod.DELETE, "/certificates/*").hasRole(roleAdmin)

                .antMatchers(HttpMethod.POST, tagsPath).hasRole(roleAdmin)
                .antMatchers(HttpMethod.DELETE, tagsPath).hasRole(roleAdmin)
                .antMatchers(HttpMethod.GET, tagsPath, "/tags/*").authenticated()
                .antMatchers(HttpMethod.GET, "/tags/popular").permitAll()

                .antMatchers(HttpMethod.POST, purchasePath).authenticated()
                .antMatchers(HttpMethod.GET, purchasePath).authenticated()
                .antMatchers(HttpMethod.GET, "/purchase/*").hasRole(roleAdmin)
                .antMatchers("/users/*").anonymous()
                .anyRequest().authenticated()
                .and()
                .exceptionHandling().authenticationEntryPoint(new RestAuthenticationEntryPoint())
                .and().exceptionHandling().accessDeniedHandler(new RestAccessDeniedHandler())
                .and().sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
    }


    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }
}