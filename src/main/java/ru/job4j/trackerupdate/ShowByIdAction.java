package ru.job4j.trackerupdate;

public class ShowByIdAction implements UserAction {

    private final Output output;

    public ShowByIdAction(Output output) {
        this.output = output;
    }

    @Override
    public String name() {
        return "Find Item By ID";
    }

    @Override
    public boolean execute(Input input, Tracker tracker) {
        output.println("=== Find Item By ID ===");
        int id = input.askInt("Enter id: ");
        Item item = tracker.findById(id);
        if (item != null) {
            output.println(item);
        } else {
            output.println(String.format("Item with id=%s not found.", id));
        }
        return true;
    }
}
