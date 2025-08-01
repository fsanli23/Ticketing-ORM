package com.cydeo.config;


import com.cydeo.service.SecurityService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
public class SecurityConfig {
    private final SecurityService securityService;

    public SecurityConfig(SecurityService securityService) {
        this.securityService = securityService;
    }
    /*    @Bean
    public UserDetailsService userDetailsService(PasswordEncoder encoder) {

        List<UserDetails> userList = new ArrayList<>();
        userList.add(new User("mike", encoder.encode("password"), List.of(new SimpleGrantedAuthority("ROLE_ADMIN"))));
        userList.add(new User("fatih", encoder.encode("password"), List.of(new SimpleGrantedAuthority("ROLE_MANAGER"))));
        return new InMemoryUserDetailsManager(userList);

    }*/

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeRequests()// to authorize any action  // use hasAuthority because hasrole has prefix ROLE . simply use has authority
                .antMatchers("/user/**").hasAuthority("Admin")
                .antMatchers("/project/**/**").hasAuthority("Manager")
                .antMatchers("/task/employee/**").hasAuthority("Employee")
                .antMatchers("/task/**").hasRole("Manager")
                // .antMatchers("/task/**").hasAuthority("ROLE_EMPLOYEE")
                // .antMatchers("/task/**").hasAnyRole("EMPLOYEE","ADMIN")
                .antMatchers("/", "/login", "/fragments/**", "/images/**", "/assets/**").permitAll()
                .anyRequest()
                .authenticated()
                .and()
                //.httpBasic()
                .formLogin()
                .loginPage("/login")
                .defaultSuccessUrl("/welcome")
                .failureUrl("/login?error=true")
                .permitAll()
                .and().logout().
                logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/login").and().rememberMe().tokenValiditySeconds(120).key("fatih").
                userDetailsService(securityService).and()
                .build();
    }


}
