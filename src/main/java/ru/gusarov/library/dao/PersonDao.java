package ru.gusarov.library.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.gusarov.library.modules.Person;

import java.util.List;

@Component
public class PersonDao {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public PersonDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Person> index() {
        return jdbcTemplate.query("SELECT * FROM person", new BeanPropertyRowMapper<>(Person.class));
    }

    public void createPerson(Person person) {
        jdbcTemplate.update("INSERT INTO person(name, birthdate) VALUES(?,?)", person.getName(), person.getBirthdate());
    }

    public Person show(int id) {
        return jdbcTemplate.query("SELECT * FROM person WHERE id=?", new BeanPropertyRowMapper<>(Person.class), id)
                .stream()
                .findAny()
                .orElse(null);
    }


    public void update(int id, Person updatedPerson) {
        jdbcTemplate.update("UPDATE person SET name=?, birthdate=?  WHERE id=?", updatedPerson.getName(), updatedPerson.getBirthdate(), id);
    }
    public void delete(int id) {
        jdbcTemplate.update("DELETE FROM person  WHERE id = ?", id);
    }
}
