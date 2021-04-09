package ru.job4j.trackerupdate;

import java.util.List;

public class ShowByNameAction implements UserAction {

    private final Output output;

    public ShowByNameAction(Output output) {
        this.output = output;
    }

    @Override
    public String name() {
        return "Find item By Name";
    }

    @Override
    public boolean execute(Input input, Tracker tracker) {
        output.println("=== Find item By Name ===");
        String name = input.askStr("Enter name: ");
        List<Item> items = tracker.findByName(name);
        if (items.size() > 0) {
            for (int i = 0; i < items.size(); i++) {
                output.println(items.get(i));
            }
        } else {
            output.println(String.format("Item with name=%s not found.", name));
        }
        return true;
    }
}
