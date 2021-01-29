package com.invicto.collector.option;

import com.invicto.collector.option.service.OptionCollector;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Slf4j
public class CollectorStart {
    public static void main(String[] args) {
        Long start = System.currentTimeMillis();
        ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("classpath:/spring.xml");
        OptionCollector collector = ctx.getBean(OptionCollector.class);
        ScheduledExecutorService executorService = new ScheduledThreadPoolExecutor(1);
        executorService.scheduleAtFixedRate(collector, 0L, 2L, TimeUnit.MINUTES);
        Long end = System.currentTimeMillis();
        log.info("Application started in "+((end - start)/1000)+" Seconds");


    }
}
