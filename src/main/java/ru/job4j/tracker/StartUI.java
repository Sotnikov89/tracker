package ru.job4j.tracker;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class StartUI {

    private Output output;
    private Input input;

    public StartUI(Output output, Input input) {
        this.output = output;
        this.input = input;
    }

    public void init(Tracker tracker, List<UserAction> actions) {
        boolean run = true;
        while (run) {
            this.showMenu(actions);
            int select = input.askInt("Select: ");
            if (select < 0 || select >= actions.size()) {
                output.println("Wrong input, you can select: 0 .. " + (actions.size() - 1));
                continue;
            }
            UserAction action = actions.get(select);
            run = action.execute(input, tracker);
        }
    }

    private void showMenu(List<UserAction> actions) {
        output.println("Menu.");
        for (int i = 0; i < actions.size(); i++) {
            output.println(i + ". " + actions.get(i).name());
        }
    }

    public static void main(String[] args) {
        Output output = new ConsoleOutput();
        Input input = new ValidateInput(output, new ConsoleInput());
        Tracker tracker = new Tracker();
        List<UserAction> actions = List.of(
                new CreateAction(output),
                new ShowAllItemsAction(output),
                new ReplaceAction(output),
                new DeleteAction(output),
                new ShowByIdAction(output),
                new ShowByNameAction(output),
                new ExitAction(output)
        );
        new StartUI(output, input).init(tracker, actions);
    }
}
