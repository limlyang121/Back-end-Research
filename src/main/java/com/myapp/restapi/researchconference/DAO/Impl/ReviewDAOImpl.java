package com.myapp.restapi.researchconference.DAO.Impl;

import com.myapp.restapi.researchconference.DAO.Interface.ReviewDAO;
import com.myapp.restapi.researchconference.entity.Review.Review;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class ReviewDAOImpl implements ReviewDAO {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public long findMyTotalReviews(int reviewerID) {
        Session session = entityManager.unwrap(Session.class);
        Query<Long> query = session.createQuery("select count(reviewID) from Review r where bid.reviewer.reviewerID = :reviewerID", Long.class);
        query.setParameter("reviewerID", reviewerID);
        return query.getSingleResult();

    }

    @Override
    public List<Review> findMyReviews(int reviewerID, int pageNumber) {
        Session session = entityManager.unwrap(Session.class);

        Query<Review> reviewQuery = session.createQuery("From Review where bid.reviewer .reviewerID = :reviewerID", Review.class);
        reviewQuery.setParameter("reviewerID", reviewerID);
        reviewQuery.setFirstResult((pageNumber - 1) * 5);
        reviewQuery.setMaxResults(5);

        return reviewQuery.getResultList();
    }

    @Override
    public Optional<Review> findReviewByID(int reviewID) {
        Session session = entityManager.unwrap(Session.class);

        Query<Review> reviewQuery = session.createQuery("From Review where reviewID = :reviewID", Review.class);

        reviewQuery.setParameter("reviewID", reviewID);
        Optional<Review> review = reviewQuery.uniqueResultOptional();
        return reviewQuery.uniqueResultOptional();
    }

    @Override
    public List<Review> findReviewsByPaperID(int paperID) {
        Session session = entityManager.unwrap(Session.class);

        Query<Review> queryReview = session.createQuery("From Review where bid.paper.paperID = :paperID", Review.class);
        queryReview.setParameter("paperID", paperID);
        return queryReview.getResultList();
    }

    @Override
    public List<Review> findCompletedReviewsByPaperID(int paperID) {
        Session session = entityManager.unwrap(Session.class);

        Query<Review> queryReview = session.createQuery("From Review where bid.paper.paperID = :paperID and bid.status != 'Reject' ", Review.class);
        queryReview.setParameter("paperID", paperID);
        return queryReview.getResultList();
    }

    @Override
    public List<Review> findReviewedPaper() {
        Session session = entityManager.unwrap(Session.class);

        Query<Review> query = session.createQuery("select r from Review r " +
                "group by r.bid.paper.paperID having COUNT (r.reviewID) >= 2 ", Review.class);

        return query.getResultList();
    }

    @Override
    public Review addReview(Review review) {
        Session session = entityManager.unwrap(Session.class);
        try{
            session.persist(review);
            return review;
        }catch (Exception e ){
            return null;
        }
    }

    @Override
    public Review updateReview(Review review) {
        Session session = entityManager.unwrap(Session.class);

        try{
            return session.merge(review);

        }catch (Exception e ){
            return null;
        }
    }
}
