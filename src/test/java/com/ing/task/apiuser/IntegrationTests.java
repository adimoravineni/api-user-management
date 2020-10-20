package com.ing.task.apiuser;

import com.ing.task.apiuser.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static com.ing.task.apiuser.util.MockDataProvider.userPatch_sample_json;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestEntityManager
@Transactional
@ActiveProfiles({"test"})
public class IntegrationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void getUser_Success() throws Exception {

        User user = new User();
        user.setTitle("Mr");
        user.setFirstname("Jack");
        user.setLastname("Callaghan");
        user.setGender("Male");
        user.setStreet("11 Chatsworth Drive");
        user.setCity("SOUTHERN RIVER");
        user.setState("Western Australia");
        user.setPostcode("6110");

        user = entityManager.persist(user);

        mockMvc.perform(get("/api/user/management/v1/users/{Id}", user.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.title", is("Mr")))
                .andExpect(jsonPath("$.firstname", is("Jack")))
                .andExpect(jsonPath("$.lastname", is("Callaghan")))
                .andExpect(jsonPath("$.gender", is("Male")))
                .andExpect(jsonPath("$.address.street", is("11 Chatsworth Drive")))
                .andExpect(jsonPath("$.address.city", is("SOUTHERN RIVER")))
                .andExpect(jsonPath("$.address.state", is("Western Australia")))
                .andExpect(jsonPath("$.address.postcode", is("6110")));
    }

    @Test
    public void getUser_NotFound() throws Exception {
        mockMvc.perform(get("/api/user/management/v1/users/1"))
                .andExpect(status().isNotFound())
                .andExpect(status().reason("User is not found"));
    }

    @Test
    public void patchUser_Success() throws Exception {

        User user = new User();
        user.setTitle("Mr");
        user.setFirstname("Jack");
        user.setLastname("Callaghan");
        user.setGender("Male");
        user.setStreet("11 Chatsworth Drive");
        user.setCity("SOUTHERN RIVER");
        user.setState("Western Australia");
        user.setPostcode("6110");

        user = entityManager.persist(user);

        mockMvc.perform(patch("/api/user/management/v1/users/{Id}", user.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(userPatch_sample_json()))
                .andExpect(status().isOk());

        User updatedUser = entityManager.find(User.class, user.getId());
        assertThat(updatedUser).isNotNull();
        assertThat(updatedUser.getTitle()).isNull();
        assertThat(updatedUser.getFirstname()).isEqualTo("Jack");
        assertThat(updatedUser.getState()).isNull();
        assertThat(updatedUser.getPostcode()).isEqualTo("1212");
    }

    @Test
    public void patchUser_NotFound() throws Exception {
        mockMvc.perform(patch("/api/user/management/v1/users/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(userPatch_sample_json()))
                .andExpect(status().isNotFound())
                .andExpect(status().reason("User is not found"));
    }
}
