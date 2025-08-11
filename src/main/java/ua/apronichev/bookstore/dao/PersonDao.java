package ua.apronichev.bookstore.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ua.apronichev.bookstore.model.Book;
import ua.apronichev.bookstore.model.Person;

import java.util.ArrayList;
import java.util.List;

@Component
public class PersonDao {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public PersonDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Person> index() {
        return jdbcTemplate.query("select * from person", new BeanPropertyRowMapper<>(Person.class));
    }

    public Person show(int id) {
        return jdbcTemplate.query("select * from person where id=?",
                new Object[]{id}, new BeanPropertyRowMapper<>(Person.class)).stream().findAny().orElse(null);
    }

    public void save(Person person) {
        jdbcTemplate.update("insert into person(name, yearofbirth) VALUES (?, ?)",
                person.getName(), person.getYearOfBirth());
    }

    public void update(Person person, int id) {
        jdbcTemplate.update("update person set name=?, yearofbirth=? where id=?",
                person.getName(), person.getYearOfBirth(), person.getId());
    }

    public void delete(int id) {
        jdbcTemplate.update("delete from person where id=?", id);
    }

    public List<Book> getBooksByPersonId(int personId) {
        return jdbcTemplate.query("select * from book where person_id=?", new Object[] {personId}, new BookMapper());
    }
}
