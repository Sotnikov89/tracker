package ru.job4j.tracker;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.query.Query;

import java.util.List;

public class HbmTracker implements Store, AutoCloseable{

    public static void main(String[] args) {
        HbmTracker hbmTracker = new HbmTracker();
        for (Item item : hbmTracker.findAll()) {
            System.out.println(item.getId());
            System.out.println(item.getName());
            System.out.println(item.getDescription());
        }
        System.out.println(hbmTracker.findById(2));
        System.out.println(hbmTracker.findByName("Name-2"));
    }

    private final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
            .configure().build();
    private final SessionFactory sf = new MetadataSources(registry)
            .buildMetadata().buildSessionFactory();

    @Override
    public Item add(Item item) {
        Session session = sf.openSession();
        session.beginTransaction();
        int id = (Integer) session.save(item);
        session.getTransaction().commit();
        session.close();
        item.setId(id);
        return item;
    }

    @Override
    public boolean replace(int id, Item item) {
        boolean result = id > 0;
        if (result) {
            item.setId(id);
            Session session = sf.openSession();
            session.beginTransaction();
            session.update(item);
            session.getTransaction().commit();
            session.close();
        }
        return result;
    }

    @Override
    public boolean delete(int id) {
        boolean result = id > 0;
        if (result) {
            Session session = sf.openSession();
            session.beginTransaction();
            Item item = session.get(Item.class, id);
            session.delete(item);
            session.getTransaction().commit();
            session.close();
        }
        return result;
    }

    @Override
    public List<Item> findAll() {
        Session session = sf.openSession();
        Query query = session.createQuery("FROM Item");
        return (List<Item>) query.list();
    }

    @Override
    public List<Item> findByName(String name) {
        Session session = sf.openSession();
        Query query = session.createQuery("FROM Item i WHERE i.name=:name");
        query.setParameter("name", name);
        return (List<Item>) query.list();
    }

    @Override
    public Item findById(int id) {
        Session session = sf.openSession();
        session.beginTransaction();
        Item item = session.get(Item.class, id);
        session.getTransaction().commit();
        session.close();
        return item;
    }

    @Override
    public void close() throws Exception {
        StandardServiceRegistryBuilder.destroy(registry);
    }
}
