package ru.job4j.tracker;

import org.junit.Test;

import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.nullValue;
import static org.junit.Assert.*;

public class StartUITest {

    @Test
    public void whenExit() {
        Output out = new StubOutput();
        Input in = new StubInput(
                new String[] {"0"}
        );
        Tracker tracker = new Tracker();
        List<UserAction> actions = List.of(
                new ExitAction(out)
        );
        new StartUI(out).init(in, tracker, actions);
        assertThat(out.toString(), is(
                "Menu." + System.lineSeparator()
                        + "0. Exit" + System.lineSeparator()
                        + "=== Exit Program ===" + System.lineSeparator()
        ));
    }

    @Test
    public void whenInvalidExit() {
        Output out = new StubOutput();
        Input in = new StubInput(
                new String[] {"100", "0"}
        );
        Tracker tracker = new Tracker();
        List<UserAction> actions = List.of(
                new ExitAction(out)
        );
        new StartUI(out).init(in, tracker, actions);
        assertThat(out.toString(), is(
                String.format(
                        "Menu.%n"
                                + "0. Exit%n"
                                + "Wrong input, you can select: 0 .. 0%n"
                                + "Menu.%n"
                                + "0. Exit%n"
                                + "=== Exit Program ===%n"
                )
        ));
    }

    @Test
    public void whenInvalidDataNegativeNumberExit() {
        Output out = new StubOutput();
        Input in = new StubInput(
                new String[] {"-100", "0"}
        );
        Tracker tracker = new Tracker();
        List<UserAction> actions = List.of(
                new ExitAction(out)
        );
        new StartUI(out).init(in, tracker, actions);
        assertThat(out.toString(), is(
                String.format(
                        "Menu.%n"
                                + "0. Exit%n"
                                + "Wrong input, you can select: 0 .. 0%n"
                                + "Menu.%n"
                                + "0. Exit%n"
                                + "=== Exit Program ===%n"
                )
        ));
    }

    @Test
    public void whenAddItem() {
        Output out = new StubOutput();
        Input in = new StubInput(
                new String[] {"0", "Item name", "1"}
        );
        Tracker tracker = new Tracker();
        List<UserAction> actions = List.of(
                new CreateAction(out),
                new ExitAction(out)
        );
        new StartUI(out).init(in, tracker, actions);
        assertThat(tracker.findAll().get(0).getName(), is("Item name"));
    }

    @Test
    public void whenAddItemTestOutput() {
        Output out = new StubOutput();
        Input in = new StubInput(
                new String[] {"0", "Item name", "1"}
        );
        Tracker tracker = new Tracker();
        List<UserAction> actions = List.of(
                new CreateAction(out),
                new ExitAction(out)
        );
        new StartUI(out).init(in, tracker, actions);
        assertThat(out.toString(), is(
                "Menu." + System.lineSeparator()
                        + "0. Create Item" + System.lineSeparator()
                        + "1. Exit" + System.lineSeparator()
                        + "=== Create a new Item ===" + System.lineSeparator()
                        + "Menu." + System.lineSeparator()
                        + "0. Create Item" + System.lineSeparator()
                        + "1. Exit" + System.lineSeparator()
                        + "=== Exit Program ===" + System.lineSeparator()
        ));
    }

    @Test
    public void whenReplaceItem() {
        Output out = new StubOutput();
        Tracker tracker = new Tracker();
        Item item = tracker.add(new Item("Replaced item"));
        String replacedName = "New item name";
        Input in = new StubInput(
                new String[] {"0", String.valueOf(item.getId()), replacedName, "1"}
        );
        List<UserAction> actions = List.of(
                new ReplaceAction(out),
                new ExitAction(out)
        );
        new StartUI(out).init(in, tracker, actions);
        assertThat(tracker.findById(item.getId()).getName(), is(replacedName));
    }

    @Test
    public void whenReplaceItemTestOutputFirstVar() {
        Output out = new StubOutput();
        Tracker tracker = new Tracker();
        Item item = tracker.add(new Item("Replaced item"));
        String replacedName = "New item name";
        Input in = new StubInput(
                new String[] {"0", String.valueOf(item.getId()), replacedName, "1"}
        );
        List<UserAction> actions = List.of(
                new ReplaceAction(out),
                new ExitAction(out)
        );
        new StartUI(out).init(in, tracker, actions);
        assertThat(out.toString(), is("Menu." + System.lineSeparator()
                + "0. Edit Item" + System.lineSeparator()
                + "1. Exit" + System.lineSeparator()
                + "=== Edit item ===" + System.lineSeparator()
                + "Edit item is done." + System.lineSeparator()
                + "Menu." + System.lineSeparator()
                + "0. Edit Item" + System.lineSeparator()
                + "1. Exit" + System.lineSeparator()
                + "=== Exit Program ===" + System.lineSeparator()));
    }

