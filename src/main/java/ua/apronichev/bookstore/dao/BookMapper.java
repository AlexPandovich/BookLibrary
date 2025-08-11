package ua.apronichev.bookstore.dao;

import org.springframework.jdbc.core.RowMapper;
import ua.apronichev.bookstore.model.Book;

import java.sql.ResultSet;
import java.sql.SQLException;

public class BookMapper implements RowMapper<Book> {
    @Override
    public Book mapRow(ResultSet rs, int rowNum) throws SQLException {
        Book book = new Book();
        book.setId(rs.getInt("id"));
        book.setAuthor(rs.getString("author"));
        book.setName(rs.getString("name"));
        book.setYearOfProduction(rs.getInt("year"));

        return book;
    }
}
