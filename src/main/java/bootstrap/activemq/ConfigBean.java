package bootstrap.activemq;

import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.stereotype.Component;

import javax.jms.Queue;

@Component
@EnableJms
public class ConfigBean {

    @Value("${queue1}")
    private String queue1;

    @Bean
    public Queue queue1(){
        return new ActiveMQQueue(queue1);
    }
}
