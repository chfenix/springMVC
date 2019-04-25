package cn.solwind.admin.config;

import java.util.concurrent.ThreadPoolExecutor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
* @author Zln
* @version 2018-12-05 14:08
* 
*  spring线程池配置
*/

@Configuration
@PropertySource("classpath:thread.properties")
public class ThreadConfig {
 
    //线程池维护线程的最少数量
    @Value("${spring.corePoolSize}")
    private Integer corePoolSize;
    //允许的空闲时间
    @Value("${spring.keepAliveSeconds}")
    private Integer keepAliveSeconds;
    //线程池维护线程的最大数量
    @Value("${spring.maxPoolSize}")
    private Integer maxPoolSize;
    //缓存队列
    @Value("${spring.queueCapacity}")
    private Integer queueCapacity;
 
    @Bean
    public TaskExecutor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(corePoolSize);
        executor.setKeepAliveSeconds(keepAliveSeconds);
        executor.setMaxPoolSize(maxPoolSize);
        executor.setQueueCapacity(queueCapacity);
        ThreadPoolExecutor.CallerRunsPolicy callerRunsPolicy = new ThreadPoolExecutor.CallerRunsPolicy();
        //对拒绝task的处理策略
        executor.setRejectedExecutionHandler(callerRunsPolicy);
        executor.initialize();
        return executor;
    }

}