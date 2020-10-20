package com.ing.task.apiuser.controller;

import com.ing.task.apiuser.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static com.ing.task.apiuser.util.MockDataProvider.userPatch_malformed_json;
import static com.ing.task.apiuser.util.MockDataProvider.userPatch_sample_json;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
@ActiveProfiles({"test"})
public class UserControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Test
    public void getUser_userIdNotNumeric() throws Exception {

        mockMvc.perform(get("/api/user/management/v1/users/1000A"))
                .andExpect(status().isBadRequest())
                .andExpect(status().reason("User ID must be numeric"));
    }

    @Test
    public void patchUser_userIdNotNumeric() throws Exception {

        mockMvc.perform(patch("/api/user/management/v1/users/1000A").contentType(MediaType.APPLICATION_JSON)
                .content(userPatch_sample_json()))
                .andExpect(status().isBadRequest())
                .andExpect(status().reason("User ID must be numeric"));
    }

    @Test
    public void patchUser_malformedRequestBody() throws Exception {

        mockMvc.perform(patch("/api/user/management/v1/users/10001").contentType(MediaType.APPLICATION_JSON)
                .content(userPatch_malformed_json()))
                .andExpect(status().isBadRequest())
                .andExpect(status().reason("Please provide well formed JSON as request body"));
    }
}
