package bootstrap.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

@Configuration
public class ScheduleConfig implements SchedulingConfigurer {

    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        taskRegistrar.setScheduler(schedulePool());
    }

    /**
     * 为@Schedule注解配置的线程池
     * @return
     */
    @Bean(destroyMethod="shutdown",value = "schedulePool")
    public ThreadPoolTaskScheduler schedulePool() {
        ThreadPoolTaskScheduler taskScheduler = new ThreadPoolTaskScheduler();
        taskScheduler.setPoolSize(10);
        taskScheduler.setThreadNamePrefix("schedulePool-");
        taskScheduler.initialize();
        return taskScheduler;
    }

    /**
     * 为@Async注解配置的线程池
     * @return
     */
    @Bean("asyncTaskExecutor")
    public AsyncTaskExecutor asyncTaskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setThreadNamePrefix("AsyncTaskExecutor");
//        考虑到大多是定时任务，且任务时间较长，线程数量要设置多一点
        executor.setMaxPoolSize(60);
        executor.setCorePoolSize(10);
//        存放到队列中无意义，因为基本都是定时job。
        executor.setQueueCapacity(0);//此参数指定当线程满时，能缓冲的任务队列大小。
        executor.setKeepAliveSeconds(60*60);//非coreThread 空闲存活时间1h。
        // 设置拒绝策略,默认已经是AbortPolicy
//        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.AbortPolicy());
        return executor;
    }
}
