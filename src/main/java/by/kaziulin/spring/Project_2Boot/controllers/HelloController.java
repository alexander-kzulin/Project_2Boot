package by.kaziulin.spring.Project_2Boot.controllers;


import by.kaziulin.spring.Project_2Boot.service.BooksService;
import by.kaziulin.spring.Project_2Boot.service.PeopleService;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/hello")
public class HelloController {

    private final PeopleService peopleService;
    private final BooksService booksService;

    public HelloController(PeopleService peopleService, BooksService booksService) {
        this.peopleService = peopleService;
        this.booksService = booksService;
    }

    @GetMapping
    public String index(Model model) {
        model.addAttribute(peopleService.findAll());
        model.addAttribute(booksService.findAll());
    return "hello/index";
    }

}
