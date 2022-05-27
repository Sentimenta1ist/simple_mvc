package app.controllers;

import app.dao.BookDAO;
import app.dao.PersonDAO;
import app.models.Book;
import app.models.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/books")
public class BooksController {
    private final BookDAO bookDAO;
    private final PersonDAO personDAO;


    @Autowired
    public BooksController(BookDAO bookDAO, PersonDAO personDAO) {
        this.bookDAO = bookDAO;
        this.personDAO = personDAO;
    }

    @GetMapping()
    public String index(Model model) {
        model.addAttribute("books", bookDAO.index());
        return "books/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model, @ModelAttribute("person") Person person) {
        Book book = bookDAO.show(id);

        model.addAttribute("book", book);
        model.addAttribute("people", personDAO.index());
        model.addAttribute("owner", bookDAO.personOfBookById(book.getPersonId()));

        return "books/show";
    }

    @PatchMapping("/{id}/pin_person")
    public String pinBook(@PathVariable("id") int id, @ModelAttribute("person") Person person) {
        bookDAO.setPersonToBook(person.getId(), id);
        return "redirect:/books/{id}";
    }

    @PatchMapping("/{id}/release_book")
    public String releaseBook(@PathVariable("id") int id, @ModelAttribute("person") Person person) {
        bookDAO.removePersonToBook(id);
        return "redirect:/books/{id}";
    }

    @GetMapping("/new")
    public String newPerson(Model model) {
        model.addAttribute("book", new Book());
        return "books/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("person") @Valid Book book, BindingResult bindingResult) {

        if (bindingResult.hasErrors())
            return "people/new";

        bookDAO.save(book);
        return "redirect:/books";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        bookDAO.delete(id);
        return "redirect:/books";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {
        model.addAttribute("book", bookDAO.show(id));
        return "books/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("book") @Valid Book book, BindingResult bindingResult, @PathVariable("id") int id) {

        if (bindingResult.hasErrors())
            return "books/edit";

        bookDAO.update(id, book);
        return "redirect:/books";
    }
}
