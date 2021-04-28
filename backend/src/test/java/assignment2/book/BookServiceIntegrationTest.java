package assignment2.book;

import assignment2.TestCreationFactory;
import assignment2.book.model.Book;
import assignment2.book.model.dto.BookDTO;
import assignment2.book.repository.BookRepository;
import assignment2.book.service.BookService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class BookServiceIntegrationTest {
    @Autowired
    private BookService bookService;

    @Autowired
    private BookRepository bookRepository;

    @BeforeEach
    void setUp() {
        bookRepository.deleteAll();
    }

    @Test
    void findAll() {
        List<Book> books = TestCreationFactory.listOf(Book.class);

        bookRepository.saveAll(books);

        List<BookDTO> all = bookService.findAll();

        Assertions.assertEquals(books.size(), all.size());
    }

    @Test
    void create(){
        Book book = Book.builder()
                .author("Karl Marx")
                .title("Das Kapital")
                .genre("Political")
                .price(35.0F)
                .quantity(10L)
                .build();

        bookRepository.save(book);

        BookDTO bookDTO = BookDTO.builder()
                .id(5L)
                .author("Karl Marx")
                .title("Das Kapital")
                .genre("Political")
                .price(35.0F)
                .quantity(10L)
                .build();

        Assertions.assertNotNull(bookService.create(bookDTO));
    }

    @Test
    void delete(){
        BookDTO bookDTO = BookDTO.builder()
                .id(5L)
                .author("Karl Marx")
                .title("Das Kapital")
                .genre("Political")
                .price(35.0F)
                .quantity(10L)
                .build();

        bookDTO = bookService.create(bookDTO);
        bookService.delete(bookDTO.getId());
    }

    @Test
    void edit(){
        BookDTO bookDTO = BookDTO.builder()
                .id(5L)
                .author("Karl Marx")
                .title("Das Kapital")
                .genre("Political")
                .price(35.0F)
                .quantity(10L)
                .build();

        bookDTO = bookService.create(bookDTO);
        Assertions.assertEquals(bookDTO.getId(), bookService.edit(bookDTO.getId(), bookDTO).getId());
    }

    @Test
    void sell() {
        BookDTO bookDTO = BookDTO.builder()
                .id(5L)
                .author("Karl Marx")
                .title("Das Kapital")
                .genre("Political")
                .price(35.0F)
                .quantity(10L)
                .build();

        bookDTO = bookService.create(bookDTO);
        Assertions.assertTrue(bookService.sell(bookDTO.getId(), 1));
    }
}
