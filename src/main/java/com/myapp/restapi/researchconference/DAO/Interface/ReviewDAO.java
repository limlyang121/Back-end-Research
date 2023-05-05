package com.myapp.restapi.researchconference.DAO.Interface;

import com.myapp.restapi.researchconference.entity.Review.Review;

import java.util.List;
import java.util.Optional;

public interface ReviewDAO {
    List<Review> findMyReviews(int reviewerID);

    Optional<Review> findReviewByID(int reviewID);

    List<Review> findReviewsByPaperID(int paperID);

    List<Review> findCompletedReviewsByPaperID(int paperID);

    List<Review> findReviewedPaper();

    Review addReview(Review review);

    Review updateReview(Review review);

}
