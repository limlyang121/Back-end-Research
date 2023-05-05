package com.myapp.restapi.researchconference.DAO.Interface;

import com.myapp.restapi.researchconference.entity.Admin.Role;

import java.util.List;
import java.util.Optional;

public interface RoleDAO {

    List<Role> findAll();
    Optional<Role> findRoleByName(String role);

    Role add(Role role);

    Role update(Role role);

    boolean delete(String roleName);

}
