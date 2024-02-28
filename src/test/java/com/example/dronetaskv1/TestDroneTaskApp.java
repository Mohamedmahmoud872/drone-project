package com.example.dronetaskv1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.TestConfiguration;

@TestConfiguration(proxyBeanMethods = false)
public class TestDroneTaskApp {

    public static void main(String[] args) {
        SpringApplication.from(Dronetaskv1Application::main).with(TestDroneTaskApp.class).run(args);
    }

}
