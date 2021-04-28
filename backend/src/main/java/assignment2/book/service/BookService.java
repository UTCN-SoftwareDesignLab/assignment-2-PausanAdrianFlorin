package assignment2.book.service;

import assignment2.book.mapper.BookMapper;
import assignment2.book.model.Book;
import assignment2.book.model.dto.BookDTO;
import assignment2.book.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookService {
    private final BookRepository bookRepository;
    private final BookMapper bookMapper;

    private Book findById(Long id){
        return bookRepository
                .findById(id)
                .orElseThrow(()-> new EntityNotFoundException("Book not found: " + id));
    }

    public List<BookDTO> findAll() {
        return bookRepository.findAll().stream()
                .map(bookMapper::toDTO)
                .collect(Collectors.toList());
    }

    public List<BookDTO> filter(String title, String author, String genre){
        return bookRepository.findByTitleContainingAndAuthorContainingAndGenreContainingIgnoreCase(title, author, genre)
                .stream()
                .map(bookMapper::toDTO)
                .collect(Collectors.toList());
    }

    public BookDTO create(BookDTO bookDTO) {
        return bookMapper.toDTO(bookRepository.save(
                bookMapper.fromDTO(bookDTO)
        ));
    }

    public BookDTO edit(Long id, BookDTO bookDTO) {
        Book actBook = findById(id);

        actBook.setId(bookDTO.getId());
        actBook.setTitle(bookDTO.getTitle());
        actBook.setAuthor(bookDTO.getAuthor());
        actBook.setGenre(bookDTO.getGenre());
        actBook.setQuantity(bookDTO.getQuantity());
        actBook.setPrice(bookDTO.getPrice());

        return bookMapper.toDTO(
                bookRepository.save(actBook)
        );
    }

    public void delete(Long id){
        bookRepository.deleteById(id);
    }

    public boolean sell(Long id, int quantity){
        Book actBook = findById(id);

        if (actBook.getQuantity() - quantity >= 0){
            actBook.setQuantity(actBook.getQuantity() - quantity);

            bookRepository.save(actBook);

            return true;
        }
        return false;
    }
}
