package ru.job4j.tracker;

public class DI {
    public static void main(String[] args) {
        Context context = new Context();
        context.reg(ConsoleOutput.class);
        context.reg(ConsoleInput.class);
        context.reg(StartUI.class);
        StartUI ui = context.get(StartUI.class);
    }
}
