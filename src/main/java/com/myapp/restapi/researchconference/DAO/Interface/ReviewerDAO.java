package com.myapp.restapi.researchconference.DAO.Interface;


import com.myapp.restapi.researchconference.entity.Admin.User;
import com.myapp.restapi.researchconference.entity.Admin.Userdetails;
import com.myapp.restapi.researchconference.entity.Review.Reviewer;

import java.util.List;
import java.util.Optional;

public interface ReviewerDAO {
    List<Reviewer> findAll();

    Optional<Reviewer> findByID(int reviewerID);

    Optional<Reviewer> findByUserID(int userID);

    Reviewer addReviewer(Reviewer reviewer);

    boolean isActive(int reviewerID);

    boolean isNotActive(int reviewerID);
}
