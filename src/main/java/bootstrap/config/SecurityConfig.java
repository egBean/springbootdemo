package bootstrap.config;

import bootstrap.domain.User;
import bootstrap.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private UserMapper mapper;

    /**
     * 这个方法是用来配置一些访问权限的。
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.authorizeRequests().
                antMatchers("/css/**","/index").permitAll()
                .antMatchers("/user/**").hasAnyRole(/*new String[]{"NORMAL","USER"}*/"NORMAL","USER")
                .and().formLogin()/*.loginPage("/login").failureUrl("/login-error")*/;
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {

        List<User> users = mapper.selectAll();
        for(User user: users){
            auth
                    .inMemoryAuthentication().passwordEncoder(new BCryptPasswordEncoder())
                    .withUser(user.getUser()).password(new BCryptPasswordEncoder().encode(user.getPassword())).roles(user.getRole());
        }

    }
}
