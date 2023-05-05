package com.myapp.restapi.researchconference.Rest;

import com.myapp.restapi.researchconference.DTO.ReviewerDTO;
import com.myapp.restapi.researchconference.Restservice.Interface.ReviewerRestService;
import com.myapp.restapi.researchconference.Util.GetDataFromJWT;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api")
@CrossOrigin(origins = "*")
public class ReviewerRest {
    private final ReviewerRestService reviewerRestService;
    private final GetDataFromJWT getDataFromJWT;

    @Autowired
    public ReviewerRest(ReviewerRestService reviewerRestService, GetDataFromJWT getDataFromJWT) {
        this.reviewerRestService = reviewerRestService;
        this.getDataFromJWT = getDataFromJWT;
    }

    @GetMapping("reviewer")
    public ReviewerDTO findByUserID(HttpServletRequest request) {
        int userID = getDataFromJWT.getID(request);
        return reviewerRestService.findByUserID(userID);
    }
}
