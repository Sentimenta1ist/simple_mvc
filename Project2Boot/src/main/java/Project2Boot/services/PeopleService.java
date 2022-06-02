package Project2Boot.services;


import Project2Boot.models.Book;
import Project2Boot.models.Person;
import Project2Boot.repositories.PeopleRepository;
import org.hibernate.Hibernate;
import org.hibernate.query.criteria.internal.expression.function.CurrentDateFunction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.beans.Transient;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class PeopleService {

    private final PeopleRepository peopleRepository;

    @Autowired
    public PeopleService(PeopleRepository peopleRepository) {
        this.peopleRepository = peopleRepository;
    }

    public List<Person> findAll() {
        return peopleRepository.findAll();
    }

    public Person findOne(int id) {
        return peopleRepository.findById(id).orElse(null);
    }

    public List<Book> getBooksByPersonId(int id) {
        Optional<Person> person = peopleRepository.findById(id);

        if (person.isPresent()) {
            Hibernate.initialize(person.get().getBooks());
            return updateBadTimes(person.get().getBooks());
        } else {
            return Collections.emptyList();
        }
    }

    @Transient
    private List<Book> updateBadTimes(List<Book> books) {
        for (Book book : books) {
            long diffInTime = new Date(System.currentTimeMillis()).getTime() - book.getTaken().getTime();
            long diffInDays = (diffInTime / (1000 * 60 * 60 * 24)) % 365;
            if (diffInDays > 10) {  // if difference more than 10 days
                book.setBadTime(true);
            }
        }

        return books;
    }

    public Optional<Person> findByName(String name) {
        return peopleRepository.findByName(name);
    }

    @Transactional
    public void save(Person person) {
        peopleRepository.save(person);
    }

    @Transactional
    public void update(int id, Person updatedPerson) {
        updatedPerson.setId(id);
        peopleRepository.save(updatedPerson);
    }

    @Transactional
    public void delete(int id) {
        peopleRepository.deleteById(id);
    }

}
