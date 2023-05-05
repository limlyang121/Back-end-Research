package com.myapp.restapi.researchconference.entity.Bid;


import com.myapp.restapi.researchconference.entity.Paper.Paper;
import com.myapp.restapi.researchconference.entity.Review.Reviewer;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.Date;

@Data
@AllArgsConstructor
@Entity
@Table(name = "bid", schema = "public")
public class Bid {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "bid_seq")
    @SequenceGenerator(name = "bid_seq", sequenceName = "bid_id_seq", allocationSize = 1)
    private int bidID;

    @OneToOne (cascade = {
            CascadeType.PERSIST, CascadeType.DETACH
    })
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "paperID")
    private Paper paper;
    @OneToOne (cascade = {
            CascadeType.PERSIST, CascadeType.DETACH})
    @JoinColumn(name = "reviewerID")
    private Reviewer reviewer;
    private String status;

    @Column(name = "bid_date")
    private Date bidDate;

    public Bid() {
        paper = new Paper();
        reviewer = new Reviewer();
    }


}
