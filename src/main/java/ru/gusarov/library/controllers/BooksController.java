package ru.gusarov.library.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.gusarov.library.dao.BookDao;
import ru.gusarov.library.dao.PersonDao;
import ru.gusarov.library.modules.Book;
import ru.gusarov.library.modules.Person;

import javax.validation.Valid;

@Controller
@RequestMapping("/books")
public class BooksController {
    private final BookDao bookDao;
    private final PersonDao personDao;

    @Autowired
    public BooksController(BookDao bookDao, PersonDao personDao) {
        this.bookDao = bookDao;
        this.personDao = personDao;
    }

    @GetMapping()
    public String index(Model model) {
        model.addAttribute("books", bookDao.index());
        return "books/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model) {
        model.addAttribute("person", personDao.show(bookDao.getIdOwner(id)));
        model.addAttribute("book", bookDao.show(id));
        model.addAttribute("people", personDao.index());
        model.addAttribute("newOwner", new Person());
        return "books/show";
    }
    @GetMapping("/new")
    public String newBook(@ModelAttribute("book") Book book) {
        return "books/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("book")@Valid Book book, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            return "/books/edit";
        }
       bookDao.createBook(book);
       return "redirect:/books";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id") int id, Model model) {
        model.addAttribute("book", bookDao.show(id));
        return "books/edit";
    }

    @PostMapping("/{id}")
    public String update(@PathVariable("id")int id, @ModelAttribute("book") @Valid Book book, BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "people/edit";
        bookDao.update(id, book);
        return "redirect:/books";
    }

    @PostMapping("/{id}/changeOwner")
    public String changeOwner(@PathVariable("id") int id, @ModelAttribute("newOwner")Person newOwner) {
        if(newOwner.getId() == 0) {
            bookDao.freeBook(id);
        } else {
            bookDao.changeOwner(id, newOwner);
        }
        return "redirect:/books";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable("id")int id) {
        bookDao.delete(id);
        return "redirect:/books";
    }
}
