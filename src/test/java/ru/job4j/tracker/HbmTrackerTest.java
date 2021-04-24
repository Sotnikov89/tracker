package ru.job4j.tracker;

import junit.framework.TestCase;

import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.nullValue;
import static org.junit.Assert.assertThat;

public class HbmTrackerTest extends TestCase {

    public void testReplace() {
        Tracker tracker = new Tracker();
        Item item = new Item("name");
        tracker.add(item);
        int id = item.getId();
        Item newItem = new Item("newName");
        tracker.replace(id, newItem);
        assertThat(tracker.findById(id).getName(), is("newName"));
    }

    public void testDelete() {
        Tracker tracker = new Tracker();
        Item deleteName = new Item("deleteName");
        int id = deleteName.getId();
        tracker.add(deleteName);
        tracker.delete(id);
        assertThat(tracker.findById(id), is(nullValue()));
    }

    public void testFindAll() {
        Tracker tracker = new Tracker();
        tracker.add(new Item("name1"));
        tracker.add(new Item("name2"));
        tracker.add(new Item("name3"));
        List<Item> findAll = tracker.findAll();
        assertThat(findAll.size(), is(3));
        assertThat(findAll.get(0).getName(), is("name1"));
        assertThat(findAll.get(1).getName(), is("name2"));
        assertThat(findAll.get(2).getName(), is("name3"));
    }

    public void testFindByName() {
        Tracker tracker = new Tracker();
        tracker.add(new Item("findByName"));
        List<Item> items = tracker.findByName("findByName");
        assertThat(items.get(0).getName(), is("findByName"));
    }

    public void testFindById() {
        Tracker tracker = new Tracker();
        Item item = new Item("findById");
        tracker.add(item);
        Item byId = tracker.findById(item.getId());
        assertThat(byId.getName(), is("findById"));
    }
}