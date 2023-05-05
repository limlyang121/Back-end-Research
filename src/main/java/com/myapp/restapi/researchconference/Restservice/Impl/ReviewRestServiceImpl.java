package com.myapp.restapi.researchconference.Restservice.Impl;

import com.myapp.restapi.researchconference.DAO.Interface.BidDAO;
import com.myapp.restapi.researchconference.DAO.Interface.PaperDAO;
import com.myapp.restapi.researchconference.DAO.Interface.ReviewDAO;
import com.myapp.restapi.researchconference.DAO.Interface.ReviewerDAO;
import com.myapp.restapi.researchconference.DTO.ReviewDTO;
import com.myapp.restapi.researchconference.Exception.IllegalAccessException;
import com.myapp.restapi.researchconference.Exception.NoDataFoundException;
import com.myapp.restapi.researchconference.Restservice.Interface.ReviewRestService;
import com.myapp.restapi.researchconference.entity.Admin.Userdetails;
import com.myapp.restapi.researchconference.entity.Bid.Bid;
import com.myapp.restapi.researchconference.entity.Paper.Paper;
import com.myapp.restapi.researchconference.entity.Review.Review;
import com.myapp.restapi.researchconference.entity.Review.Reviewer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ReviewRestServiceImpl implements ReviewRestService {
    private final ReviewDAO reviewDAO;
    private final PaperDAO paperDAO;
    private final ReviewerDAO reviewerDAO;
    private final BidDAO bidDAO;

    public ReviewRestServiceImpl(ReviewDAO reviewDAO, PaperDAO paperDAO, ReviewerDAO reviewerDAO, BidDAO bidDAO) {
        this.reviewDAO = reviewDAO;
        this.paperDAO = paperDAO;
        this.reviewerDAO = reviewerDAO;
        this.bidDAO = bidDAO;
    }

    @Override
    @Transactional
    public List<ReviewDTO> findMyReviews(int reviewerID) {
        List<Review> reviewList = reviewDAO.findMyReviews(reviewerID);
        return ReviewDTO.DTOList(reviewList);
    }

    @Override
    @Transactional
    public ReviewDTO findReviewByID(int reviewID, int reviewerID) throws IllegalAccessException {
        Optional<Review> reviewOptional = reviewDAO.findReviewByID(reviewID);
        if (!reviewOptional.isPresent())
            throw new NoDataFoundException("No Data Found");

        Review review = reviewOptional.get();
        if (review.getBid().getReviewer().getReviewerID() != reviewerID){
            throw new IllegalAccessException("You can't access other people review");
        }

        return ReviewDTO.DTOSingle(review);
    }

    @Override
    public List<ReviewDTO> findReviewedPaper() {
        List<Review> reviewList = reviewDAO.findReviewedPaper();
        return ReviewDTO.DTOList(reviewList);
    }

    @Override
    @Transactional
    public List<ReviewDTO> findReviewsByPaperID(int paperID, int authorID) throws IllegalAccessException {
        Optional<Paper> paperOptional = paperDAO.findPaperByID(paperID);
        if (paperOptional.isPresent()){
            Paper paper = paperOptional.get();
            if (paper.getPaperInfo().getAuthorID().getId() != authorID){
                throw new IllegalAccessException("Not allow to See Reviews that belong to other User paper");
            }
            List<Review> reviewList = reviewDAO.findReviewsByPaperID(paperID);
            return ReviewDTO.DTOList(reviewList);
        }
        throw new NoDataFoundException("Paper not exist");
    }

    @Override
    @Transactional
    public List<ReviewDTO> findCompletedReviewsByPaperID(int paperID) {
        List<Review> reviewList = reviewDAO.findCompletedReviewsByPaperID(paperID);
        return ReviewDTO.DTOList(reviewList);
    }

    @Override
    @Transactional
    public Review addReview(Review review) {
        Date currentTime = new Date();
        review.setReviewDate(currentTime);
        Optional<Bid> bid = bidDAO.findBidByID(review.getBid().getBidID());
        boolean success = false;
        if (bid.isPresent()){
            review.setBid(bid.get());
            review =  reviewDAO.addReview(review);
            success = bidDAO.completeBid(review.getBid().getBidID());
            if (success){
                paperDAO.increaseReviewTimes(review.getBid().getPaper().getPaperID());
                return review;
            }
        }

        return null;
    }

    @Override
    @Transactional
    public Review updateReview(Review review) {
        Optional<Review> reviewDataOptional = reviewDAO.findReviewByID(review.getReviewID());
        if (reviewDataOptional.isPresent()){
            Review reviewData = reviewDataOptional.get();
            reviewData.setRate(review.getRate());
            reviewData.setComment(review.getComment());
            reviewData.setReviewDate(new Date());
            return  reviewDAO.updateReview(reviewData);
        }
        return null;
    }
}
