package activemq;

import bootstrap.Example;
import bootstrap.activemq.MqSender;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.annotation.Resource;

@SpringBootTest(classes = Example.class)
@RunWith(SpringRunner.class)
//@WebAppConfiguration
public class TestSendMq {

    /*@Resource
    private MqSender mqSender;*/

    public static void main(String[] args) {

    }
    @Test
    public void sendMsg(){
        System.out.println("zzzz");
    }
}
