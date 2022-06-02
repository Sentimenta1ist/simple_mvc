package Project2Boot.models;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import java.util.Date;

@Entity
@Table(name = "Book")
public class Book {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotEmpty(message = "Empty name!")
    @Column(name = "name")
    private String name;

    @Min(value = 0, message = "Year should be more than 0")
    @Column(name = "year")
    private int year;

    @NotEmpty(message = "Empty author!")
    @Column(name = "author")
    private String author;

    @ManyToOne
    @JoinColumn(name = "person_id", referencedColumnName = "id")
    private Person owner;

    @Column(name = "taken")
    @Temporal(TemporalType.TIMESTAMP)
    private Date taken;

    @Transient
    private boolean badTime = false;

    public Book() {

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

    public String getAuthor() {
        return author;
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

    public void setAuthor(String author) {
        this.author = author;
    }

    public Person getOwner() {
        return owner;
    }

    public void setOwner(Person owner) {
        this.owner = owner;
    }

    public Date getTaken() {
        return taken;
    }

    public void setTaken(Date taken) {
        this.taken = taken;
    }

    public boolean isBadTime() {
        return badTime;
    }

    public void setBadTime(boolean badTime) {
        this.badTime = badTime;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", year=" + year +
                ", author='" + author + '\'' +
                ", owner=" + owner +
                ", taken=" + taken +
                ", badTime=" + badTime +
                '}';
    }
}
