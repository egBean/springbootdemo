package demo.activemq;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.junit.Test;

import javax.jms.*;

public class MqConsumer {

    private static final String Q_NAME = "demo1";

    /**
     * 消费者如果开启事务，则不考虑acknowledgeMode。
     * 如果不开启事务，则需要考虑acknowledgeMode.
     * @param args
     * @throws JMSException
     */
    public static void main(String[] args) throws JMSException {
        ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory();
        Connection connection = factory.createConnection();
        connection.start();
        Session session = connection.createSession(false,Session.CLIENT_ACKNOWLEDGE);
        Queue queue = session.createQueue(Q_NAME);
        MessageConsumer consumer = session.createConsumer(queue);
        while(true){
            TextMessage msg = (TextMessage)consumer.receive(1000L);
            if(msg!=null){
                System.out.println("接受到的消息为:"+msg.getText());
                msg.acknowledge();
            }else{
                break;
            }
        }
        consumer.close();
        //session.commit();
        session.close();
        connection.close();
        System.out.println("consumer end!!!");
    }
}
