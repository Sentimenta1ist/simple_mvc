package org.example;

import org.example.model.Item;
import org.example.model.Person;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {
        Configuration configuration = new Configuration().addAnnotatedClass(Person.class).addAnnotatedClass(Item.class);

        SessionFactory sessionFactory = configuration.buildSessionFactory();
        Session session = sessionFactory.getCurrentSession();

        try {
            session.beginTransaction();

            // Updating
            Person p = (Person) session.get(Person.class, 2);
            System.out.println(p.getName());
            p.setName("New name");


            //Deleting
            Person p1 = (Person) session.get(Person.class, 2);
            session.delete(p1);


            // Adding
            Person p2 = new Person("Some name", 60);
            session.save(p);


            //HQL
            List<Person> lst = session.createQuery("FROM Person where name LIKE 'T%'").getResultList();
            session.createQuery("delete from Person where age = 30").executeUpdate();
            System.out.println(lst);


            //One to many
            Person person = session.get(Person.class, 3);
            System.out.println(person);

            List<Item> items = person.getItems();

            System.out.println(items);

            Item item = session.get(Item.class, 5);
            System.out.println(item);

            System.out.println(item.getOwner());



            Person person1 = session.get(Person.class, 2);
            Item newItem = new Item("NEW ITEM");

            person1.getItems().add(newItem);
            session.save(newItem);


            Person person2 = session.get(Person.class, 3);
            List<Item> items2 = person.getItems();

            for(Item item1 : items2){
                session.remove(item1);
            }
            person2.getItems().clear();

            Person person3 = session.get(Person.class, 2);
            session.remove(person3);
            person3.getItems().forEach(i -> i.setOwner(null));


            //cascading
            Person p4 = new Person("Test cascade", 30);

            p4.addItem(new Item("Item1"));
            p4.addItem(new Item("Item2"));
            p4.addItem(new Item("Item3"));
            session.save(p4);



            // lazy loading
            Person p5 = session.get(Person.class, 1);
            System.out.println(p5.getItems());


            // eager loading
            Item i5 = session.get(Item.class, 1);
            System.out.println(i5.getOwner());


            session.getTransaction().commit();
            System.out.println("Out of session");

            // Open new session
            session = sessionFactory.getCurrentSession();
            session.beginTransaction();

            // merging item from last session
            p5 = (Person) session.merge(p5);

            // loading before closing
            Hibernate.initialize(p5.getItems());

            session.getTransaction().commit();


            // works because loaded before closing
            System.out.println(p5.getItems());

        } finally {
            sessionFactory.close();
        }

    }
}
