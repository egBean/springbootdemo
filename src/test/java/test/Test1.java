package test;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Phaser;

public class Test1 {

    @Test
    public void aaa(){
        System.out.println(2<<16>>>16);
    }

    public static void main(String[] args) throws InterruptedException {
        Phaser phaser = new Phaser(1);
        List<Runnable> list = new ArrayList<>();
        for(int i=0;i<10;i++){
            list.add(() ->{
                System.out.println(Thread.currentThread().getName()+"--->");
            });
        }

        for(Runnable r : list){
            phaser.register();
            new Thread(){
                @Override
                public void run() {
                    phaser.arriveAndAwaitAdvance();
                    r.run();
                }
            }.start();
        }
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        phaser.arriveAndDeregister();

        for(Runnable r : list){
            //phaser.register();
            new Thread(){
                @Override
                public void run() {
                    phaser.arriveAndAwaitAdvance();
                    r.run();
                }
            }.start();
        }
        Thread.sleep(2000);
        System.out.println("zzzzzzzzzzzz");
    }
}
