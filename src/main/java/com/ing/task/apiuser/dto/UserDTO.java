package com.ing.task.apiuser.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

    private Long id;
    private String title;
    private String firstname;
    private String lastname;
    private String gender;
    private AddressDTO address;
}
