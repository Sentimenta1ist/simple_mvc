package app.models;


import javax.validation.constraints.*;

public class Person {
    private int id;

    @NotEmpty(message = "Empty name!")
    @Pattern(regexp = "[A-Z]\\w+ [A-Z]\\w+ [A-Z]\\w+", message = "Name should be in this format: 'Surname Name Second'")
    private String name;

    @Min(value = 1900, message = "Year should be biggest than 1900")
    private int year;

    public Person() {

    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getYear() {
        return year;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setYear(int year) {
        this.year = year;
    }
}
