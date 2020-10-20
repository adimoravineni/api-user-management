package com.ing.task.apiuser.util;

import com.github.fge.jsonpatch.JsonPatchException;
import com.ing.task.apiuser.dto.AddressDTO;
import com.ing.task.apiuser.dto.UserDTO;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static com.ing.task.apiuser.util.MockDataProvider.userPatch_sample_json;
import static org.assertj.core.api.Assertions.assertThat;

public class ApplicationUtilTests {

    @Test
    public void mergePatchJson() throws IOException, JsonPatchException {

        AddressDTO address = new AddressDTO("85 Bayview Close", "MACKENZIE RIVER", "Queensland", null);
        UserDTO source = new UserDTO(1L, "Mr", "Xavier", "Wild", null, address);

        UserDTO result = ApplicationUtil.mergePatchJson(source, userPatch_sample_json(), UserDTO.class);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getTitle()).isNull();
        assertThat(result.getFirstname()).isEqualTo("Xavier");
        assertThat(result.getLastname()).isEqualTo("Wild");
        assertThat(result.getGender()).isEqualTo("Male");
        assertThat(result.getAddress().getStreet()).isEqualTo("85 Bayview Close");
        assertThat(result.getAddress().getCity()).isEqualTo("MACKENZIE RIVER");
        assertThat(result.getAddress().getState()).isNull();
        assertThat(result.getAddress().getPostcode()).isEqualTo("1212");
    }
}
