package com.myapp.restapi.researchconference.DTO;

import com.myapp.restapi.researchconference.entity.Bid.Bid;
import com.myapp.restapi.researchconference.entity.Paper.Paper;
import lombok.Data;

import java.util.List;

@Data
public class PublishDTO {
    private PaperDTO paper;
    private boolean allReviewed;

    public PaperDTO getPaper() {
        return paper;
    }

    public void setPaper(PaperDTO paper) {
        this.paper = paper;
    }

    public boolean isAllReviewed() {
        return allReviewed;
    }

    public void setAllReviewed(List<Bid> bidList) {
        this.allReviewed = true;

        for (Bid bid : bidList ){
            if (!(bid.getStatus().equalsIgnoreCase("Complete"))){
                allReviewed = false;
                break;
            }
        }
    }
}
