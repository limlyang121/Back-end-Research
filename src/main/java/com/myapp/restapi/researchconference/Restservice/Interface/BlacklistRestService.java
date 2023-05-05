package com.myapp.restapi.researchconference.Restservice.Interface;

import com.myapp.restapi.researchconference.entity.Review.BlacklistPaper;

public interface BlacklistRestService {
    boolean addBlackList(BlacklistPaper blacklistPaper);

    boolean deleteBlackList(BlacklistPaper blacklistPaper);

}
