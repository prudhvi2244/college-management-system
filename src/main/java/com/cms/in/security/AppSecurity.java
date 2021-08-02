package com.cms.in.security;

import com.cms.in.entity.UserRegistration;
import com.cms.in.repository.UserRegistrationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.util.Optional;
import java.util.stream.Collectors;

@Configuration
@EnableWebSecurity
public class AppSecurity  extends WebSecurityConfigurerAdapter
{

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserRegistrationRepository urepo;

    @Autowired
    private InvalidAuthenticationEntryPoint invalidAuthenticationEntryPoint;

    @Autowired
    private MySecurityFilter mySecurityFilter;

    @Override
    @Bean
    protected UserDetailsService userDetailsService() {

        return new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

               Optional<UserRegistration> opt= urepo.findByUsername(username);
                if(opt.isPresent())
                {
                   UserRegistration userRegistration=opt.get();
                   return new org.springframework.security.core.userdetails.User(username,
                                        userRegistration.getPassword()
                                        ,userRegistration.getRoles().stream().
                           map(role->new SimpleGrantedAuthority(role.toString()))
                           .collect(Collectors.toSet()));
                }
                else
                {
                    throw  new UsernameNotFoundException("User Not Exist!");
                }

            }
        };

    }

    @Override
    @Bean
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {


        http
                .csrf()
                .disable()
                .authorizeRequests()
                .antMatchers("/","/about","/admissions","/register")
                .permitAll()
                .antMatchers(HttpMethod.POST,"/login")
                .permitAll()
                .antMatchers(HttpMethod.GET,"/user/allStudents").hasAuthority("STUDENT")
                .antMatchers("/user/allTeachers").hasAuthority("TEACHER")
                .antMatchers("/user/allTeachers","/user/allStudents").hasAuthority("ADMIN")
                .anyRequest()
                .authenticated()
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(invalidAuthenticationEntryPoint)
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilterBefore(mySecurityFilter, UsernamePasswordAuthenticationFilter.class)
                ;

    }
}
