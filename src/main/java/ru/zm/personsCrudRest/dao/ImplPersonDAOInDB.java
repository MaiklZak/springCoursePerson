package ru.zm.personsCrudRest.dao;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.zm.personsCrudRest.models.Person;

import java.util.List;

@Component
public class ImplPersonDAOInDB implements PersonDAO{

    private static int ID_PERSON = 100;

    private final JdbcTemplate jdbcTemplate;

    public ImplPersonDAOInDB(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Person> index() {
        return jdbcTemplate.query("SELECT * FROM person", new BeanPropertyRowMapper<>(Person.class));
    }

    @Override
    public Person show(int id) {
        return jdbcTemplate.query("SELECT * FROM person WHERE id=?", new BeanPropertyRowMapper<>(Person.class), id)
                .stream().findAny().orElse(null);
    }

    @Override
    public void save(Person person) {
        jdbcTemplate.update("INSERT INTO person VALUES (?, ?, ?, ?)", ++ID_PERSON, person.getName(),
                person.getAge(), person.getEmail());
    }

    @Override
    public void update(int id, Person person) {
        jdbcTemplate.update("UPDATE person SET name=?, age=?, email=? WHERE id=?",
                person.getName(), person.getAge(), person.getEmail(), id);
    }

    @Override
    public void delete(int id) {

        jdbcTemplate.update("DELETE FROM person WHERE id=?", id);

    }
}
