package com.ing.task.apiuser.pact;

import au.com.dius.pact.provider.junit5.HttpTestTarget;
import au.com.dius.pact.provider.junit5.PactVerificationContext;
import au.com.dius.pact.provider.junit5.PactVerificationInvocationContextProvider;
import au.com.dius.pact.provider.junitsupport.Provider;
import au.com.dius.pact.provider.junitsupport.State;
import au.com.dius.pact.provider.junitsupport.loader.PactFolder;
import com.ing.task.apiuser.entity.User;
import com.ing.task.apiuser.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestTemplate;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Provider("provider")
@PactFolder("src/test/resources/pacts")
@ActiveProfiles({"test"})
public class UserPactVerificationTests {

    @LocalServerPort
    private int port;

    @MockBean
    private UserRepository userRepository;

    @BeforeEach
    void before(PactVerificationContext context) {
        context.setTarget(new HttpTestTarget("localhost", port, "/"));
    }

    @TestTemplate
    @ExtendWith(PactVerificationInvocationContextProvider.class)
    public void pactVerificationTestTemplate(PactVerificationContext context) {

        context.verifyInteraction();
    }

    @State("user found")
    public void userFound() {
        User user = new User(10001L, "Mr", "Jack", "Callaghan", "Male", "11 Chatsworth Drive", "SOUTHERN RIVER", "Western Australia", "2145");
        given(userRepository.findById(anyLong())).willReturn(Optional.of(user));
    }

    @State("user not found")
    public void userNotFound() {
        given(userRepository.findById(anyLong())).willReturn(Optional.empty());
    }

}
