package ir.mb.auth.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Objects;


@Configuration
@Order(1)
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http


                .requestMatchers()
                .antMatchers("/login", "/oauth/authorize")
//                .antMatchers("/oauth/token")
//                .antMatchers("/resources/**")
//                .antMatchers("/*.js")
//                .antMatchers("/", "/index", "/logout", "/error")
                .and()
                .authorizeRequests()
                .antMatchers("/oauth/authorize").permitAll()
                .antMatchers("/oauth/token").permitAll()
                .antMatchers("/resources/**").permitAll()
                .antMatchers("/login").permitAll()
                .antMatchers("/", "/index", "/logout", "/error").permitAll()
                .antMatchers("/*.js").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin().permitAll();
    }

    @Bean("clientPasswordEncoder")
    public BCryptPasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.parentAuthenticationManager(authenticationManagerBean())
                .inMemoryAuthentication().withUser("mb").password(encoder().encode("mb")).roles("USER");
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

}
