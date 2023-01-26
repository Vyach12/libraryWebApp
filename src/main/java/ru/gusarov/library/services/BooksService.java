package ru.gusarov.library.services;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.gusarov.library.modules.Book;
import ru.gusarov.library.modules.Person;
import ru.gusarov.library.repositories.BooksRepository;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class BooksService {
    private final BooksRepository booksRepository;
    private final PeopleService peopleService;

    @Autowired
    public BooksService(BooksRepository booksRepository, PeopleService peopleService) {
        this.booksRepository = booksRepository;
        this.peopleService = peopleService;
    }

    public List<Book> findAll(boolean sortByYear) {
        if(sortByYear) {
            return booksRepository.findAll(Sort.by("yearDate"));
        }
        return booksRepository.findAll();
    }

    public List<Book> findAllWithPagination(int page, int booksPerPage, boolean sortByYear) {
        if(sortByYear){
            return booksRepository.findAll(PageRequest.of(page,booksPerPage,Sort.by("yearDate"))).getContent();
        }
        return booksRepository.findAll(PageRequest.of(page, booksPerPage)).getContent();
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
        updatedBook.setOwner(booksRepository.findById(id).get().getOwner());
        booksRepository.save(updatedBook);
    }

    @Transactional
    public void delete(int id) {
        booksRepository.deleteById(id);
    }

    @Transactional
    public void assign(int id, Person person) {
        booksRepository.findById(id).ifPresent(book ->{
            book.setTakenAt(new Date());
            book.setOwner(person);
        });
    }

    @Transactional
    public void release(int id) {
        booksRepository.findById(id).ifPresent(book -> {
            book.setTakenAt(null);
            book.setOwner(null);
        });
    }
    
    public Optional<Person> findOwnerByIdBook(int id) {
        return booksRepository.findById(id).map(Book::getOwner);
    }

    public List<Book> searchBooksByQuery(String query) {
        return booksRepository.searchBookByTitleContaining(query);
    }

    public List<Book> findBooksByOwnerId(int id) {
        Optional<Person> owner = Optional.ofNullable(peopleService.findById(id));
        if (owner.isPresent()) {
            Hibernate.initialize(owner.get().getBooks());
            owner.get().getBooks().forEach(book -> {
                if (new Date().getTime() - book.getTakenAt().getTime() > 864000000) {
                    book.setExpired(true);
                }
            });
            return owner.get().getBooks();
        }
        return Collections.emptyList();
    }
}
