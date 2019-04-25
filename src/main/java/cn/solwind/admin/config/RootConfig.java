package cn.solwind.admin.config;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.FilterType;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * @author Zln
 * @version 2018-11-23 14:47
 * 
 */

@Configuration
@ComponentScan(basePackages = { "cn.solwind.*" }, excludeFilters = {
		@ComponentScan.Filter(type = FilterType.ANNOTATION, value = EnableWebMvc.class) })
@EnableAspectJAutoProxy(proxyTargetClass = true)
@EnableScheduling
public class RootConfig implements SchedulingConfigurer {

	@Override
	public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
		taskRegistrar.setScheduler(scheduleExecutor());
	}

	@Bean(destroyMethod = "shutdown")
	public Executor scheduleExecutor() {
		return Executors.newScheduledThreadPool(10);
	}

}