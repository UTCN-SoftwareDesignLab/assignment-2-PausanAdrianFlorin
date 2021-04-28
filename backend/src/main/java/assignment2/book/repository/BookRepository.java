package assignment2.book.repository;

import assignment2.book.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long>  {

    List<Book> findByTitleContainingAndAuthorContainingAndGenreContainingIgnoreCase(String title, String author, String genre);
}
