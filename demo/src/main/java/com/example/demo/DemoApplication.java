package com.example.demo;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.message.Message;
import org.apache.logging.log4j.message.ParameterizedMessage;
import org.apache.logging.log4j.message.StringMapMessage;
import org.slf4j.MDC;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

@SpringBootApplication
public class DemoApplication implements CommandLineRunner {

    private static final Logger logger = LogManager.getLogger(DemoApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        scheduledExecutorService.scheduleWithFixedDelay(() -> {
            ThreadLocalRandom current = ThreadLocalRandom.current();
            MDC.remove("unit");
            MDC.put("unit", "0911-00");
            logger.info(new StringMapMessage()
                    .with("message", "Imported data")
                    .with("opType", "import")
                    .with("duration",  current.nextInt(10000)));
            logger.info(new StringMapMessage()
                    .with("message", "Exported data")
                    .with("opType", "export")
                    .with("duration",  current.nextInt(10000)));

            MDC.remove("unit");
            MDC.put("unit", "1828-00");

            logger.info(new StringMapMessage()
                    .with("message", "Imported data")
                    .with("opType", "import")
                    .with("duration",  current.nextInt(10000)));
            logger.info(new StringMapMessage()
                    .with("message", "Exported data")
                    .with("opType", "export")
                    .with("duration",  current.nextInt(10000)));

        }, 0, 5, TimeUnit.SECONDS);
    }

    @RestController
    public static class MyRest {
        @GetMapping
        String get() throws UnknownHostException {
            return "Hello from " + InetAddress.getLocalHost().getHostName();
        }
    }

}