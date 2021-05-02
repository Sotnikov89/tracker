package ru.job4j.tracker;

import org.springframework.stereotype.Component;

@Component
public class ConsoleOutput implements Output {
    @Override
    public void println(Object object) {
        System.out.println(object);
    }
}
