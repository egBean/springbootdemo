package bootstrap.activemq;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.TextMessage;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class MqListener {

    private static final AtomicInteger i = new AtomicInteger();
    /**
     * 我们消费者设置的是无事务，手动签收模式，此时如果receive方法没有抛出异常，spring会自动帮我们签收，方法在AbstractMessageListenerContainer类中。
     * @param textMessage
     * @throws JMSException
     */
    @JmsListener(destination = "${queue1}",containerFactory = "jmsListener")
    public void receive(TextMessage textMessage) throws JMSException {
        System.out.println("接受到的消息为:"+textMessage.getText());
        System.out.println("重发次数为："+i.getAndIncrement()+"次");
        throw new RuntimeException("zzzzzzzzzzzzzzzzzzzzzzzzz");
    }
}
