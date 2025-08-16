package ua.apronichev.bookstore.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.apronichev.bookstore.model.Book;
import ua.apronichev.bookstore.model.Person;
import ua.apronichev.bookstore.repositories.BooksRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class BooksService {
    private final BooksRepository booksRepository;

    public BooksService(BooksRepository booksRepository) {
        this.booksRepository = booksRepository;
    }

    public List<Book> findAll() {
        return booksRepository.findAll();
    }

    public void save(Book book) {
        booksRepository.save(book);
    }

    public Optional<Book> findById(int bookId) {
        return booksRepository.findById(bookId);
    }

    public void delete(int bookId) {
        Book book = new Book();
        book.setId(bookId);
        booksRepository.delete(book);
    }
    public void update(int bookId, Book book) {
        book.setId(bookId);
        booksRepository.save(book);
    }

    public void assignBookToPerson(int bookId, Person person) {
        Book book = new Book();
        book.setId(bookId);
        book.setPerson(person);

        this.save(book);
    }

    public void releaseBook(int bookId) {
        Optional<Book> book = booksRepository.findById(bookId);
        if(book.isPresent()) {
            book.get().setPerson(null);
            booksRepository.save(book.get());
        }
    }

}
