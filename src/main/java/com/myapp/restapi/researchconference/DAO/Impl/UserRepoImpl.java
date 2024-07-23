package com.myapp.restapi.researchconference.DAO.Impl;

import com.myapp.restapi.researchconference.DAO.Interface.UserRepo;
import com.myapp.restapi.researchconference.entity.Admin.Role;
import com.myapp.restapi.researchconference.entity.Admin.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class UserRepoImpl implements UserRepo {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public User findByUserName(String userName) {
        try {
            Session session = entityManager.unwrap(Session.class);
            Query<User> query = session.createQuery("from User where userName = :username", User.class);
            query.setParameter("username", userName);
            Optional<User> result = query.uniqueResultOptional();
            if (result.isPresent()) {
                return result.get();
            } else
                return null;
        }catch (Exception e ){
            System.err.println(e);
            return null;
        }
    }
    @Override
    public List<User> findAll(int pageNumber, int myUserID){
        Session session = entityManager.unwrap(Session.class);
        Query<User> query = session.createQuery("From User where active = 1 and id != :myUserID order by id asc ", User.class);
        query.setParameter("myUserID", myUserID);
        query.setFirstResult((pageNumber - 1) * 5);
        query.setMaxResults(5);
        return query.getResultList();
    }

    @Override
    public long getTotalUser(int isActive) {
        Session session = entityManager.unwrap(Session.class);
        Query<Long> query = session.createQuery("Select count(id) from User where active = :isActive ", Long.class);
        query.setParameter("isActive", isActive);
        return query.getSingleResult();
    }


    @Override
    public List<User> findNonActiveAccount(int pageNumber) {
        Session session = entityManager.unwrap(Session.class);
        Query<User> query = session.createQuery("From User where active = 0", User.class);
        query.setFirstResult((pageNumber - 1) * 5);
        query.setMaxResults(5);

        return query.getResultList();
    }

    @Override
    public List<User> findUserByRole(String roleName) {
        Session session = entityManager.unwrap(Session.class);

        Query<User> query = session.createQuery("from User where role = :roleName", User.class);
        query.setParameter("roleName", findRoleByName(roleName));

        List<User> userList = query.getResultList();

        return query.getResultList();
    }

    @Override
    public Role findRoleByName(String roleName) {
        Session session = entityManager.unwrap(Session.class);
        return session.get(Role.class, roleName);
    }

    @Override
    public User findByID(int userID) {
        Session session = entityManager.unwrap(Session.class);

        return session.get(User.class, userID);
    }
    @Override
    public User save(User user) {
        Session session = entityManager.unwrap(Session.class);
        try{
            if (user.getId() == 0){
                session.persist(user);
            }else{
                session.merge(user);
            }

            return user;

        }catch (NoResultException e){
            return null;
        }
    }

    @Override
    public void delete(int userID) {
        Session session = entityManager.unwrap(Session.class);
        User user = session.get(User.class, userID);
        if (user != null)
            session.remove(user);


    }

    @Override
    public List<User> searchByUsername(String username) {
        Session session = entityManager.unwrap(Session.class);


        Query<User> query = session.createQuery("From User where userName = :username", User.class);
        query.setParameter("username", username);
        List<User> users = query.getResultList();

        return users;

    }

    @Override
    public List<User> searchByUsernameAndRole(String username, String roleName) {
        Session session = entityManager.unwrap(Session.class);
        Query<User> query = session.createQuery("From User where userName = :username and  role = :roleName", User.class);
        query.setParameter("username", username);
        query.setParameter("roleName", findRoleByName(roleName));
        return query.getResultList();
    }

    @Override
    public void deactivation(int userID) {
        Session session = entityManager.unwrap(Session.class);

        User user = findByID(userID);
        user.setActive(0);

        session.merge(user);
    }

    @Override
    public void activation(int userID) {
        Session session = entityManager.unwrap(Session.class);

        User user = findByID(userID);
        user.setActive(1);

        session.merge(user);
    }

    @Override
    public User resetPassword(int userID, String password) {
        Session session = entityManager.unwrap(Session.class);
        User user = session.get(User.class, userID);
        if (user == null)
            return null;

        user.setPassword(password);
        return session.merge(user);
    }
}
