package ua.apronichev.bookstore.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ua.apronichev.bookstore.model.Book;
import ua.apronichev.bookstore.model.Person;
import ua.apronichev.bookstore.services.BooksService;
import ua.apronichev.bookstore.services.PeopleService;

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

    @GetMapping
    public String index(Model model,
            @RequestParam(required = false, defaultValue = "0",name = "page") Integer page,
            @RequestParam(required = false, defaultValue = "#{T(java.lang.Integer).MAX_VALUE}", name = "size") Integer size,
            @RequestParam(required = false, defaultValue = "false", name = "sort_by_year") boolean sortByYear) {

        model.addAttribute("books", booksService.findAll(page, size, sortByYear));
        return "books/index";
    }
    @GetMapping("/new")
    public String newBook(Model model) {
        model.addAttribute("book", new Book());
        return "books/new";
    }
    @PostMapping("")
    public String create(@ModelAttribute("book") @Valid Book book, BindingResult bindingResult) {
        if(bindingResult.hasErrors())
            return "books/new";

        booksService.save(book);
        return "redirect:/books";
    }
    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, @ModelAttribute("person") Person person, Model model) {
        Book book = booksService.findById(id).get();
        model.addAttribute("book", book);
        Person owner = book.getPerson();

        if(owner != null) {
            model.addAttribute("owner", owner);
        } else {
            model.addAttribute("people", peopleService.findAll());
        }
        return "books/show";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        booksService.delete(id);
        return "redirect:/books";
    }
    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {
        Book book = booksService.findById(id).get();
        model.addAttribute("book", book);
        return "books/edit";
    }
    @PatchMapping("/{id}")
    public String update(@ModelAttribute("book") @Valid Book book, BindingResult bindingResult, @PathVariable("id") int id) {
        if (bindingResult.hasErrors())
            return "books/edit";

        booksService.update(id, book);
        return "redirect:/books";
    }

    @PatchMapping("/{id}/assign")
    public String assignPerson(@PathVariable("id")int bookId, @ModelAttribute("person") Person person) {
        booksService.assignBookToPerson(bookId, person);
        return "redirect:/books/" + bookId;
    }

    @PatchMapping("/{id}/release")
    public String releaseBook(@PathVariable("id") int bookId) {
        booksService.releaseBook(bookId);
        return "redirect:/books/" + bookId;
    }
}
