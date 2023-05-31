package com.myapp.restapi.researchconference.Rest;


import com.myapp.restapi.researchconference.Restservice.Interface.GeneralRestService;
import com.myapp.restapi.researchconference.Restservice.Interface.UserRestService;
import com.myapp.restapi.researchconference.Util.GetDataFromJWT;
import com.myapp.restapi.researchconference.entity.Admin.User;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("api")
@PreAuthorize("hasAnyAuthority()")
public class GeneralController {
    private final GeneralRestService generalRestService;
    private final UserRestService userRestService;
    private final GetDataFromJWT getDataFromJWT;

    @Autowired
    public GeneralController(GeneralRestService generalRestService, UserRestService userRestService, GetDataFromJWT getDataFromJWT) {
        this.generalRestService = generalRestService;
        this.userRestService = userRestService;
        this.getDataFromJWT = getDataFromJWT;
    }

    @PostMapping("profile")
    public ResponseEntity<String> updateMyProfile(@RequestBody User user, HttpServletRequest request){
        int myID = getDataFromJWT.getID(request);
        User user1 = generalRestService.updateMyProfile(myID, user);
        if (user1 == null)
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("User data not found");

        return ResponseEntity.ok("Successfully Update user profile");
    }

    @GetMapping("profile")
    public User getMyData(HttpServletRequest request){
        int userID = getDataFromJWT.getID(request);
        User user = userRestService.findByID(userID);
        if (user == null)
            return null;

        return user;
    }


}
