package com.vector.vectorservice.dto;

import com.vector.vectorservice.entity.enums.UserType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserRequestDto {

    private String name;
    private String surname;
    private String email;
    private String password;
    private String phone;
    private UserType userType;
    private boolean active;
    private String token;
    private String picName;
}
