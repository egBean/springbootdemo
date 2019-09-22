package demo.activemq;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.ActiveMQMessageProducer;
import org.apache.activemq.AsyncCallback;

/**
 * activemq使用总结:
 * 1 消息模式必须是持久化模式。
 * 2 对于生产者，普通使用非事务模式，是默认同步发送数据的。可以保证消息一定发送成功，但是效率较低。如果使用事务模式，效率会提高。可以强制设置异步发送模式提高效率。此时最好指定一个callback，这样当消息发送失败时将消息存储下来。或者使用事务模式发送，也默认为异步发送模式。
 * 3 对于消费者，如果使用事务模式，此时忽略acknowledgeMode，则可保证消费成功。如果使用非事务模式，此时视acknowledgeMode而定。
 * 4 对于避免重复消费问题。考虑如下情况:
 * 当消费者消费成功后，却无法进行ack确认或者commit，比如与mq服务器失去连接。此时其他服务器可能会继续消费此条消息。可以考虑在mysql数据库中存一个表，记录消息唯一id插入。如果插入失败则消息已经被消费。
 * 如果简单一点，可以考虑加个redis，向里面put（msgid，null）。每次先查，如果查不到则说明没有消费。但是并不是绝对确保不重复消费（redis 与 ack同时失败）。
 */

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
        //开启异步发送方式
        //factory.setUseAsyncSend(true);
        Connection connection = factory.createConnection();
        connection.start();
        Session session = connection.createSession(false,Session.AUTO_ACKNOWLEDGE);
        Queue queue = session.createQueue(Q_NAME);
        ActiveMQMessageProducer producer = (ActiveMQMessageProducer)session.createProducer(queue);
        //此选项表示消息是否持久化在broker中，如果选NON，则服务器重启消息不复存在。
        producer.setDeliveryMode(DeliveryMode.PERSISTENT);

        TextMessage msg = session.createTextMessage("hello demo1");
        msg.setJMSMessageID("from laowang");
        String msgId = msg.getJMSMessageID();
        System.out.println(Thread.currentThread().getName());
        long start = System.currentTimeMillis();
        /*producer.send(msg, new AsyncCallback() {
            @Override
            public void onSuccess() {
                System.out.println("回调线程名："+Thread.currentThread().getName());
                System.out.println("消息："+msgId+"发送成功");
            }

            @Override
            public void onException(JMSException exception) {
                System.out.println("消息："+msgId+"发送失败");
            }
        });*/
        producer.send(msg);
        System.out.println("花费的时间为："+(System.currentTimeMillis()-start)+"ms");
        //session.commit();
        producer.close();
        session.close();
        connection.close();

        System.out.println("消息发送完成");


    }
}
