package by.kaziulin.spring.Project_2Boot.repositories;

import by.kaziulin.spring.Project_2Boot.models.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BooksRepository extends JpaRepository<Book, Integer> {

   List<Book> findByTitleContainingIgnoreCase(String title);
   Page<Book> findAll(Pageable pageable);
  // List<Book> fi(Sort sort);
        }
