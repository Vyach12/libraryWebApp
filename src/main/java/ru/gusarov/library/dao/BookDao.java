package ru.gusarov.library.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.gusarov.library.modules.Book;
import ru.gusarov.library.modules.Person;

import java.util.List;

@Component
public class BookDao {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public BookDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    public List<Book> index() {
        return jdbcTemplate.query("SELECT * FROM book", new BeanPropertyRowMapper<>(Book.class));
    }

    public Book show(int id) {
        return jdbcTemplate.query("SELECT * FROM book WHERE id=?", new BeanPropertyRowMapper<>(Book.class), id)
                .stream()
                .findAny()
                .orElse(null);
    }

    public void createBook(Book book) {
        jdbcTemplate.update("INSERT INTO book(title, author, year_date) VALUES(?,?,?)", book.getTitle(), book.getAuthor(), book.getYearDate());
    }

    public List<Book> getBooks(int idOwner) {
        return jdbcTemplate.query("SELECT * FROM book WHERE owner=?", new BeanPropertyRowMapper<>(Book.class), idOwner);
    }

    public void update(int id, Book updatedBook) {
        jdbcTemplate.update("UPDATE book SET title=?, author=?, year_date=? WHERE id=?", updatedBook.getTitle(), updatedBook.getAuthor(), updatedBook.getYearDate(), id);
    }

    public void changeOwner(int id, Person newOwner) {
        jdbcTemplate.update("UPDATE book SET owner=? WHERE id=?", newOwner.getId(), id);
    }

    public int getIdOwner(int id) {
        Number x = jdbcTemplate.queryForObject("SELECT owner from book WHERE id=?", Number.class, id);
        return (x!=null? x.intValue() : 0);
    }

    public void freeBook(int id) {
        jdbcTemplate.update("UPDATE book SET owner=? WHERE id=?", null, id);
    }
    public void delete(int id) {
        jdbcTemplate.update("DELETE from book WHERE id=?", id);
    }
}

