package bootstrap.eventpubandlisten;

import bootstrap.domain.Bilibili;
import bootstrap.domain.People;
import com.alibaba.fastjson.JSON;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class DemoListener implements ApplicationListener<DemoEvent> {
    @Override
    @Async
    public void onApplicationEvent(DemoEvent event) {
        System.out.println(Thread.currentThread().getName());
        System.out.println(JSON.toJSONString(event.getSource()));
    }
}

