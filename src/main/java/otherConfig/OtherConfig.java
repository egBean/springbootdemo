package otherConfig;

import bootstrap.domain.People;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@Getter
@Setter
@Configuration
@ConfigurationProperties
@PropertySource("classpath:people.properties")
@EnableAsync  //开启异步线程池
public class OtherConfig implements AsyncConfigurer {

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

    @Override
    public Executor getAsyncExecutor(){
        return Executors.newFixedThreadPool(20);
    }
}
