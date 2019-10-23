package bootstrap.controller;

import bootstrap.domain.User;
import bootstrap.service.MyUserDetails;
import bootstrap.service.MyUserDetailsService;
import com.alibaba.fastjson.JSON;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
public class LoginController {

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private MyUserDetailsService myUserDetailsService;

    @PostMapping("/jwt")
    public String login(@RequestBody User user){
        UserDetails userDetails = myUserDetailsService.loadUserByUsername(user.getUsername());
        if(!passwordEncoder.matches(user.getPassword(),userDetails.getPassword())){
            throw new RuntimeException("密码错误");
        }
        StringBuilder sb = new StringBuilder();
        for(GrantedAuthority auth : userDetails.getAuthorities()){
            if(StringUtils.isNotEmpty(auth.getAuthority())){
                sb.append(auth.getAuthority()+",");
            }
        }
        JwtBuilder builder = Jwts.builder()
                .setSubject(user.getUsername())
                .setExpiration(new Date(System.currentTimeMillis() + 60 * 60 * 24 * 1000))
                .signWith(SignatureAlgorithm.HS512, "MyJwtSecret");
        System.out.println(sb.toString());
        if(sb.length()>0){
            sb.deleteCharAt(sb.length()-1);
            builder.claim("authorities",sb.toString());
        }
        String token = builder.compact();
        return token;
    }
}
