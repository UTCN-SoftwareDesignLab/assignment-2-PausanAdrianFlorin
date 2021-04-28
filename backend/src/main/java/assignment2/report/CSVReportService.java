package assignment2.report;

import assignment2.book.model.Book;
import assignment2.book.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.stream.Collectors;

import static assignment2.report.ReportType.CSV;

@RequiredArgsConstructor
@Service
public class CSVReportService implements ReportService {

    private final BookRepository bookRepository;

    public void csvGenerate() throws IOException{
        List<Book> books = bookRepository
                .findAll()
                .stream()
                .filter(book -> book.getQuantity() == 0)
                .collect(Collectors.toList());

        try(PrintWriter writer = new PrintWriter(new File("Book report.csv"))) {

            for (Book book : books) {
                String stringBuilder = book.getId().toString() +
                        book.getTitle() +
                        book.getAuthor() +
                        book.getPrice().toString() +
                        book.getQuantity() +
                        "\n";

                writer.write(stringBuilder);
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public String export() {
        return "I am a CSV reporter.";
    }

    @Override
    public ReportType getType() {
        return CSV;
    }
}
