package ua.apronichev.bookstore.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.apronichev.bookstore.model.Book;

public interface BooksRepository extends JpaRepository<Book, Integer> {
}
