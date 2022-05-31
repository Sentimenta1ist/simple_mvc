package app.models;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.Objects;

@Entity
@Table(name = "Person")
public class Person {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotEmpty(message = "Empty name!")
    @Size(min = 2, max = 30, message = "name cane be from 2 to 30")
    @Column(name = "name")
    private String name;

    @Min(value = 0, message = "Age can be more than 0")
    @Column(name = "age")
    private int age;

    @NotEmpty(message = "Empty message")
    @Email(message = "Incorrect email")
    @Column(name = "email")
    private String email;

    /*@Pattern(regexp = "[A-Z]\\w+,[A-Z]\\w+,\\d{6}", message = "Address should be in this format: 'Country, City, Postal(######)'")
    @Column(name = "address")
    private String address;*/
    // country,town,index

    public Person() {

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return id == person.id && age == person.age && Objects.equals(name, person.name) && Objects.equals(email, person.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, age, email);
    }

    public Person(int id, String name, int age, String email) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.email = email;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
