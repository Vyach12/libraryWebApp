package ru.gusarov.library.modules;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Entity
@Table(name = "book")
public class Book {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotEmpty(message = "Это поле не может быть пустым")
    @Size(min = 2,max = 30, message = "Название должно быть от 2 до 30 символов")
    @Column(name = "title")
    private String title;
    @NotEmpty(message = "Это поле не может быть пустым")
    @Size(min = 2,max = 30, message = "Имя автора должно быть от 2 до 30 символов")
    @Column(name = "author")
    private String author;

    @Column(name = "year_date")
    private int yearDate;

    @ManyToOne
    @JoinColumn(name="owner", referencedColumnName = "id")
    private Person owner;


    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setYearDate(int yearDate) {
        this.yearDate = yearDate;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public int getYearDate() {
        return yearDate;
    }

    public Person getOwner() {
        return owner;
    }

    public void setOwner(Person owner) {
        this.owner = owner;
    }
}
