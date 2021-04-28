package assignment2.user;

import assignment2.TestCreationFactory;
import assignment2.user.dto.UserListDTO;
import assignment2.user.mapper.UserMapper;
import assignment2.user.model.User;
import assignment2.user.repository.UserRepository;
import assignment2.user.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;

public class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
        userService = new UserService(userRepository, userMapper);
    }

    @Test
    void findAll() {
        List<User> users = TestCreationFactory.listOf(User.class);
        when(userRepository.findAll()).thenReturn(users);

        List<UserListDTO> all = userService.findAll();

        Assertions.assertEquals(users.size(), all.size());
    }

    @Test
    void create(){
        UserListDTO userListDTO = UserListDTO.builder()
                .username("test")
                .email("test@email.com")
                .id(1L)
                .build();

        User user = User.builder()
                .username("test")
                .email("test@email.com")
                .id(1L)
                .build();

        when(userMapper.toDTO(user)).thenReturn(userListDTO);

        when(userMapper.fromDTO(userListDTO)).thenReturn(user);

        when(userRepository.save(user)).thenReturn(user);

        UserListDTO newUser = userService.create(userListDTO);
        Assertions.assertNotNull(newUser);
    }

    @Test
    void update(){
        UserListDTO userListDTO = UserListDTO.builder()
                .username("test")
                .email("test@email.com")
                .id(1L)
                .build();

        User user = User.builder()
                .username("test")
                .email("test@email.com")
                .id(1L)
                .build();

        when(userMapper.fromDTO(userListDTO)).thenReturn(user);
        when(userMapper.toDTO(user)).thenReturn(userListDTO);

        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        when(userRepository.save(user)).thenReturn(user);

        Assertions.assertEquals(userListDTO.getId(), userService.edit(userListDTO.getId(), userListDTO).getId());
    }

    @Test
    void delete(){
        User user = User.builder()
                .username("test")
                .email("test@email.com")
                .id(1L)
                .build();

        userService.delete(user.getId());
    }
}
