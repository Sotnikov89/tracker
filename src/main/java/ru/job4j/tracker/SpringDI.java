package ru.job4j.tracker;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class SpringDI {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.scan("ru.job4j.tracker");
        context.refresh();
        StartUI ui = context.getBean(StartUI.class);
    }
}
