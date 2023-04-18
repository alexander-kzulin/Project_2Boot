package by.kaziulin.spring.Project_2Boot.models;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Date;

@Entity
@Table(name = "Book")
public class Book {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

@Column(name = "title")
    @NotEmpty
    @Size(min = 2, max = 100, message = "Book name must be min - 1 max - 20 characters")
    private String title;



    @Column(name = "authorname")
    @NotEmpty
    @Size(min = 1, max = 40, message = "Author name must be min - 1 max - 30 characters")
    //@Pattern(regexp = "[A-Z]\\w+ [A-Z]\\w+", message = "Author Name wrong format ")
     private String authorName;

@Column(name = "date")
    @Min(value =  1000, message = "minimum year - 1")
    @Max(value = 2023, message = "maximum year - 2023")
    //@Pattern(regexp = "\\d{4}", message = "Year wrong format ")
    private int date;

    @ManyToOne
    @JoinColumn(name = "person_id", referencedColumnName = "id")
    private Person owner;

    @Column(name = "taken_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date takenAt;

    @Transient
    private boolean expired;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getTakenAt() {
        return takenAt;
    }

    public void setTakenAt(Date takenAt) {
        this.takenAt = takenAt;
    }

    public boolean isExpired() {
        return expired;
    }

    public void setExpired(boolean expired) {
        this.expired = expired;
    }

    public Person getOwner() {
        return owner;
    }

    public void setOwner(Person owner) {
        this.owner = owner;
    }

    public Book(String title, String authorName, int date) {

        this.title = title;
        this.authorName = authorName;
        this.date = date;
    }

    public Book(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return title;
    }

    public void setName(String title) {
        this.title = title;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public int getDate() {
        return date;
    }

    public void setDate(int date) {
        this.date = date;
    }

}
