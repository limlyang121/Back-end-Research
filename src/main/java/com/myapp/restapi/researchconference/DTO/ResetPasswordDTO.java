package com.myapp.restapi.researchconference.DTO;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ResetPasswordDTO {
    private int userID;
    private String password;
}
