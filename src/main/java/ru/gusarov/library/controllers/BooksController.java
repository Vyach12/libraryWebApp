package ru.gusarov.library.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.gusarov.library.modules.Book;
import ru.gusarov.library.modules.Person;
import ru.gusarov.library.services.BooksService;
import ru.gusarov.library.services.PeopleService;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/books")
public class BooksController {
    private final PeopleService peopleService;
    private final BooksService booksService;

    @Autowired
    public BooksController(PeopleService peopleService, BooksService booksService) {
        this.peopleService = peopleService;
        this.booksService = booksService;
    }

    @GetMapping()
    public String index(Model model, @RequestParam(value = "page") Optional<Integer> page,
                        @RequestParam(value = "books_per_page") Optional<Integer> booksPerPage,
                        @RequestParam(value = "sort_by_year", required = false)boolean sortByYear) {

        List<Book> books;
        if (page.isPresent() && booksPerPage.isPresent()) {
            books = booksService.findAllWithPagination(page.get(), booksPerPage.get(), sortByYear);
        }  else {
            books = booksService.findAll(sortByYear);
        }
        model.addAttribute("books", books);

        return "books/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model, @ModelAttribute("person") Person person) {
        model.addAttribute("book", booksService.findBookById(id));
        Optional<Person> owner = booksService.findOwnerByIdBook(id);
        if (owner.isPresent()) {
            model.addAttribute("owner", owner.get());
        } else {
            model.addAttribute("people", peopleService.findAll());
        }
        return "books/show";
    }
    @GetMapping("/new")
    public String newBook(@ModelAttribute("book") Book book) {
        return "books/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("book")@Valid Book book, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            return "/books/new";
        }
        booksService.save(book);
        return "redirect:/books";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id") int id, Model model) {
        model.addAttribute("book", booksService.findBookById(id));
        return "books/edit";
    }

    @PostMapping("/{id}")
    public String update(@PathVariable("id")int id, @ModelAttribute("book") @Valid Book book,
                         BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "people/edit";
        booksService.update(id, book);
        return "redirect:/books/" + id;
    }

    @PostMapping("/{id}/assign")
    public String assign(@PathVariable("id")int id, @ModelAttribute("person")Person person) {
        booksService.assign(id, person);
        return "redirect:/books";
    }

    @PostMapping("/{id}/release")
    public String release(@PathVariable("id")int id) {
        booksService.release(id);
        return "redirect:/books";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable("id")int id) {
        booksService.delete(id);
        return "redirect:/books";
    }

    @GetMapping("/search")
    public String search() {
        return "books/search";
    }

    @PostMapping("/search")
    public String search(Model model, @RequestParam(value = "query", required = false)String query) {
        model.addAttribute("books", booksService.searchBooksByQuery(query));
        return "/books/search";
    }
}
