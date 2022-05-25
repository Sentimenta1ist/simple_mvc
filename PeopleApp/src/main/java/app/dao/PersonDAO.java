package app.dao;

import app.models.Person;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


//jdbc:postgresql://localhost:5432/postgres
@Component
public class PersonDAO {

    private static int PEOPLE_COUNT;

    private static final String URL = "jdbc:postgresql://localhost:5432/first_db";
    private static final String USERNAME = "postgres";
    private static final String PASSWORD = "6969";

    private static Connection connection;

    static {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        try {
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Person> index() {
        List<Person> people = new ArrayList<>();

        try {
            Statement statement = connection.createStatement();
            String sql = "select * from person";
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                Person person = new Person();

                person.setId(resultSet.getInt("id"));
                person.setName(resultSet.getString("name"));
                person.setEmail(resultSet.getString("email"));
                person.setAge(resultSet.getInt("age"));

                people.add(person);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return people;
    }

    public Person show(int id) {
        Person person = null;

        try {
            PreparedStatement prepareStatement = connection.prepareStatement("SELECT * FROM person WHERE id=?");
            prepareStatement.setInt(1, id);
            ResultSet resultSet = prepareStatement.executeQuery();
            resultSet.next();

            person = new Person();
            person.setId(resultSet.getInt("id"));
            person.setName(resultSet.getString("name"));
            person.setEmail(resultSet.getString("email"));
            person.setAge(resultSet.getInt("age"));

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return person;
    }

    public void save(Person person) {

        try {
            PreparedStatement prepareStatement = connection.prepareStatement("INSERT INTO person VALUES (1, ?, ?, ?)");
            prepareStatement.setString(1, person.getName());
            prepareStatement.setInt(2, person.getAge());
            prepareStatement.setString(3, person.getEmail());

            prepareStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void update(int id, Person person) {
        try {
            PreparedStatement prepareStatement = connection.prepareStatement("UPDATE person set name=?, age=?, email=? where id=?");

            prepareStatement.setString(1, person.getName());
            prepareStatement.setInt(2, person.getAge());
            prepareStatement.setString(3, person.getEmail());
            prepareStatement.setInt(4, id);

            prepareStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(int id) {
        try {
            PreparedStatement prepareStatement = connection.prepareStatement("DELETE FROM person where id=?");
            prepareStatement.setInt(1, id);
            prepareStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