    @Test
    public void whenReplaceItemTestOutputSecondVar() {
        Output out = new StubOutput();
        Tracker tracker = new Tracker();
        tracker.add(new Item("Replaced item"));
        String replacedName = "New item name";
        Input in = new StubInput(
                new String[] {"0", "1000", replacedName, "1"}
        );
        List<UserAction> actions = List.of(
                new ReplaceAction(out),
                new ExitAction(out)
        );
        new StartUI(out).init(in, tracker, actions);
        assertThat(out.toString(), is("Menu." + System.lineSeparator()
                + "0. Edit Item" + System.lineSeparator()
                + "1. Exit" + System.lineSeparator()
                + "=== Edit item ===" + System.lineSeparator()
                + "Item with id=1000 not found." + System.lineSeparator()
                + "Menu." + System.lineSeparator()
                + "0. Edit Item" + System.lineSeparator()
                + "1. Exit" + System.lineSeparator()
                + "=== Exit Program ===" + System.lineSeparator()));
    }

    @Test
    public void whenDeleteItem() {
        Output out = new StubOutput();
        Tracker tracker = new Tracker();
        Item item = tracker.add(new Item("Deleted item"));
        Input in = new StubInput(
                new String[] {"0", String.valueOf(item.getId()), "1"}
        );
        List<UserAction> actions = List.of(
                new DeleteAction(out),
                new ExitAction(out)
        );
        new StartUI(out).init(in, tracker, actions);
        assertThat(tracker.findById(item.getId()), is(nullValue()));
    }

    @Test
    public void whenDeleteItemTestOutputFirstVar() {
        Output out = new StubOutput();
        Tracker tracker = new Tracker();
        Item item = tracker.add(new Item("Deleted item"));
        Input in = new StubInput(
                new String[] {"0", String.valueOf(item.getId()), "1"}
        );
        List<UserAction> actions = List.of(
                new DeleteAction(out),
                new ExitAction(out)
        );
        new StartUI(out).init(in, tracker, actions);
        assertThat(out.toString(), is("Menu." + System.lineSeparator()
                + "0. Delete Item" + System.lineSeparator()
                + "1. Exit" + System.lineSeparator()
                + "=== Delete Item ===" + System.lineSeparator()
                + "Item was deleted." + System.lineSeparator()
                + "Menu." + System.lineSeparator()
                + "0. Delete Item" + System.lineSeparator()
                + "1. Exit" + System.lineSeparator()
                + "=== Exit Program ===" + System.lineSeparator()));
    }

    @Test
    public void whenDeleteItemTestOutputSecondVar() {
        Output out = new StubOutput();
        Tracker tracker = new Tracker();
        tracker.add(new Item("Deleted item"));
        Input in = new StubInput(
                new String[] {"0", "1000", "1"}
        );
        List<UserAction> actions = List.of(
                new DeleteAction(out),
                new ExitAction(out)
        );
        new StartUI(out).init(in, tracker, actions);
        assertThat(out.toString(), is("Menu." + System.lineSeparator()
                + "0. Delete Item" + System.lineSeparator()
                + "1. Exit" + System.lineSeparator()
                + "=== Delete Item ===" + System.lineSeparator()
                + "Item with id=1000 not found." + System.lineSeparator()
                + "Menu." + System.lineSeparator()
                + "0. Delete Item" + System.lineSeparator()
                + "1. Exit" + System.lineSeparator()
                + "=== Exit Program ===" + System.lineSeparator()));
    }

    @Test
    public void whenFindAllItemsFirstVar() {
        Output out = new StubOutput();
        Tracker tracker = new Tracker();
        tracker.add(new Item("New Item"));
        Input in = new StubInput(
                new String[] {"0", "1"}
        );
        List<UserAction> actions = List.of(
                new ShowAllItemsAction(out),
                new ExitAction(out)
        );
        new StartUI(out).init(in, tracker, actions);
        assertThat(out.toString(), is("Menu." + System.lineSeparator()
                + "0. Show All Items" + System.lineSeparator()
                + "1. Exit" + System.lineSeparator()
                + "=== Show All Items ===" + System.lineSeparator()
                + "Item: id=1, name=New Item" + System.lineSeparator()
                + "Menu." + System.lineSeparator()
                + "0. Show All Items" + System.lineSeparator()
                + "1. Exit" + System.lineSeparator()
                + "=== Exit Program ===" + System.lineSeparator()));
    }

