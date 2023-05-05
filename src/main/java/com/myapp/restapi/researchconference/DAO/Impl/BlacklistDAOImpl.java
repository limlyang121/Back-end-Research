package com.myapp.restapi.researchconference.DAO.Impl;

import com.myapp.restapi.researchconference.DAO.Interface.BlacklistDAO;
import com.myapp.restapi.researchconference.entity.Paper.Paper;
import com.myapp.restapi.researchconference.entity.Review.BlacklistPaper;
import com.myapp.restapi.researchconference.entity.Review.Reviewer;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class BlacklistDAOImpl implements BlacklistDAO {
    private EntityManager entityManager;

    @Autowired
    public BlacklistDAOImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public boolean addBlackList(BlacklistPaper blacklistPaper) {
        Session session = entityManager.unwrap(Session.class);
        try{
            session.persist(blacklistPaper);
            return true;
        }catch (Exception e){
//            System.err.println(e);
            return false;
        }
    }

    @Override
    public boolean deleteBlackList(BlacklistPaper blacklistPaper) {
        Session session = entityManager.unwrap(Session.class);
        BlacklistPaper merged = session.merge(blacklistPaper);
        try{
            session.remove(merged);
            return true;
        }catch(Exception e){
            System.err.println(e);
            return false;
        }
    }

    @Override
    public boolean deleteBlackListAssociatedWithSpecifiedPaperID(int paperID) {
        try {
            Session session = entityManager.unwrap(Session.class);
            Query query = session.createQuery("delete  from BlacklistPaper where paper.id = :paperID");
            query.setParameter("paperID", paperID);
            query.executeUpdate();
            return true;
        }catch (Exception e ){
            return false;
        }
    }
}
