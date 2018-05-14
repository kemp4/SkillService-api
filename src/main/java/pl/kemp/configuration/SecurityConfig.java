package pl.kemp.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    DataSource dataSource;
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.jdbcAuthentication().dataSource(dataSource).usersByUsernameQuery("select username,password, enabled from users where username=?");
//    }
protected void configure(AuthenticationManagerBuilder auth)
        throws Exception {
    auth.jdbcAuthentication().dataSource(dataSource).passwordEncoder(org.springframework.security.crypto.password.NoOpPasswordEncoder.getInstance())
            .usersByUsernameQuery("select name as username, password, true from users where name = ?")
            .authoritiesByUsernameQuery("select name as username, 'USER' from users where name=?")
            ;//.passwordEncoder(new BCryptPasswordEncoder());
}

    protected void configure(HttpSecurity http) throws Exception {
        http
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .httpBasic()
                .and()
                .csrf().disable()
                .authorizeRequests()
                .antMatchers(HttpMethod.POST,"/users").permitAll()
                .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.GET,"/users/{id}").permitAll()
                .and()
                .authorizeRequests()
                .anyRequest().authenticated()
                .and()

//                .anyRequest().authenticated()
//                .antMatchers(HttpMethod.GET,"/users").permitAll()

        ;


    }
}