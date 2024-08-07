package com.myapp.restapi.researchconference.Restservice.Interface;

import com.myapp.restapi.researchconference.DTO.ResetPasswordDTO;
import com.myapp.restapi.researchconference.DTO.UserDTO;
import com.myapp.restapi.researchconference.entity.Admin.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserRestService {
    List<UserDTO> findAll(int pageNumber, int myuserID);
    long getTotalUser(int isActive);


    List<UserDTO> findNonActiveAccount(int pageNumber);

    List<User> findUserByRole(String roleName);

    List<User> searchByUsername(String username);

    List<User> searchByUsernameAndRole(String username, String roleName);

    UserDTO findByID(int userID);

    User findByUserName(String username);

    User save(User user);

    User update(User user, int userID);

    void delete(int userID);

    void deactivation(int userID);

    void activation(int userID);

    User resetPassword(ResetPasswordDTO resetPasswordDTO);

}
