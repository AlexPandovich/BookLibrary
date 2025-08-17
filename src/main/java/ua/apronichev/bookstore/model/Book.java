package ua.apronichev.bookstore.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import org.springframework.beans.factory.annotation.Value;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Entity
@Table(name = "book")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotEmpty(message = "Book name shouldn't be empty")
    @Size(min = 3, max = 100, message = "Book name should be greater than 3 and less than 100 characters")
    private String name;
    @NotEmpty(message = "Author name shouldn't be empty")
    @Size(min = 3, max = 100, message = "Author name should be greater than 3 and less than 100 characters")
    private String author;
    @Min(value = 0, message = "year should be greater than zero")
    @Column(name = "year")
    private int yearOfProduction;

    @ManyToOne
    @JoinColumn(name = "person_id", referencedColumnName = "id")
    private Person person;

    @Column(name = "assign_time")
    LocalDateTime assignTime;

    @Value("${overdue_days}")
    @Transient
    private int overdueDays;

    public boolean isOverdue() {
        boolean result = false;
        if (assignTime != null) {
            long days = ChronoUnit.DAYS.between(assignTime, LocalDateTime.now());
            if(days > 10)
                result = true;
        }

        return result;
    }

    public Book() {

    }

    public Book(int id, String name, String author, int yearOfProduction) {
        this.id = id;
        this.name = name;
        this.author = author;
        this.yearOfProduction = yearOfProduction;
    }

    public LocalDateTime getAssignTime() {
        return assignTime;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;

        if (person != null)
            assignTime = LocalDateTime.now();
        else
            assignTime = null;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getYearOfProduction() {
        return yearOfProduction;
    }

    public void setYearOfProduction(int yearOfProduction) {
        this.yearOfProduction = yearOfProduction;
    }
}
