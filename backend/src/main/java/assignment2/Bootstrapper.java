package assignment2;

import assignment2.book.model.Book;
import assignment2.user.repository.RoleRepository;
import assignment2.user.repository.UserRepository;
import assignment2.user.model.ERole;
import assignment2.user.model.Role;
import assignment2.book.repository.BookRepository;
import assignment2.security.AuthService;
import assignment2.security.dto.SignupRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@RequiredArgsConstructor
public class Bootstrapper implements ApplicationListener<ApplicationReadyEvent> {

    private final RoleRepository roleRepository;

    private final UserRepository userRepository;

    private final AuthService authService;

    private final BookRepository bookRepository;

    @Value("${app.bootstrap}")
    private Boolean bootstrap;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        if (bootstrap) {
            bookRepository.deleteAll();
            userRepository.deleteAll();
            roleRepository.deleteAll();

            for (ERole value : ERole.values()) {
                roleRepository.save(
                        Role.builder()
                                .name(value)
                                .build()
                );
            }

            authService.register(SignupRequest.builder()
                    .email("boss@email.com")
                    .username("Boss")
                    .password("MareBoss777.")
                    .roles(Set.of("ADMIN"))
                    .build());

            authService.register(SignupRequest.builder()
                    .email("dorel@email.com")
                    .username("Dorel")
                    .password("MareDorel777.")
                    .roles(Set.of("CUSTOMER"))
                    .build());

            bookRepository.save(Book.builder()
                    .author("Fyodor Dostoevsky")
                    .title("The Idiot")
                    .quantity(11L)
                    .genre("Philosophical novel")
                    .price(45.55F)
                    .build());

            bookRepository.save(Book.builder()
                    .author("Octavian Cret")
                    .title("Analysis and synthesis of numerical systems")
                    .quantity(20L)
                    .genre("Technological research")
                    .price(37.99F)
                    .build());

            bookRepository.save(Book.builder()
                    .author("Ion Creanga")
                    .title("The story of a lazy man")
                    .quantity(15L)
                    .genre("Fable")
                    .price(25.99F)
                    .build());
        }
    }
}
