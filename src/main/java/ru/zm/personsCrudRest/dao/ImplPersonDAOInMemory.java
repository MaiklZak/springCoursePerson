package ru.zm.personsCrudRest.dao;

import org.springframework.stereotype.Component;
import ru.zm.personsCrudRest.models.Person;

import java.util.ArrayList;
import java.util.List;

@Component
public class ImplPersonDAOInMemory implements PersonDAO {

    private static int COUNT_PEOPLE;
    private List<Person> people;

    {
        people = new ArrayList<>();

        people.add(new Person(++COUNT_PEOPLE, "Tom", 20, "tom@mail.com"));
        people.add(new Person(++COUNT_PEOPLE, "Kate", 20, "kate@yehoo.com"));
        people.add(new Person(++COUNT_PEOPLE, "Mike", 30, "mike@goof.eu"));
        people.add(new Person(++COUNT_PEOPLE, "Linda", 30, "lind@mail.ru"));
    }

    public List<Person> index() {
        return people;
    }

    public Person show(int id) {
        return people.stream().filter(p -> p.getId() == id).findAny().orElse(null);
    }

    public void save(Person person) {
        person.setId(++COUNT_PEOPLE);
        people.add(person);
    }

    public void update(int id, Person person) {
        Person upPerson = show(id);
        upPerson.setName(person.getName());
        upPerson.setAge(person.getAge());
        upPerson.setEmail(person.getEmail());
    }

    public void delete(int id) {
        people.removeIf(person -> person.getId() == id);
    }
}
