package ua.apronichev.bookstore.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.apronichev.bookstore.model.Book;

@Repository
public interface BooksRepository extends JpaRepository<Book, Integer> {
}
