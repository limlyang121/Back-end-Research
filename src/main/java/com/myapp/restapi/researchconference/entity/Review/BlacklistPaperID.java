package com.myapp.restapi.researchconference.entity.Review;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BlacklistPaperID implements Serializable {
    @Column(name = "reviewerID")
    private int reviewerId;

    @Column(name = "paperID")
    private int paperId;

    // Constructors, getters, and setters

    public void storeID(int reviewerID, int paperID){
        this.reviewerId = reviewerID;
        this.paperId = paperID;
    }
}
