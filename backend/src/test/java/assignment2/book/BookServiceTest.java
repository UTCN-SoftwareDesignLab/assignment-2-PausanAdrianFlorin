package assignment2.book;

import assignment2.TestCreationFactory;
import assignment2.book.mapper.BookMapper;
import assignment2.book.model.Book;
import assignment2.book.model.dto.BookDTO;
import assignment2.book.repository.BookRepository;
import assignment2.book.service.BookService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;

public class BookServiceTest {

    @InjectMocks
    private BookService bookService;

    @Mock
    private BookRepository bookRepository;

    @Mock
    private BookMapper bookMapper;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
        bookService = new BookService(bookRepository, bookMapper);
    }

    @Test
    void findAll(){
        List<Book> books = TestCreationFactory.listOf(Book.class);

        when(bookRepository.findAll()).thenReturn(books);

        List<BookDTO> all = bookService.findAll();

        Assertions.assertEquals(books.size(), all.size());
    }

    @Test
    void create(){
        BookDTO bookDTO = BookDTO.builder()
                .id(5L)
                .author("Karl Marx")
                .title("Das Kapital")
                .genre("Political")
                .price(35.0F)
                .quantity(10L)
                .build();

        Book book = Book.builder()
                .id(5L)
                .author("Karl Marx")
                .title("Das Kapital")
                .genre("Political")
                .price(35.0F)
                .quantity(10L)
                .build();

        when(bookMapper.toDTO(book)).thenReturn(bookDTO);
        when(bookMapper.fromDTO(bookDTO)).thenReturn(book);
        when(bookRepository.save(book)).thenReturn(book);

        BookDTO newBook = bookService.create(bookDTO);
        Assertions.assertNotNull(newBook);
    }

    @Test
    void update(){
        BookDTO bookDTO = BookDTO.builder()
                .id(6L)
                .author("Karl Marx")
                .title("Das Kapital")
                .genre("Political")
                .price(35.0F)
                .quantity(10L)
                .build();

        Book book = Book.builder()
                .id(6L)
                .author("Karl Marx")
                .title("Das Kapital")
                .genre("Political")
                .price(35.0F)
                .quantity(10L)
                .build();

        when(bookMapper.fromDTO(bookDTO)).thenReturn(book);
        when(bookMapper.toDTO(book)).thenReturn(bookDTO);
        when(bookRepository.findById(book.getId())).thenReturn(Optional.of(book));
        when(bookRepository.save(book)).thenReturn(book);

        Assertions.assertEquals(bookDTO.getId(), bookService.edit(bookDTO.getId(), bookDTO).getId());
    }

    @Test
    void delete(){
        Book book = Book.builder()
                .id(6L)
                .author("Karl Marx")
                .title("Das Kapital")
                .genre("Political")
                .price(35.0F)
                .quantity(10L)
                .build();

        bookService.delete(book.getId());
    }

    @Test
    void sell(){
        BookDTO bookDTO = BookDTO.builder()
                .id(6L)
                .author("Karl Marx")
                .title("Das Kapital")
                .genre("Political")
                .price(35.0F)
                .quantity(10L)
                .build();

        Book book = Book.builder()
                .id(6L)
                .author("Karl Marx")
                .title("Das Kapital")
                .genre("Political")
                .price(35.0F)
                .quantity(10L)
                .build();

        when(bookMapper.fromDTO(bookDTO)).thenReturn(book);
        when(bookMapper.toDTO(book)).thenReturn(bookDTO);
        when(bookRepository.findById(book.getId())).thenReturn(Optional.of(book));
        when(bookRepository.save(book)).thenReturn(book);

        Assertions.assertTrue(bookService.sell(bookDTO.getId(), 1));
    }
}