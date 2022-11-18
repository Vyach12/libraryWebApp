package ru.gusarov.library.modules;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class Book {
    private int id;
    @NotEmpty(message = "Это поле не может быть пустым")
    @Size(min=2,max=30, message = "Название должно быть от 2 до 30 символов")
    private String title;
    @NotEmpty(message = "Это поле не может быть пустым")
    @Size(min=2,max=30, message = "Имя автора должно быть от 2 до 30 символов")
    private String author;

    private int yearDate;

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
}
