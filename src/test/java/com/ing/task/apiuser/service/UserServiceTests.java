package com.ing.task.apiuser.service;

import com.ing.task.apiuser.dto.UserDTO;
import com.ing.task.apiuser.entity.User;
import com.ing.task.apiuser.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

import static com.ing.task.apiuser.util.MockDataProvider.userPatch_sample_json;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowableOfType;
import static org.mockito.BDDMockito.given;

@SpringBootTest
@ActiveProfiles({"test"})
public class UserServiceTests {

    @Autowired
    private UserService userService;

    @MockBean
    private UserRepository userRepository;

    @Test
    public void getUser_Success(){

        Long id = 1L;
        User user = new User(id, "Mr", "Jack", "Callaghan", null, "11 Chatsworth Drive", "SOUTHERN RIVER", "Western Australia", null);

        given(userRepository.findById(id)).willReturn(Optional.of(user));

        UserDTO userDTO = userService.getUser(id);

        assertThat(userDTO).isNotNull();
        assertThat(userDTO.getTitle()).isEqualTo("Mr");
        assertThat(userDTO.getFirstname()).isEqualTo("Jack");
        assertThat(userDTO.getLastname()).isEqualTo("Callaghan");
        assertThat(userDTO.getGender()).isNull();
        assertThat(userDTO.getAddress().getStreet()).isEqualTo("11 Chatsworth Drive");
        assertThat(userDTO.getAddress().getCity()).isEqualTo("SOUTHERN RIVER");
        assertThat(userDTO.getAddress().getState()).isEqualTo("Western Australia");
        assertThat(userDTO.getAddress().getPostcode()).isNull();
    }

    @Test
    public void getUser_NotFound(){

        Long id = 1L;
        User user = new User(id, "Mr", "Jack", "Callaghan", null, "11 Chatsworth Drive", "SOUTHERN RIVER", "Western Australia", null);

        given(userRepository.findById(1L)).willReturn(Optional.empty());

        ResponseStatusException ex = catchThrowableOfType(() -> userService.getUser(id), ResponseStatusException.class);

        assertThat(ex).isNotNull();
        assertThat(ex.getStatus()).isEqualByComparingTo(HttpStatus.NOT_FOUND);
        assertThat(ex.getReason()).isEqualTo("User is not found");
    }

    @Test
    public void patchUser_NotFound(){

        Long id = 1L;

        given(userRepository.findById(id)).willReturn(Optional.empty());

        ResponseStatusException ex = catchThrowableOfType(() -> userService.patchUser(id, userPatch_sample_json()), ResponseStatusException.class);

        assertThat(ex).isNotNull();
        assertThat(ex.getStatus()).isEqualByComparingTo(HttpStatus.NOT_FOUND);
        assertThat(ex.getReason()).isEqualTo("User is not found");
    }
}
