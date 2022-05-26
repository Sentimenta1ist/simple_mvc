package app.dao;

import app.models.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;


import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Component
public class PersonDAO {


    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public PersonDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Person> index() {
        return jdbcTemplate.query("SELECT * FROM Person", new BeanPropertyRowMapper<>(Person.class));
    }

    public Person show(int id) {

        return jdbcTemplate.queryForObject("SELECT * FROM Person WHERE id=?", new BeanPropertyRowMapper<>(Person.class), id);
    }

    public Optional<Person> show(String email) {
        return jdbcTemplate.query("SELECT * FROM Person WHERE email=?", new Object[] {email}, new BeanPropertyRowMapper<>(Person.class)).stream().findAny();
    }

    public void save(Person person) {
        jdbcTemplate.update("INSERT INTO person(name, age, email, address) values(?, ?, ?, ?)", person.getName(), person.getAge(), person.getEmail(), person.getAddress());
    }

    public void update(int id, Person person) {
        jdbcTemplate.update("UPDATE person SET name=?, age=?, email=?, address=? WHERE id=?", person.getName(), person.getAge(), person.getEmail(), person.getAddress(), id);
    }

    public void delete(int id) {
        jdbcTemplate.update("DELETE FROM person WHERE id=?", id);
    }

    /*
    TEST BATCH UPDATE
     */
    public void testBatchUpdate() {
        List<Person> people = createManyPeople(1000);

        jdbcTemplate.batchUpdate("INSERT INTO person VALUES(?, ?, ?, ?)",
                new BatchPreparedStatementSetter() {
                    @Override
                    public void setValues(PreparedStatement ps, int i) throws SQLException {
                        ps.setInt(1, people.get(i).getId());
                        ps.setString(2, people.get(i).getName());
                        ps.setInt(3, people.get(i).getAge());
                        ps.setString(4, people.get(i).getEmail());
                    }

                    @Override
                    public int getBatchSize() {
                        return people.size();
                    }
                });
    }

    private List<Person> createManyPeople(int n) {
        List<Person> people = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            people.add(new Person(i, "Name" + i, 30, "test" + i + "@gmail.com", "some addr"));
        }

        return people;
    }
}
