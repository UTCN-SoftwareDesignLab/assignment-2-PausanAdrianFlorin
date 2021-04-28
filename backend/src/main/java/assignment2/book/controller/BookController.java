package assignment2.book.controller;

import assignment2.UrlMapping;
import assignment2.book.model.dto.BookDTO;
import assignment2.book.service.BookService;
import assignment2.report.ReportServiceFactory;
import assignment2.report.ReportType;
import assignment2.security.dto.MessageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static assignment2.UrlMapping.*;

@RestController
@RequestMapping(UrlMapping.BOOK)
@RequiredArgsConstructor
public class BookController {
    private final BookService bookService;

    private final ReportServiceFactory reportServiceFactory;

    @GetMapping
    public List<BookDTO> allBooks() {
        return bookService.findAll();
    }

    @PostMapping
    public BookDTO create(@RequestBody BookDTO book){
        return bookService.create(book);
    }

    @PatchMapping(ENTITY)
    public BookDTO edit(@PathVariable Long id, @RequestBody BookDTO book){
        return bookService.edit(id, book);
    }

    @DeleteMapping(ENTITY)
    public void delete(@PathVariable Long id){
        bookService.delete(id);
    }

    @PostMapping(ENTITY + SELL)
    public ResponseEntity<?> sell(@PathVariable Long id, @RequestBody int quantity){
        if(bookService.sell(id, quantity)){
            return ResponseEntity.ok(new MessageResponse("One or more books were sold!"));
        }

        return ResponseEntity
                .badRequest()
                .body(new MessageResponse("Error: insufficient stock!"));
    }

    @GetMapping(FILTER)
    public List<BookDTO> filter(@RequestBody String title, @RequestBody String author, @RequestBody String genre){
        return bookService.filter(title, author, genre);
    }

    @GetMapping(UrlMapping.EXPORT_REPORT)
    public String exportReport(@PathVariable ReportType type){
        return reportServiceFactory.getReportService(type).export();
    }
}
