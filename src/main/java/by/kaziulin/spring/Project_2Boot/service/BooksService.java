package by.kaziulin.spring.Project_2Boot.service;

import by.kaziulin.spring.Project_2Boot.models.Book;
import by.kaziulin.spring.Project_2Boot.models.Person;
import by.kaziulin.spring.Project_2Boot.repositories.BooksRepository;
import by.kaziulin.spring.Project_2Boot.repositories.PeopleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;


@Service
@Transactional(readOnly = true)
public class BooksService {

    private final BooksRepository booksRepository;
    private final PeopleRepository peopleRepository;
    //private final List<Book> books;


    final private List<Book> books;

    public Page<Book> findAllBooks(Pageable pageable) {
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;
        List<Book> list;

        if (books.size() < startItem) {
            list = Collections.emptyList();
        } else {
            int toIndex = Math.min(startItem + pageSize, books.size());
            list = books.subList(startItem, toIndex);
        }

        return
                 new PageImpl<Book>(list, PageRequest.of(currentPage, pageSize), books.size());


    }

    @Autowired
    public BooksService(BooksRepository booksRepository, PeopleRepository peopleRepository, List<Book> books) {
        this.booksRepository = booksRepository;
        this.peopleRepository = peopleRepository;
        this.books = books;
    }



    public List<Book> findAllWithoutPagination(Integer page, Integer booksPerPage, boolean sortByYear) {

      //  if (!sortByYear)
            return booksRepository.findAll(PageRequest.of(page, booksPerPage, Sort.by("date"))).getContent();
      //  else
      //      return booksRepository.findAllSorting(Sort.by("date"));
    }


    public List <Book> findAll() {
        return booksRepository.findAll();
    }


    public Book findOne(int id) {
        Optional<Book> foundBook = booksRepository.findById(id);

        return foundBook.orElse(null);
    }


public List<Book> searchByTitle(String query) {
        return booksRepository.findByTitleContainingIgnoreCase(query);
}
    @Transactional
    public void save(Book book) {
        booksRepository.save(book);
    }


    @Transactional
    public void update(int id, Book updatedBook) {
        Book bookToBeUpdated = booksRepository.findById(id).get();
        updatedBook.setId(id);
        updatedBook.setOwner(bookToBeUpdated.getOwner());
        booksRepository.save(updatedBook);
    }

    @Transactional
    public void delete(int id) {
        booksRepository.deleteById(id);
    }

    @Transactional
    public Person getBookOwner(int id) {
        return booksRepository.findById(id).map(Book::getOwner).orElse(null);

    }

    @Transactional
    public void release(int id) {
        booksRepository.findById(id).ifPresent(
                book -> {
                    book.setOwner(null);
                    book.setTakenAt(null);
                }
        );

    }

    @Transactional
    public void assign(@PathVariable("id") int id, @ModelAttribute("person") Person selectedPerson) {
        booksRepository.findById(id).ifPresent(
                book -> {
                    book.setOwner(selectedPerson);
                    book.setTakenAt(new Date());
                }
        );

    }
}






