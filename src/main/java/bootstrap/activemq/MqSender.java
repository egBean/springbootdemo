package bootstrap.activemq;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.stereotype.Component;

import javax.jms.Queue;


@Component
public class MqSender {

    @Autowired
    private JmsMessagingTemplate jmsMessagingTemplate;
    @Autowired
    private Queue queue1;

    public void produceMsg(final String message){
        jmsMessagingTemplate.convertAndSend(queue1,message);
    }


}
