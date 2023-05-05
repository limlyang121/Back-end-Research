package com.myapp.restapi.researchconference.Restservice.Interface;

import com.myapp.restapi.researchconference.DTO.ReviewDTO;
import com.myapp.restapi.researchconference.Exception.IllegalAccessException;
import com.myapp.restapi.researchconference.entity.Review.Review;

import java.util.List;
import java.util.Optional;

public interface ReviewRestService {
    List<ReviewDTO> findMyReviews(int reviewerID);
    ReviewDTO findReviewByID(int reviewID, int reviewerID) throws IllegalAccessException;
    List<ReviewDTO> findReviewedPaper();

    List<ReviewDTO> findReviewsByPaperID(int paperID, int authorID) throws IllegalAccessException;


    List<ReviewDTO> findCompletedReviewsByPaperID(int paperID);


    Review addReview(Review review);
    Review updateReview(Review review);


}
