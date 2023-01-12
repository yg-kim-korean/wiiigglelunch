package com.server.wiiigglelunch.security;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true) //인증됐을때만 되도록
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

//    private UsersService usersService;
    @Bean
    PasswordEncoder passwordEncoder(){
        return NoOpPasswordEncoder.getInstance();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        JWTLoginFilter loginFilter = new JWTLoginFilter(authenticationManager());
//        JWTCheckFilter checkFilter = new JWTCheckFilter(authenticationManager(),usersService);
        http.
                csrf().disable()
//                .sessionManagement(session->
//                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//                .addFilterAt(loginFilter, UsernamePasswordAuthenticationFilter.class)
//                .addFilterAt(checkFilter, BasicAuthenticationFilter.class)
                .authorizeRequests()
                .anyRequest()
                .authenticated()
                .and()
                .formLogin()
                .defaultSuccessUrl("/",true)
                .permitAll()
                .and()
                .logout();

    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().anyRequest();
//        web.ignoring().antMatchers("/"); //홈 화면 혹은 로그인화면같이 보여줘야 하는 페이지들에 접근하는거는 혀ㅓ용하도록함.
    }
}
