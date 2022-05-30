package org.example;

import org.example.model.Passport;
import org.example.model.Person;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class App {
    public static void main(String[] args) {
        Configuration configuration = new Configuration().addAnnotatedClass(Person.class).addAnnotatedClass(Passport.class);

        SessionFactory sessionFactory = configuration.buildSessionFactory();
        Session session = sessionFactory.getCurrentSession();

        try {
            session.beginTransaction();

            Person person = new Person("Test person", 50);
            Passport passport = new Passport(12345);
            person.setPassport(passport);
            session.save(person);


            Person person1 = session.get(Person.class, 4);
            System.out.println(person1.getPassport());

            Passport passport1 = session.get(Passport.class, 4);
            System.out.println(passport1.getPerson().getName());


            Person person2 = session.get(Person.class, 4);
            person2.getPassport().setNumber(7777);

            Person person3 = session.get(Person.class, 4);
            session.remove(person3);

            session.getTransaction().commit();


        } finally {
            sessionFactory.close();
        }

    }
}