    @Test
    public void whenFindAllItemsSecondVar() {
        Output out = new StubOutput();
        Tracker tracker = new Tracker();
        Input in = new StubInput(
                new String[] {"0", "1"}
        );
        List<UserAction> actions = List.of(
                new ShowAllItemsAction(out),
                new ExitAction(out)
        );
        new StartUI(out).init(in, tracker, actions);
        assertThat(out.toString(), is("Menu." + System.lineSeparator()
                + "0. Show All Items" + System.lineSeparator()
                + "1. Exit" + System.lineSeparator()
                + "=== Show All Items ===" + System.lineSeparator()
                + "Tracker not contains items." + System.lineSeparator()
                + "Menu." + System.lineSeparator()
                + "0. Show All Items" + System.lineSeparator()
                + "1. Exit" + System.lineSeparator()
                + "=== Exit Program ===" + System.lineSeparator()));
    }

    @Test
    public void whenFindByNameItemsFirstVar() {
        Output out = new StubOutput();
        Tracker tracker = new Tracker();
        tracker.add(new Item("New Item"));
        Input in = new StubInput(
                new String[] {"0", "New Item", "1"}
        );
        List<UserAction> actions = List.of(
                new ShowByNameAction(out),
                new ExitAction(out)
        );
        new StartUI(out).init(in, tracker, actions);
        assertThat(out.toString(), is("Menu." + System.lineSeparator()
                + "0. Find item By Name" + System.lineSeparator()
                + "1. Exit" + System.lineSeparator()
                + "=== Find item By Name ===" + System.lineSeparator()
                + "Item: id=1, name=New Item" + System.lineSeparator()
                + "Menu." + System.lineSeparator()
                + "0. Find item By Name" + System.lineSeparator()
                + "1. Exit" + System.lineSeparator()
                + "=== Exit Program ===" + System.lineSeparator()));
    }

    @Test
    public void whenFindByNameItemsSecondVar() {
        Output out = new StubOutput();
        Tracker tracker = new Tracker();
        tracker.add(new Item("New Item"));
        Input in = new StubInput(
                new String[] {"0", "Find Item", "1"}
        );
        List<UserAction> actions = List.of(
                new ShowByNameAction(out),
                new ExitAction(out)
        );
        new StartUI(out).init(in, tracker, actions);
        assertThat(out.toString(), is("Menu." + System.lineSeparator()
                + "0. Find item By Name" + System.lineSeparator()
                + "1. Exit" + System.lineSeparator()
                + "=== Find item By Name ===" + System.lineSeparator()
                + "Item with name=Find Item not found." + System.lineSeparator()
                + "Menu." + System.lineSeparator()
                + "0. Find item By Name" + System.lineSeparator()
                + "1. Exit" + System.lineSeparator()
                + "=== Exit Program ===" + System.lineSeparator()));
    }

    @Test
    public void whenFindByIdItemsFirstVar() {
        Output out = new StubOutput();
        Tracker tracker = new Tracker();
        Item item = tracker.add(new Item("New Item"));
        Input in = new StubInput(
                new String[] {"0", String.valueOf(item.getId()), "1"}
        );
        List<UserAction> actions = List.of(
                new ShowByIdAction(out),
                new ExitAction(out)
        );
        new StartUI(out).init(in, tracker, actions);
        assertThat(out.toString(), is("Menu." + System.lineSeparator()
                + "0. Find Item By ID" + System.lineSeparator()
                + "1. Exit" + System.lineSeparator()
                + "=== Find Item By ID ===" + System.lineSeparator()
                + "Item: id=1, name=New Item" + System.lineSeparator()
                + "Menu." + System.lineSeparator()
                + "0. Find Item By ID" + System.lineSeparator()
                + "1. Exit" + System.lineSeparator()
                + "=== Exit Program ===" + System.lineSeparator()));
    }

    @Test
    public void whenFindByIdItemsSecondVar() {
        Output out = new StubOutput();
        Tracker tracker = new Tracker();
        tracker.add(new Item("New Item"));
        Input in = new StubInput(
                new String[] {"0", "1000", "1"}
        );
        List<UserAction> actions = List.of(
                new ShowByIdAction(out),
                new ExitAction(out)
        );
        new StartUI(out).init(in, tracker, actions);
        assertThat(out.toString(), is("Menu." + System.lineSeparator()
                + "0. Find Item By ID" + System.lineSeparator()
                + "1. Exit" + System.lineSeparator()
                + "=== Find Item By ID ===" + System.lineSeparator()
                + "Item with id=1000 not found." + System.lineSeparator()
                + "Menu." + System.lineSeparator()
                + "0. Find Item By ID" + System.lineSeparator()
                + "1. Exit" + System.lineSeparator()
                + "=== Exit Program ===" + System.lineSeparator()));
    }
}