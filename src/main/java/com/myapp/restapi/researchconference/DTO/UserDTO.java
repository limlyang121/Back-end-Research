package com.myapp.restapi.researchconference.DTO;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.myapp.restapi.researchconference.entity.Admin.Role;
import com.myapp.restapi.researchconference.entity.Admin.User;
import com.myapp.restapi.researchconference.entity.Admin.Userdetails;
import com.myapp.restapi.researchconference.entity.Review.Reviewer;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonRootName("user")
public class UserDTO {
    private int id;
    private String userName;

    private Role role;
    private int active;

    private Userdetails userdetails;

    public static List<UserDTO> convertToDTO(List<User> users){
        List<UserDTO> userDTOList = new ArrayList<>();
        for (User user : users)
            userDTOList.add(convertToDTOSingle(user));

        return userDTOList;
    }

    public static UserDTO convertToDTOSingle(User user){
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setUserName(user.getUserName());
        userDTO.setRole(user.getRole());
        userDTO.setUserdetails(user.getUserdetails());
        userDTO.setActive(user.getActive());
        return userDTO;
    }


}
