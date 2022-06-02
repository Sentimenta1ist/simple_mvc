package Project2Boot.services;

import Project2Boot.models.Book;
import Project2Boot.models.Person;
import Project2Boot.repositories.BooksRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class BooksService {

    private final BooksRepository booksRepository;

    @Autowired
    public BooksService(BooksRepository booksRepository) {
        this.booksRepository = booksRepository;
    }


    public List<Book> findAll(String page, String booksPerPage, String sortByYear) {
        Sort sort = sortByYear.equals("true") ? Sort.by("year") : Sort.unsorted();

        if (page != null && booksPerPage != null) {
            return booksRepository.findAll(PageRequest.of(Integer.parseInt(page), Integer.parseInt(booksPerPage), sort))
                    .getContent();
        } else {
            return booksRepository.findAll(sort);
        }
    }

    public List<Book> findByNameStartingWith(String startingWith) {
        return booksRepository.findByNameStartingWith(startingWith);
    }

    public Book findOne(int id) {
        return booksRepository.findById(id).orElse(null);
    }

    public Person getOwnerByBookId(int id) {
        return booksRepository.findById(id).map(Book::getOwner).orElse(null);
    }

    @Transactional
    public void removePersonFromBook(int id) {
        Book book = findOne(id);
        book.setOwner(null);
        book.setTaken(null);
        book.setBadTime(false);
    }

    @Transactional
    public void setPersonToBook(int id, Person person) {
        Book book = findOne(id);
        book.setOwner(person);
        book.setTaken(new Date(System.currentTimeMillis()));
    }

    @Transactional
    public void save(Book Book) {
        booksRepository.save(Book);
    }

    @Transactional
    public void update(int id, Book updatedBook) {
        updatedBook.setId(id);
        booksRepository.save(updatedBook);
    }

    @Transactional
    public void delete(int id) {
        booksRepository.deleteById(id);
    }
}
