package demo.activemq;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class MqProducer {

    private static final String Q_NAME = "demo1";

    /**
     * 1 个人感觉在发消息的时候，acknowledgeMode（告知已收到的模式）没有用处。
     * 发消息只有开启事务和不开启事务，开启事务需要session commit。
     * If {@code transacted} is set to {@code false} then the session will be non-transacted. In this case the argument {@code acknowledgeMode} is used to specify how messages received by this session will be acknowledged.
     *
     *
     * @param args
     * @throws JMSException
     */
    public static void main(String[] args) throws JMSException {
        ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory();
        Connection connection = factory.createConnection();
        connection.start();
        Session session = connection.createSession(false,Session.AUTO_ACKNOWLEDGE);
        Queue queue = session.createQueue(Q_NAME);
        MessageProducer producer = session.createProducer(queue);
        //此选项表示消息是否持久化在broker中，如果选NON，则服务器重启消息不复存在。
        producer.setDeliveryMode(DeliveryMode.PERSISTENT);

        TextMessage msg = session.createTextMessage("hello demo1");
        producer.send(msg);
        //session.commit();
        producer.close();
        session.close();
        connection.close();

        System.out.println("消息发送完成");


    }
}
