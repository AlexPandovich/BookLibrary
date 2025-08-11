package ua.apronichev.bookstore.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ua.apronichev.bookstore.dao.BookDao;
import ua.apronichev.bookstore.dao.PersonDao;
import ua.apronichev.bookstore.model.Book;
import ua.apronichev.bookstore.model.Person;

import java.util.List;

@Controller
@RequestMapping("/books")
public class BooksController {
    BookDao bookDao;
    PersonDao personDao;

    @Autowired
    public BooksController(BookDao bookDao, PersonDao personDao) {
        this.bookDao = bookDao;
        this.personDao = personDao;
    }

    @GetMapping
    public String index(Model model) {
        model.addAttribute("books", bookDao.index());
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

        bookDao.save(book);
        return "redirect:/books";
    }
    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, @ModelAttribute("person") Person person, Model model) {
        Book book = bookDao.show(id);
        model.addAttribute("book", book);
        model.addAttribute("people", personDao.index());
        model.addAttribute("selectedPerson", personDao.show(book.getPersonId()));
        return "books/show";
    }
    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        bookDao.delete(id);
        return "redirect:/books";
    }
    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {
        Book book = bookDao.show(id);
        model.addAttribute("book", book);
        return "books/edit";
    }
    @PatchMapping("/{id}")
    public String update(@ModelAttribute("book") @Valid Book book, BindingResult bindingResult, @PathVariable("id") int id) {
        if (bindingResult.hasErrors())
            return "books/edit";

        bookDao.update(id, book);
        return "redirect:/books";
    }

    @PatchMapping("/{id}/choose")
    public String choosePerson(@PathVariable("id")int bookId, @ModelAttribute("person") Person person) {
        bookDao.choosePerson(bookId, person.getId());
        return "redirect:/books/" + bookId;
    }
    @GetMapping("/{id}/release")
    public String releaseBook(@PathVariable("id") int bookId) {
        bookDao.choosePerson(bookId, null);
        return "redirect:/books/" + bookId;
    }
}
