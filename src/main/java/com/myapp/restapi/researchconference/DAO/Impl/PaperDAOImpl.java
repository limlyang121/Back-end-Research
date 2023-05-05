package com.myapp.restapi.researchconference.DAO.Impl;

import com.myapp.restapi.researchconference.DAO.Interface.PaperDAO;
import com.myapp.restapi.researchconference.entity.Paper.Paper;
import jakarta.persistence.EntityManager;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class PaperDAOImpl implements PaperDAO {
    private final EntityManager entityManager;

    @Autowired
    public PaperDAOImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<Paper> findAll() {
        Session session = entityManager.unwrap(Session.class);

        Query<Paper> paperQuery = session.createQuery("From Paper", Paper.class);
        return paperQuery.getResultList();
    }

    @Override
    public List<Paper> findBidPapers(int reviewerID) {
        Session session = entityManager.unwrap(Session.class);
        Query<Paper> query = session.createQuery("SELECT p FROM Paper p WHERE p.status = :status AND p.paperID " +
                "NOT IN (SELECT bp.paper.paperID FROM BlacklistPaper bp WHERE bp.reviewer.reviewerID = :reviewerID)" +
                "AND p.paperID NOT IN (SELECT b.paper.paperID FROM Bid b WHERE b.reviewer.reviewerID = :reviewerID) ", Paper.class);

        query.setParameter("status", "Pending");
        query.setParameter("reviewerID", reviewerID);

        return query.getResultList();
    }

    @Override
    public List<Paper> findBanPapers(int reviewerID){
        Session session = entityManager.unwrap(Session.class);
        Query<Paper> query = session.createQuery("SELECT p FROM Paper p WHERE p.status = :status AND p.paperID " +
                "IN (SELECT bp.paper.paperID FROM BlacklistPaper bp WHERE bp.reviewer.reviewerID = :reviewerID)" +
                "AND p.paperID NOT IN (SELECT b.paper.paperID FROM Bid b WHERE b.reviewer.reviewerID = :reviewerID) ", Paper.class);

        query.setParameter("status", "Pending");
        query.setParameter("reviewerID", reviewerID);

        return query.getResultList();
    }

    @Override
    public List<Paper> findMyPaper(int userID) {
        Session session = entityManager.unwrap(Session.class);
        try{
            Query<Paper> paperQuery = session.createQuery("From Paper p inner join PaperInfo pi on p.paperInfo.id = pi.paperID" +
                    " where pi.authorID.id = :userID and (p.status = 'Pending' or p.status = 'Ready')", Paper.class);
            paperQuery.setParameter("userID", userID);
            return paperQuery.getResultList();
        }catch (Exception e){
            return null;
        }
    }

    @Override
    public List<Paper> findAllMyPublishedPapers(int userID) {
        Session session = entityManager.unwrap(Session.class);
        try{
            Query<Paper> paperQuery = session.createQuery("From Paper p inner join PaperInfo pi on p.paperInfo.id = pi.paperID" +
                    " where pi.authorID.id = :userID and (p.status = 'Accept' or p.status = 'Reject')", Paper.class);
            paperQuery.setParameter("userID", userID);
            return paperQuery.getResultList();
        }catch (Exception e){
            return null;
        }
    }

    @Override
    public List<Paper> findMyPublishedPapersByStatus(String status, int userID) {
        Session session = entityManager.unwrap(Session.class);
        try{
            Query<Paper> paperQuery = session.createQuery("From Paper p inner join PaperInfo pi on p.paperInfo.id = pi.paperID" +
                    " where pi.authorID.id = :userID and p.status = :status ", Paper.class);
            paperQuery.setParameter("userID", userID);
            paperQuery.setParameter("status", status);

            return paperQuery.getResultList();
        }catch (Exception e){
            return null;
        }
    }

    @Override
    public Optional<Paper> findPaperByID(int paperID) {
        Session session = entityManager.unwrap(Session.class);

        Query<Paper> query = session.createQuery("From Paper " +
                "where  paperID = :paperID", Paper.class);
        query.setParameter("paperID", paperID);


        return query.uniqueResultOptional();
    }

    @Override
    public List<Paper> findPapersThatReviewed() {
        Session session = entityManager.unwrap(Session.class);

        Query<Paper> paperQuery = session.createQuery("from Paper where reviewedTimes >= 5 and status='Pending'", Paper.class);
        return paperQuery.getResultList();
    }

    @Override
    public List<Paper> findReadyPapers() {
        Session session = entityManager.unwrap(Session.class);

        Query<Paper> paperQuery = session.createQuery("from Paper where status = 'Ready'", Paper.class);
        return paperQuery.getResultList();
    }

    @Override
    public List<Paper> findCompletedPapers() {
        Session session = entityManager.unwrap(Session.class);

        Query<Paper> paperQuery = session.createQuery("from Paper where status = 'Accept' or status = 'Reject'", Paper.class);
        return paperQuery.getResultList();
    }



    @Override
    public Paper add(Paper paper) {
        Session session = entityManager.unwrap(Session.class);

        try{
            if (paper.getPaperID() == 0){
                session.persist(paper);
            }else{
                session.merge(paper);

            }

            return paper;
        }catch (Exception e){
            return null;
        }
    }

    @Override
    public void increaseReviewTimes(int paperID) {
        Session session = entityManager.unwrap(Session.class);

        Paper paper = session.get(Paper.class, paperID);
        paper.setReviewedTimes(paper.getReviewedTimes() + 1);
        session.merge(paper);
    }

    @Override
    public boolean deletePaper(int paperID) {
        Session session = entityManager.unwrap(Session.class);
        Paper paper = session.get(Paper.class, paperID);
        if (paper != null){
            session.remove(paper);
            return true;
        }
        return false;
    }

    @Override
    public Paper updatePaper(Paper paper){
        Session session = entityManager.unwrap(Session.class);
        return session.merge(paper);
    }

    @Override
    public boolean readyPaper(int paperID) {
        Session session = entityManager.unwrap(Session.class);
        Paper paper = session.get(Paper.class, paperID);
        if (paper == null)
            return false;

        paper.setStatus("Ready");
        session.merge(paper);
        return true;
    }

    @Override
    public boolean acceptPaper(int paperID) {
        Session session = entityManager.unwrap(Session.class);
        Paper paper = session.get(Paper.class, paperID);
        if (paper == null)
            return false;

        paper.setStatus("Accept");
        session.merge(paper);
        return true;
    }

    @Override
    public boolean rejectPaper(int paperID) {
        Session session = entityManager.unwrap(Session.class);
        Paper paper = session.get(Paper.class, paperID);
        if (paper == null)
            return false;

        paper.setStatus("Reject");
        session.merge(paper);
        return true;
    }

}
