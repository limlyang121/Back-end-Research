package com.myapp.restapi.researchconference.DTO;

import com.myapp.restapi.researchconference.entity.Admin.Userdetails;
import com.myapp.restapi.researchconference.entity.Review.Review;
import com.myapp.restapi.researchconference.entity.Review.Reviewer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReviewerDTO {
    private int reviewerID;
    private int isActive;
    private Userdetails userdetails;

    public static ReviewerDTO convertToDTO(Reviewer reviewer){
        ReviewerDTO reviewerDTO = new ReviewerDTO();
        reviewerDTO.setReviewerID(reviewer.getReviewerID());
        reviewerDTO.setIsActive(reviewer.getIsActive());
        reviewerDTO.setUserdetails(reviewer.getUserdetails());
        return reviewerDTO;
    }

    @Override
    public String toString() {
        return "ReviewerDTO{" +
                "reviewerID=" + reviewerID +
                ", isActive=" + isActive +
                '}';
    }
}
