package ua.apronichev.bookstore.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.apronichev.bookstore.model.Book;
import ua.apronichev.bookstore.model.Person;
import ua.apronichev.bookstore.repositories.BooksRepository;
import ua.apronichev.bookstore.repositories.PeopleRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class BooksService {
    private final BooksRepository booksRepository;
    private final PeopleRepository peopleRepository;

    public BooksService(BooksRepository booksRepository, PeopleRepository peopleRepository) {
        this.booksRepository = booksRepository;
        this.peopleRepository = peopleRepository;
    }

    public List<Book> findAll() {
        return booksRepository.findAll();
    }

    @Transactional
    public void save(Book book) {
        booksRepository.save(book);
    }

    public Optional<Book> findById(int bookId) {
        return booksRepository.findById(bookId);
    }

    @Transactional
    public void delete(int bookId) {
        Book book = new Book();
        book.setId(bookId);
        booksRepository.delete(book);
    }

    @Transactional
    public void update(int bookId, Book book) {
        book.setId(bookId);
        booksRepository.save(book);
    }

    @Transactional
    public void assignBookToPerson(int bookId, Person person) {
        Optional<Book> optionalBook = booksRepository.findById(bookId);
        if (person != null)
            person = peopleRepository.findById(person.getId()).get();

        if(optionalBook.isPresent()) {
            Book book = optionalBook.get();
            book.setPerson(person);
            person.getBooks().add(book);
        }
    }

    @Transactional
    public void releaseBook(int bookId) {
        Optional<Book> optionalBook = booksRepository.findById(bookId);

        if (optionalBook.isPresent()) {
            Book book = optionalBook.get();
            Person owner = book.getPerson();

            if (owner != null) {
                owner.getBooks().remove(book); // удаляем книгу из списка владельца
                book.setPerson(null);          // отвязываем владельца от книги
            }
        }
    }
}
