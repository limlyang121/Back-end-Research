package com.myapp.restapi.researchconference.entity.Review;

import com.myapp.restapi.researchconference.entity.Paper.Paper;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Data
@Entity
@Table(name = "blacklist_paper", schema = "public")
public class BlacklistPaper{
    @EmbeddedId
    private BlacklistPaperID id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {
            CascadeType.DETACH,
            CascadeType.PERSIST,
    })
    @JoinColumn(name = "reviewerID", referencedColumnName = "reviewerID", insertable = false, updatable = false)
    private Reviewer reviewer;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {
            CascadeType.DETACH,
            CascadeType.PERSIST
    })
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "paperID", referencedColumnName = "paperID", insertable = false, updatable = false)
    private Paper paper;

    public BlacklistPaper() {
        reviewer = new Reviewer();
        paper = new Paper();
    }

    public BlacklistPaper(Reviewer reviewer, Paper paper) {
        this.reviewer = reviewer;
        this.paper = paper;
    }
}
