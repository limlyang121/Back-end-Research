package com.myapp.restapi.researchconference.Restservice.Interface;

import com.myapp.restapi.researchconference.DTO.ReviewerDTO;
import com.myapp.restapi.researchconference.entity.Review.Reviewer;

import java.util.Optional;

public interface ReviewerRestService {
    ReviewerDTO findByUserID(int userID);

}
