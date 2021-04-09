package ru.job4j.trackerupdate;

public interface UserAction {
    String name();
    boolean execute(Input input, Tracker tracker);
}
