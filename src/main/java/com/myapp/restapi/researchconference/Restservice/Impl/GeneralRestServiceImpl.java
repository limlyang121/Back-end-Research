package com.myapp.restapi.researchconference.Restservice.Impl;

import com.myapp.restapi.researchconference.DAO.Interface.UserRepo;
import com.myapp.restapi.researchconference.Exception.PrivilegesUserException;
import com.myapp.restapi.researchconference.Exception.UsernameExistedException;
import com.myapp.restapi.researchconference.Restservice.Interface.GeneralRestService;
import com.myapp.restapi.researchconference.entity.Admin.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class GeneralRestServiceImpl implements GeneralRestService {
    private final UserRepo userRepo;

    @Autowired
    public GeneralRestServiceImpl(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    @Transactional
    public User updateMyProfile(int userID,User user) {
        if (userID >=0 && userID <= 4)
            throw new PrivilegesUserException("Can't modified user 1 to 4");

        User myData = userRepo.findByID(userID);
        if (myData == null)
            return null;

        User checkIfUserExisted = userRepo.findByUserName(user.getUserName());
        if (checkIfUserExisted != null && checkIfUserExisted.getId() != userID){
            throw new UsernameExistedException("Username is already used by Someone");
        }

        myData.setUserName(user.getUserName());
        myData.setUserdetails(user.getUserdetails());
        return userRepo.save(myData);
    }
}
