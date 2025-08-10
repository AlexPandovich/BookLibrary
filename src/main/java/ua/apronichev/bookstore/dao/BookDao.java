package ua.apronichev.bookstore.dao;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ua.apronichev.bookstore.model.Book;

import java.util.List;

@Component
public class BookDao {
    private final JdbcTemplate jdbcTemplate;

    public BookDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Book> index() {
        return jdbcTemplate.query("select * from book", new BookMapper());
    }

    public void save(Book book) {
        jdbcTemplate.update("insert into book(name, author, year, person_id) VALUES (?, ?, ?, ?)",
                book.getName(), book.getAuthor(), book.getYearOfProduction(), null);
    }
    public Book show(int id) {
        return jdbcTemplate.query("select * from book where id = ?", new Object[]{id}, new BookMapper())
                .stream().findAny().orElse(null);
    }

    public void delete(int id) {
        jdbcTemplate.update("delete from book where id=?", id);
    }

    public void update(int id, Book book) {
        jdbcTemplate.update("update book set name=?, author=?, year=? where id = ?",
                book.getName(), book.getAuthor(), book.getYearOfProduction(), id);
    }
    public void choosePerson(int bookId, Integer personId) {
        jdbcTemplate.update("update book set person_id=? where id = ?",
               personId, bookId);
    }
}
