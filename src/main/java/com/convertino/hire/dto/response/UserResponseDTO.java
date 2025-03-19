package com.convertino.hire.dto.response;

import com.convertino.hire.utils.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * Data Transfer Object for user response.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDTO {
    private long id;
    private String email;
    private String name;
    private String surname;
    private Role role;
}