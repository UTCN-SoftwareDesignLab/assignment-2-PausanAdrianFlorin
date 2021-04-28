package assignment2.book;

import assignment2.TestCreationFactory;
import assignment2.book.model.Book;
import assignment2.book.repository.BookRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class BookRepositoryTest {

    @Autowired
    private BookRepository repository;

    @BeforeEach
    public void beforeEach() {
        repository.deleteAll();
    }

    @Test
    public void testMock() {
        Book bookSaved = repository.save(Book.builder()
                .author("Karl Marx")
                .title("Das Kapital")
                .genre("Political")
                .price(35.0F)
                .quantity(10L)
                .build());

        assertNotNull(bookSaved);

        assertThrows(DataIntegrityViolationException.class, () -> {
            repository.save(Book.builder().build());
        });
    }

    @Test
    public void testFindAll() {
        List<Book> items = TestCreationFactory.listOf(Book.class);

        repository.saveAll(items);

        List<Book> all = repository.findAll();

        assertEquals(items.size(), all.size());
    }

    @Test
    public void save(){
        Book book = Book.builder()
                .id(5L)
                .author("Karl Marx")
                .title("Das Kapital")
                .genre("Political")
                .price(35.0F)
                .quantity(10L)
                .build();

        repository.save(book);
        List<Book> all = repository.findAll();

        Assertions.assertNotNull(all);
    }

    @Test
    public void delete(){
        Book book = Book.builder()
                .author("Karl Marx")
                .title("Das Kapital")
                .genre("Political")
                .price(35.0F)
                .quantity(10L)
                .build();

        repository.save(book);
        repository.delete(book);
        List<Book> all = repository.findAll();

        Assertions.assertEquals(all.size(), 0);
    }
}