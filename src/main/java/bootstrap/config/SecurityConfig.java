package bootstrap.config;

import bootstrap.domain.User;
import bootstrap.filter.JWTAuthenticationFilter;
import bootstrap.filter.JWTLoginFilter;
import bootstrap.mapper.UserMapper;
import bootstrap.service.MyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
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

    //数据库相关信息加密存储，比如client端的secret，还有用户密码等
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    @Autowired
    private MyUserDetailsService userDetailsService;

    /**
     * 这个方法是用来配置一些访问权限的。
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {

//      http.authorizeRequests().
//                antMatchers("/css/**","/index").authenticated()
//                //判断角色是最基本与简单的判断，一般内存模式可以这么做。如果使用jdbc，最好是用户对应多个角色，一个角色又对应多种权限
//                //这样通过数据库将一个角色的所有权限都拿到，在userdetails接口中实现。
//                .antMatchers("/user/**").hasAnyAuthority("NORMAL")/*.hasAnyRole(*//*new String[]{"NORMAL","USER"}*//*"NORMAL","USER")*/
//                .and().formLogin()/*.loginPage("/login").failureUrl("/login-error")*/;
        http.requestMatchers().anyRequest().and().authorizeRequests()
                .antMatchers("/**").authenticated()
                .antMatchers("/login/**").permitAll()
                .and().formLogin();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {

        /*List<User> users = mapper.selectAll();
        for(User user: users){
            auth
                    .inMemoryAuthentication()//.passwordEncoder(new BCryptPasswordEncoder())
                    .withUser(user.getUsername()).password(passwordEncoder.encode(user.getPassword())).roles(user.getRole());
        }*/

        auth.authenticationProvider(authenticationProvider());
    }


    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider
                = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder);
        return authProvider;
    }

}
