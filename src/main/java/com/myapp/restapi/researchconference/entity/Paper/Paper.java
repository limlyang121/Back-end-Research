package com.myapp.restapi.researchconference.entity.Paper;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Table (name = "paper", schema = "public")
@Entity
@Data
@Builder
@AllArgsConstructor
public class Paper {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "paper_seq")
    @SequenceGenerator(name = "paper_seq", sequenceName = "paper_id_seq", allocationSize = 1)
    @Column(name = "paperID")
    private int paperID;

    @OneToOne(fetch = FetchType.LAZY,cascade = {
        CascadeType.REMOVE, CascadeType.REFRESH, CascadeType.PERSIST, CascadeType.MERGE
    })
    @JsonIgnore
    @JoinColumn(name = "file_info_id", referencedColumnName = "fileID")
    private FileInfo fileInfo;
    @Column(name = "status")
    private String status;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn (name = "paper_info_ID")
    private PaperInfo paperInfo;

    @Column(name = "reviewed_time")
    private int reviewedTimes;

    public Paper() {
        fileInfo = new FileInfo();
        paperInfo = new PaperInfo();
    }
}
