package ru.gusarov.library.modules;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

public class Person {
    private int id;
    @NotEmpty(message = "Это поле не может быть пустым")
    private String name;
    @NotEmpty(message = "Это поле не может быть пустым")
    @Pattern(regexp = "([0-9]{4})(-[0-9]{2}){2}", message = "Формат даты: YYYY-MM-DD")
    private String birthdate;

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
    public String getBirthdate() {
        return birthdate;
    }

    public static Person create() {
        return new Person();
    }
}
