package bootstrap.activemq;

import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;
import javax.jms.ConnectionFactory;
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

    @Bean

    public JmsTemplate jmsTemplate(ConnectionFactory connectionFactory) {
        JmsTemplate template = new JmsTemplate(connectionFactory);
        template.setPubSubDomain(false);
        template.setDeliveryMode(2);
        template.setConnectionFactory(connectionFactory);
        template.setSessionAcknowledgeMode(2);
        template.setSessionTransacted(false);
        return template;
    }
}
