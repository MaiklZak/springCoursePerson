package ru.zm.personsCrudRest.dao;

import org.springframework.stereotype.Component;
import ru.zm.personsCrudRest.models.Person;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class ImplPersonDAOInDB implements PersonDAO{

    private static int ID_PERSON = 100;

    private static final String URL = "jdbc:postgresql://localhost:5432/people";
    private static final String USERNAME = "postgres";
    private static final String PASSWORD = "postgres";
    private static Connection connection;

    static  {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    @Override
    public List<Person> index() {
        List<Person> result = new ArrayList<>();

        try {
            Statement statement = connection.createStatement();

            ResultSet set = statement.executeQuery("SELECT * FROM person");

            while (set.next()) {
                Person person = new Person();

                person.setId(set.getInt("id"));
                person.setName(set.getString("name"));
                person.setAge(set.getInt("age"));
                person.setEmail(set.getString("email"));

                result.add(person);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return result;
    }

    @Override
    public Person show(int id) {
        Person person = null;

        try {
            PreparedStatement preparedStatement =
                    connection.prepareStatement("SELECT * FROM person WHERE id=?");
            preparedStatement.setInt(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();

            person = new Person();

            resultSet.next();

            person.setId(resultSet.getInt("id"));
            person.setName(resultSet.getString("name"));
            person.setAge(resultSet.getInt("age"));
            person.setEmail(resultSet.getString("email"));

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return person;
    }

    @Override
    public void save(Person person) {
        try {
            PreparedStatement preparedStatement =
                    connection.prepareStatement("INSERT INTO person VALUES(?, ?, ?, ?)");

            preparedStatement.setInt(1, ++ID_PERSON);
            preparedStatement.setString(2, person.getName());
            preparedStatement.setInt(3, person.getAge());
            preparedStatement.setString(4, person.getEmail());

            preparedStatement.executeUpdate();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void update(int id, Person person) {
        try {
            PreparedStatement preparedStatement =
                connection.prepareStatement("UPDATE person SET name=?, age=?, email=? WHERE id=?");

                preparedStatement.setString(1, person.getName());
                preparedStatement.setInt(2, person.getAge());
                preparedStatement.setString(3, person.getEmail());
                preparedStatement.setInt(4, id);

                preparedStatement.executeUpdate();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void delete(int id) {
        try {
            PreparedStatement preparedStatement =
                    connection.prepareStatement("DELETE FROM person WHERE id=?");

            preparedStatement.setInt(1, id);

            preparedStatement.executeUpdate();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
