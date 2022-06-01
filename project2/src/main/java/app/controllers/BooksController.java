package app.controllers;

import app.models.Book;
import app.models.Person;
import app.services.BooksService;
import app.services.PeopleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/books")
public class BooksController {

    private final BooksService booksService;
    private final PeopleService peopleService;

    @Autowired
    public BooksController(BooksService booksService, PeopleService peopleService) {
        this.booksService = booksService;
        this.peopleService = peopleService;
    }

    @GetMapping()
    public String index(@RequestParam(value = "page", required = false) String page,
                        @RequestParam(value = "books_per_page", required = false) String number,
                        @RequestParam(value = "sort_by_year", required = false, defaultValue = "false") String sort,
                        Model model) {

        model.addAttribute("books", booksService.findAll(page, number, sort));
        return "books/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model, @ModelAttribute("person") Person person) {
        model.addAttribute("book", booksService.findOne(id));
        model.addAttribute("people", peopleService.findAll());
        model.addAttribute("owner", booksService.getOwnerByBookId(id));

        return "books/show";
    }

    @GetMapping("/search")
    public String search(@RequestParam(value = "start_from", required = false) String start, Model model) {
        model.addAttribute("start_from", start);
        model.addAttribute("books", booksService.findByNameStartingWith(start));
        return "books/search";
    }

    @PatchMapping("/{id}/pin_person")
    public String pinBook(@PathVariable("id") int id, @ModelAttribute("person") Person person) {
        booksService.setPersonToBook(id, person);
        return "redirect:/books/{id}";
    }

    @PatchMapping("/{id}/release_book")
    public String releaseBook(@PathVariable("id") int id, @ModelAttribute("person") Person person) {
        booksService.removePersonFromBook(id);
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

        booksService.save(book);
        return "redirect:/books";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        booksService.delete(id);
        return "redirect:/books";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {
        model.addAttribute("book", booksService.findOne(id));
        return "books/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("book") @Valid Book book, BindingResult bindingResult, @PathVariable("id") int id) {

        if (bindingResult.hasErrors())
            return "books/edit";

        booksService.update(id, book);
        return "redirect:/books";
    }
}
