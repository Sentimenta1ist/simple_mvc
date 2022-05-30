package org.example;

import org.example.model.Item;
import org.example.model.Person;
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
            /*Person p = (Person) session.get(Person.class, 2);
            System.out.println(p.getName());
            p.setName("New name");*/


            //Deleting
            /*Person p = (Person) session.get(Person.class, 2);
            session.delete(p);*/


            // Adding
            /*Person p = new Person("Some name", 60);
            session.save(p);*/


            //HQL
            /*List<Person> lst = session.createQuery("FROM Person where name LIKE 'T%'").getResultList();
            session.createQuery("delete from Person where age = 30").executeUpdate();
            System.out.println(lst);*/


            //One to many
            /*Person person = session.get(Person.class, 3);
            System.out.println(person);

            List<Item> items = person.getItems();

            System.out.println(items);

            Item item = session.get(Item.class, 5);
            System.out.println(item);

            System.out.println(item.getOwner());*/



            /*Person person = session.get(Person.class, 2);
            Item newItem = new Item("NEW ITEM", person);

            person.getItems().add(newItem);
            session.save(newItem);*/


            /*Person person = new Person("Test person", 30);
            Item newItem = new Item("Item2", person);

            person.setItems(new ArrayList<>(Collections.singletonList(newItem)));

            session.save(person);
            session.save(newItem);*/


            /*Person person = session.get(Person.class, 3);
            List<Item> items = person.getItems();

            for(Item item : items){
                session.remove(item);
            }
            person.getItems().clear();*/

            /*Person person = session.get(Person.class, 2);
            session.remove(person);
            person.getItems().forEach(i -> i.setOwner(null));*/


            //cascading
            Person p = new Person("Test cascade", 30);

            p.addItem(new Item("Item1"));
            p.addItem(new Item("Item2"));
            p.addItem(new Item("Item3"));
            session.save(p);

            session.getTransaction().commit();

        } finally {
            sessionFactory.close();
        }

    }
}
