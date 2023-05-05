package com.myapp.restapi.researchconference.Restservice.Interface;

import com.myapp.restapi.researchconference.entity.Admin.Role;

import java.util.List;
import java.util.Optional;

public interface RoleRestService {

    List<Role> findAll();

    Optional<Role> findRoleByName(String role);

    Role add(Role role);

    Role update(Role role, String roleName);

    boolean delete(String roleName);


}
