package com.myapp.restapi.researchconference.entity.Review;


import com.myapp.restapi.researchconference.entity.Bid.Bid;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@Entity
@Table (name = "review", schema = "public")
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "review_seq")
    @SequenceGenerator(name = "review_seq", sequenceName = "review_id_seq", allocationSize = 1)
    @Column(name = "reviewID")
    private int reviewID;

    @OneToOne
    @JoinColumn(name = "bidID")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Bid bid;

    @Column(name = "rate")
    private int rate;
    @Column(name = "comment")
    private String comment;
    @Column(name = "review_date")
    private Date reviewDate;

    public Review() {
        bid = new Bid();
    }

}
