package bootstrap;

import tk.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("bootstrap.mapper")
public class Example {


    private static Logger logger = LoggerFactory.getLogger(Example.class);

    public static void main(String[] args) {
        SpringApplication.run(Example.class, args);
    }

}