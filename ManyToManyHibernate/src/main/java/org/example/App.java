package org.example;

import org.example.model.Actor;
import org.example.model.Movie;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import javax.persistence.Column;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class App {
    public static void main(String[] args) {
        Configuration configuration = new Configuration().addAnnotatedClass(Actor.class).addAnnotatedClass(Movie.class);

        SessionFactory sessionFactory = configuration.buildSessionFactory();


        try (sessionFactory) {
            Session session = sessionFactory.getCurrentSession();
            session.beginTransaction();

            Movie movie = new Movie("movie1", 1994);
            Actor actor1 = new Actor("actor1", 23);
            Actor actor2 = new Actor("actor2", 45);

            movie.setActors(new ArrayList<>(List.of(actor1, actor2)));

            actor1.setMovies(new ArrayList<>(Collections.singletonList(movie)));
            actor2.setMovies(new ArrayList<>(Collections.singletonList(movie)));

            session.save(movie);
            session.save(actor1);
            session.save(actor2);


            Movie movie1 = session.get(Movie.class, 1);
            System.out.println(movie1.getActors());



            Movie movie2 = new Movie("Film2", 2031);
            Actor actor = session.get(Actor.class, 1);

            movie2.setActors(new ArrayList<>(Collections.singletonList(actor)));
            actor.getMovies().add(movie2);

            session.save(movie2);

            Actor actor3 = session.get(Actor.class, 2);
            System.out.println(actor.getMovies());

            Movie movieToRemove = actor.getMovies().get(0);

            actor.getMovies().remove(0);
            movieToRemove.getActors().remove(actor3);

            session.getTransaction().commit();
        }

    }
}
