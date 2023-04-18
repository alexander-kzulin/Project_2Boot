package by.kaziulin.spring.Project_2Boot.controllers;

import by.kaziulin.spring.Project_2Boot.models.Book;
import by.kaziulin.spring.Project_2Boot.models.Person;
import by.kaziulin.spring.Project_2Boot.service.BooksService;
import by.kaziulin.spring.Project_2Boot.service.PeopleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/book")

public class BookController {
    private final BooksService booksService;
    private final PeopleService peopleService;


    @Autowired
    public BookController(BooksService booksService, PeopleService peopleService) {
        this.booksService = booksService;

        this.peopleService = peopleService;
    }





    @GetMapping()
    public String index(Model model, @RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
                        @RequestParam(value = "books_per_page", required = false, defaultValue = "5") Integer booksPerPage,
                        @RequestParam(value = "sort_by_date", required = false) boolean sortByDate ) {

            model.addAttribute("book", booksService.findAllWithoutPagination(page,booksPerPage,sortByDate));





        return "book/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model, @ModelAttribute("person") Person person) {
        model.addAttribute("book", booksService.findOne(id));
        Person bookOwner = booksService.getBookOwner(id);
        if (bookOwner != null)
            model.addAttribute("owner", bookOwner);
        else
            model.addAttribute("people", peopleService.findAll());
        return "book/show";
    }

    @GetMapping("/new")
    public String newBook(@ModelAttribute("book") Book book) {
        return "book/new";
    }

    @PostMapping
    public String create(@ModelAttribute("book") @Valid Book book, BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "book/new";
        booksService.save(book);
        return "redirect:/book";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {
        model.addAttribute("book", booksService.findOne(id));
        return "book/edit";
    }

    @PostMapping("/{id}")
    public String update(@ModelAttribute("book") @Valid Book book, BindingResult bindingResult,
                         @PathVariable("id") int id) {
        if (bindingResult.hasErrors())
            return "book/edit";
        booksService.update(id, book);
        return "redirect:/book";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        booksService.delete(id);
        return "redirect:/book";
    }

    @PatchMapping("/{id}/release")
    public String release(@PathVariable("id") int id) {
        booksService.release(id);
        return "redirect:/book/" + id;
    }

    @PatchMapping("/{id}/assign")
    public String assign(@PathVariable("id") int id, @ModelAttribute("person") Person selectedPerson) {
        booksService.assign(id, selectedPerson);
        return "redirect:/book/" + id;
    }
    @GetMapping("/search")
    public String searchPage() {
        return "book/search";
    }

    @PostMapping("/search")
    public String makeSearch(Model model, @RequestParam("query") String query) {
        model.addAttribute("book", booksService.searchByTitle(query));
        return "book/search";
    }
}
