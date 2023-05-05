package com.myapp.restapi.researchconference.DAO.Impl;

import com.myapp.restapi.researchconference.DAO.Interface.RoleDAO;
import com.myapp.restapi.researchconference.entity.Admin.Role;
import jakarta.persistence.EntityManager;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class RoleDAOImpl implements RoleDAO {

    private EntityManager entityManager;

    @Autowired
    public RoleDAOImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }


    @Override
    public List<Role> findAll() {
        Session session = entityManager.unwrap(Session.class);

        Query<Role> query = session.createQuery("From Role order by role", Role.class);
        return query.getResultList();
    }

    @Override
    public Optional<Role> findRoleByName(String role) {
        Session session = entityManager.unwrap(Session.class);

        Query<Role> roleQuery = session.createQuery("From Role where role = :roleName");
        roleQuery.setParameter("roleName", role);

        return roleQuery.uniqueResultOptional();
    }

    @Override
    public Role add(Role role) {
        Session session = entityManager.unwrap(Session.class);
        try{
             session.persist(role);
             return role;
        }catch (Exception e){
            return null;
        }
    }

    @Override
    public Role update(Role role) {
        Session session = entityManager.unwrap(Session.class);
        try{
            return session.merge(role);
        }catch (Exception e){
            return null;
        }
    }

    @Override
    public boolean delete(String roleName) {
        Session session = entityManager.unwrap(Session.class);

        Optional<Role> roleOptional = findRoleByName(roleName);
        if (roleOptional.isPresent()){
            session.remove(roleOptional.get());
            return true;
        }
        else
            return false;
    }
}
