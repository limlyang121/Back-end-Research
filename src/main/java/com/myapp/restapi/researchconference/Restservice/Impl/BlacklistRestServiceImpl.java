package com.myapp.restapi.researchconference.Restservice.Impl;

import com.myapp.restapi.researchconference.DAO.Interface.BlacklistDAO;
import com.myapp.restapi.researchconference.DAO.Interface.PaperDAO;
import com.myapp.restapi.researchconference.DAO.Interface.ReviewerDAO;
import com.myapp.restapi.researchconference.Restservice.Interface.BlacklistRestService;
import com.myapp.restapi.researchconference.entity.Paper.Paper;
import com.myapp.restapi.researchconference.entity.Review.BlacklistPaper;
import com.myapp.restapi.researchconference.entity.Review.BlacklistPaperID;
import com.myapp.restapi.researchconference.entity.Review.Reviewer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class BlacklistRestServiceImpl implements BlacklistRestService {
    private final PaperDAO paperDAO;
    private final BlacklistDAO blacklistDAO;
    private final ReviewerDAO reviewerDAO;

    @Autowired
    public BlacklistRestServiceImpl(PaperDAO paperDAO, BlacklistDAO blacklistDAO, ReviewerDAO reviewerDAO) {
        this.paperDAO = paperDAO;
        this.blacklistDAO = blacklistDAO;
        this.reviewerDAO = reviewerDAO;
    }

    @Override
    @Transactional
    public boolean addBlackList(BlacklistPaper blacklistPaper) {
        BlacklistPaper myData =  setUpData(blacklistPaper);
        if (myData != null){
            return blacklistDAO.addBlackList(myData);
        }
        return false;
    }

    @Override
    @Transactional
    public boolean deleteBlackList(BlacklistPaper blacklistPaper) {
        BlacklistPaper myData =  setUpData(blacklistPaper);
        if (myData != null){
            return blacklistDAO.deleteBlackList(myData);
        }
        return false;
    }

    private BlacklistPaper setUpData(BlacklistPaper blacklistPaper){
        Optional<Paper> paperOptional = paperDAO.findPaperByID(blacklistPaper.getPaper().getPaperID());
        Optional<Reviewer> reviewerOptional = reviewerDAO.findByID(blacklistPaper.getReviewer().getReviewerID());
        if (paperOptional.isPresent() && reviewerOptional.isPresent()) {
            BlacklistPaperID id = new BlacklistPaperID(reviewerOptional.get().getReviewerID(), paperOptional.get().getPaperID());
            blacklistPaper.setId(id);
            blacklistPaper.setPaper(paperOptional.get());
            blacklistPaper.setReviewer(reviewerOptional.get());
            return blacklistPaper;
        }
        return null;
    }

}
