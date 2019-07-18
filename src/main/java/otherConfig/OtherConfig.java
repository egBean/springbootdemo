package otherConfig;

import bootstrap.domain.People;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Getter
@Setter
@Configuration
@ConfigurationProperties
@PropertySource("classpath:people.properties")
public class OtherConfig {

    private String name;
    private int age;

    @Bean("laowang")
    public People getPeople(){
        return new People("laowang",18);
    }

    @Bean("p3")
    public People getPeople2(){
        return new People(name,age);
    }
}
