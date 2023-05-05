package com.myapp.restapi.researchconference.DAO.Impl;

import com.myapp.restapi.researchconference.DAO.Interface.BidDAO;
import com.myapp.restapi.researchconference.entity.Bid.Bid;
import jakarta.persistence.EntityManager;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class BidDAOImpl implements BidDAO {
    private final EntityManager entityManager;

    @Autowired
    public BidDAOImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<Bid> findAllBidsByStatus(String Status) {
        Session session = entityManager.unwrap(Session.class);

        Query<Bid> query = session.createQuery("From Bid where status = :status", Bid.class);
        query.setParameter("status", Status);
        return query.getResultList();
    }

    @Override
    public List<Bid> findMyBidByStatus(int reviewerID, String status) {
        Session session = entityManager.unwrap(Session.class);

        Query<Bid> query = session.createQuery("From Bid where reviewer.reviewerID = :reviewerID " +
                "and status = :status", Bid.class);

        query.setParameter("reviewerID", reviewerID);
        query.setParameter("status", status);

        return query.getResultList();
    }

    @Override
    public List<Bid> findMyAcceptedBid(int reviewerID) {
        Session session = entityManager.unwrap(Session.class);

        Query<Bid> query = session.createQuery("From Bid where reviewer.reviewerID = :reviewerID and status = 'Accept'", Bid.class);
        query.setParameter("reviewerID", reviewerID);
        return query.getResultList();
    }


    @Override
    public Optional<Bid> findBidByID(int bidID) {
        Session session = entityManager.unwrap(Session.class);

        Query<Bid> query = session.createQuery("From Bid where bidID = : bidID", Bid.class);
        query.setParameter("bidID", bidID);
        return query.uniqueResultOptional();
    }

    @Override
    public List<Bid> findMyCompletedBid(int reviewerID) {
        Session session = entityManager.unwrap(Session.class);

        Query<Bid> query = session.createQuery("From Bid where reviewer.reviewerID = :reviewerID and status = 'Complete'", Bid.class);
        query.setParameter("reviewerID", reviewerID);
        return query.getResultList();
    }

    @Override
    public Optional<Bid> findBidByForeignKey(int reviewerID, int paperID){
        Session session = entityManager.unwrap(Session.class);

        Query<Bid> bid = session.createQuery("From Bid  where reviewer.reviewerID = :reviewerID " +
                "and paper.paperID =: paperID", Bid.class);

        bid.setParameter("reviewerID", reviewerID);
        bid.setParameter("paperID", paperID);
        return bid.uniqueResultOptional();
    }

    @Override
    public List<Bid> findReadyPapersBid() {
        Session session = entityManager.unwrap(Session.class);
        Query<Bid> bidQuery = session.createQuery("From Bid where paper.status = 'Ready'");
        return bidQuery.getResultList();
    }

    @Override
    public List<Bid> findAllBidForSpecifiedPapers(int paperID) {
        Session session = entityManager.unwrap(Session.class);
        Query<Bid> bidQuery = session.createQuery("From Bid where paper.id = :paperID " +
                "and status != 'Reject'", Bid.class);
        bidQuery.setParameter("paperID", paperID);
        return bidQuery.getResultList();
    }

    @Override
    public boolean hideBid(int bidID) {
        return false;
    }

    @Override
    public Bid addBid (Bid bid) {
        Session session = entityManager.unwrap(Session.class);
        try{
            session.merge(bid);
            return bid;
        }catch(Exception e){
            return null;
        }
    }

    @Override
    public boolean deleteBid(int bidID) {
        Session session = entityManager.unwrap(Session.class);
        try{
            Bid bid = session.get(Bid.class, bidID);
            session.remove(bid);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    @Override
    public boolean deleteBidAssociatedWithThisPaperID(int paperID) {
        Session session = entityManager.unwrap(Session.class);
        try{
            Query query = session.createQuery("Delete from Bid where paper.id = :paperID");
            query.setParameter("paperID", paperID);
            query.executeUpdate();
            return true;
        }catch (Exception exception){
            return false;
        }
    }

    @Override
    public boolean deleteRejectedBidOncePaperPublish(int paperID) {
        Session session = entityManager.unwrap(Session.class);
        try{
            Query query = session.createQuery("Delete from Bid where paper.id = :paperID and status = 'Reject'");
            query.setParameter("paperID", paperID);
            query.executeUpdate();
            return true;
        }catch (Exception exception){
            return false;
        }
    }

    @Override
    public boolean acceptBid(int bidID) {
        Session session = entityManager.unwrap(Session.class);
        try{
            Bid bid = session.get(Bid.class, bidID);
            if (bid == null)
                return false;

            bid.setStatus("Accept");
            session.merge(bid);

            return true;
        }catch (Exception e){
            return false;
        }
    }

    @Override
    public boolean rejectBid(int bidID) {
        Session session = entityManager.unwrap(Session.class);
        try{
            Bid bid = session.get(Bid.class, bidID);
            if (bid == null)
                return false;

            bid.setStatus("Reject");
            session.merge(bid);

            return true;
        }catch (Exception e){
            return false;
        }
    }

    @Override
    public boolean completeBid(int bidID) {
        Session session = entityManager.unwrap(Session.class);
        try{
            Bid bid = session.get(Bid.class, bidID);
            if (bid == null)
                return false;

            bid.setStatus("Complete");
            session.merge(bid);

            return true;
        }catch (Exception e){
            return false;
        }
    }

    @Override
    public boolean cancelBid(int bidID) {
        Session session = entityManager.unwrap(Session.class);
        try{
            Bid bid = session.get(Bid.class, bidID);
            if (bid == null)
                return false;

            bid.setStatus("Pending");
            session.merge(bid);

            return true;
        }catch (Exception e){
            return false;
        }
    }
}
