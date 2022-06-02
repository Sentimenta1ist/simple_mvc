package Project2Boot.repositories;

import Project2Boot.models.Book;
import Project2Boot.models.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BooksRepository extends JpaRepository<Book, Integer> {

    List<Book> findByNameStartingWith(String startingWith);

}
