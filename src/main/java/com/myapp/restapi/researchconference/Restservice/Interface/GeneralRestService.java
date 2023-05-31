package com.myapp.restapi.researchconference.Restservice.Interface;

import com.myapp.restapi.researchconference.entity.Admin.User;

public interface GeneralRestService {
    User updateMyProfile(int userID, User user);

}
