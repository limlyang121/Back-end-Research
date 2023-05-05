package com.myapp.restapi.researchconference.DAO.Interface;

import com.myapp.restapi.researchconference.entity.Paper.Paper;

import java.util.List;
import java.util.Optional;

public interface PaperDAO {
    List<Paper> findAll();

    List<Paper> findMyPaper(int userID);
    List<Paper> findAllMyPublishedPapers(int userID);

    List<Paper> findMyPublishedPapersByStatus(String status, int userID);


    Optional<Paper> findPaperByID(int userID);

    List<Paper> findBidPapers(int reviewerID);

    List<Paper> findBanPapers(int reviewerID);

    List<Paper> findPapersThatReviewed();

    List<Paper> findReadyPapers();


    List<Paper> findCompletedPapers();

    Paper add(Paper paper);

    void increaseReviewTimes(int paperID);

    boolean deletePaper(int paperID);

    Paper updatePaper(Paper paper);

    boolean readyPaper(int paperID);

    boolean acceptPaper(int paperID);

    boolean rejectPaper(int paperID);

}
