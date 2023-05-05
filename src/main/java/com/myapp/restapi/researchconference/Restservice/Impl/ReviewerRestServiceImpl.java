package com.myapp.restapi.researchconference.Restservice.Impl;

import com.myapp.restapi.researchconference.DAO.Interface.ReviewerDAO;
import com.myapp.restapi.researchconference.DTO.ReviewerDTO;
import com.myapp.restapi.researchconference.Restservice.Interface.ReviewerRestService;
import com.myapp.restapi.researchconference.entity.Review.Reviewer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class ReviewerRestServiceImpl implements ReviewerRestService {
    private final ReviewerDAO reviewerDAO;

    @Autowired
    public ReviewerRestServiceImpl(ReviewerDAO reviewerDAO) {
        this.reviewerDAO = reviewerDAO;
    }

    @Override
    @Transactional
    public ReviewerDTO findByUserID(int userID) {
        Optional<Reviewer> reviewer = reviewerDAO.findByUserID(userID);
        if (reviewer.isPresent()) {
            return ReviewerDTO.convertToDTO(reviewer.get());
        } else {
            return null;
        }
    }
}
