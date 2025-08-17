package ua.apronichev.bookstore.services;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.apronichev.bookstore.model.Book;
import ua.apronichev.bookstore.model.Person;
import ua.apronichev.bookstore.repositories.PeopleRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class PeopleService {
    PeopleRepository peopleRepository;

    @Autowired
    public PeopleService(PeopleRepository peopleRepository) {
        this.peopleRepository = peopleRepository;
    }

    public List<Person> findAll() {
        return peopleRepository.findAll();
    }

    public Optional<Person> findById(int id) {
        return  peopleRepository.findById(id);
    }

    public Optional<Person> findByIdWithBooks(int id) {
        Optional<Person> optionalPerson = peopleRepository.findById(id);

        optionalPerson.ifPresent(person -> Hibernate.initialize(person.getBooks()));

        return optionalPerson;
    }

    @Transactional
    public void save(Person p) {
        peopleRepository.save(p);
    }

    @Transactional
    public void update(Person p, int id) {
        p.setId(id);
        peopleRepository.save(p);
    }

    @Transactional
    public void delete(int id) {
        peopleRepository.deleteById(id);
    }
}
