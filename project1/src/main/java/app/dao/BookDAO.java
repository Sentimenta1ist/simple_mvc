package app.dao;

import app.models.Book;
import app.models.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BookDAO {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public BookDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Book> index() {
        return jdbcTemplate.query("SELECT * FROM book", new BookMapper());
    }

    public Book show(int id) {
        return (Book) jdbcTemplate.queryForObject("SELECT * FROM book WHERE id=?", new BookMapper(), id);
    }

    public void save(Book book) {
        jdbcTemplate.update("INSERT INTO book(name, author, year) values(?, ?, ?)", book.getName(), book.getAuthor(), book.getYear());
    }

    public void delete(int id) {
        jdbcTemplate.update("DELETE FROM book WHERE id=?", id);
    }

    public void update(int id, Book book) {
        jdbcTemplate.update("UPDATE book SET name=?, author=?, year=? WHERE id=?", book.getName(), book.getAuthor(), book.getYear(), id);
    }

    public Person personOfBookById(int id){
        return jdbcTemplate.query("SELECT person.id, person.year, person.name from person join book b on person.id = b.person_id where person.id=?",
                new BeanPropertyRowMapper<>(Person.class), id).stream().findAny().orElse(null);
    }

    public void setPersonToBook(int personId, int bookId) {
        jdbcTemplate.update("UPDATE book SET person_id=? WHERE id=?", personId, bookId);
    }

    public void removePersonToBook(int bookId) {
        jdbcTemplate.update("UPDATE book SET person_id=null WHERE id=?", bookId);
    }
}
