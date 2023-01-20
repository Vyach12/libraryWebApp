package ru.gusarov.library.services;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.gusarov.library.modules.Book;
import ru.gusarov.library.modules.Person;
import ru.gusarov.library.repositories.BooksRepository;
import ru.gusarov.library.repositories.PeopleRepository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class BooksService {
    private final BooksRepository booksRepository;
    private final PeopleRepository peopleRepository;

    @Autowired
    public BooksService(BooksRepository booksRepository,
                        PeopleRepository peopleRepository) {
        this.booksRepository = booksRepository;
        this.peopleRepository = peopleRepository;
    }

    public List<Book> findAll() {
        return booksRepository.findAll();
    }

    public Book findBookById(int id) {
        return booksRepository.findById(id).orElse(null);
    }

    @Transactional
    public void save(Book book) {
        booksRepository.save(book);
    }

    @Transactional
    public void update(int id, Book updatedBook) {
        updatedBook.setId(id);
        booksRepository.save(updatedBook);
    }

    @Transactional
    public void delete(int id) {
        booksRepository.deleteById(id);
    }

    @Transactional
    public void changeOwner(Book book, Person person) {
        book.setOwner(person);
        booksRepository.save(book);
    }

    public List<Book> findBooksByOwnerId(int id) {
        Optional<Person> owner = peopleRepository.findById(id);
        if(owner.isPresent()) {
            Hibernate.initialize(owner.get().getBooks());
            return owner.get().getBooks();
        }
        return Collections.emptyList();
    }
    
    public Optional<Person> findOwnerByIdBook(int idBook) {
        Optional<Book> book = booksRepository.findById(idBook);
        return book.map(value -> Optional.ofNullable(value.getOwner()))
                .orElse(null);
    }
}
