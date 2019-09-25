package bootstrap.activemq;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.RedeliveryPolicy;
import org.apache.activemq.command.ActiveMQQueue;
import org.messaginghub.pooled.jms.JmsPoolConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;
import javax.jms.ConnectionFactory;
import javax.jms.Queue;

@Component
//@EnableJms
public class ConfigBean {

    @Value("${queue1}")
    private String queue1;

    @Bean
    public Queue queue1(){
        return new ActiveMQQueue(queue1);
    }

    //@Bean
    public JmsTemplate jmsTemplate(ConnectionFactory connectionFactory) {
        Object connectionFactory1 = ((JmsPoolConnectionFactory) connectionFactory).getConnectionFactory();
        //((ActiveMQConnectionFactory)connectionFactory1).setUseAsyncSend(true);
        RedeliveryPolicy redeliveryPolicy = ((ActiveMQConnectionFactory) connectionFactory1).getRedeliveryPolicy();
        /**
         * 最大重发次数为3，此处的重发意思是重发给消费者。如果3次重发加上本身第一次发送共4次都失败，且消息为持久化消息，则进入死信队列。
         */
        redeliveryPolicy.setMaximumRedeliveries(3);
        JmsTemplate template = new JmsTemplate(connectionFactory);
        template.setPubSubDomain(false);
        template.setDeliveryMode(2);
        //template.setSessionAcknowledgeMode(2);
        template.setSessionTransacted(false);
        return template;
    }

    //@Bean("jmsListener")
    public JmsListenerContainerFactory<?> jmsListenerContainerQueue(ConnectionFactory connectionFactory){
        DefaultJmsListenerContainerFactory bean = new DefaultJmsListenerContainerFactory();
        bean.setSessionTransacted(false);
        bean.setSessionAcknowledgeMode(2);
        bean.setConnectionFactory(connectionFactory);
        return bean;
    }


}
