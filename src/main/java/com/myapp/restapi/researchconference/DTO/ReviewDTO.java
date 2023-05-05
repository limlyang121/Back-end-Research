package com.myapp.restapi.researchconference.DTO;

import com.myapp.restapi.researchconference.entity.Paper.Paper;
import com.myapp.restapi.researchconference.entity.Review.Review;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
public class ReviewDTO {
    private long reviewID;
    private BidDTO bid;
    private int rate;
    private String comment;
    private Date reviewDate;

    public ReviewDTO() {
        bid = new BidDTO();
    }

    public static ReviewDTO DTOSingle(Review review){
        ReviewDTO reviewDTO = new ReviewDTO();
        reviewDTO.setReviewID(review.getReviewID());
        reviewDTO.setBid(BidDTO.DTOSingle(review.getBid()));
        reviewDTO.setRate(review.getRate());
        reviewDTO.setComment(review.getComment());
        reviewDTO.setReviewDate(review.getReviewDate());
        return reviewDTO;
    }

    public static List<ReviewDTO> DTOList(List<Review> review){
        List<ReviewDTO> reviewDTOS = new ArrayList<>(review.size());
        for (Review tempReview : review){
            ReviewDTO reviewDTO = DTOSingle(tempReview);
            reviewDTOS.add(reviewDTO);
        }

        return reviewDTOS;
    }


}
