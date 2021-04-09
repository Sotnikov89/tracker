package ru.job4j.trackerupdate;

import java.util.List;

public class ShowAllItemsAction implements UserAction {

    private final Output output;

    public ShowAllItemsAction(Output output) {
        this.output = output;
    }

    @Override
    public String name() {
        return "Show All Items";
    }

    @Override
    public boolean execute(Input input, Tracker tracker) {
        output.println("=== Show All Items ===");
        List<Item> items = tracker.findAll();
        if (items.size() > 0) {
            for (int i = 0; i < items.size(); i++) {
                output.println(items.get(i));
            }
        } else {
            output.println("Tracker not contains items.");
        }
        return true;
    }
}
