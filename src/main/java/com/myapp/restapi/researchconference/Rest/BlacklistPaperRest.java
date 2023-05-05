package com.myapp.restapi.researchconference.Rest;

import com.myapp.restapi.researchconference.Restservice.Interface.BlacklistRestService;
import com.myapp.restapi.researchconference.entity.Review.BlacklistPaper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("api")
@CrossOrigin(origins = "*")
public class BlacklistPaperRest {
    private final BlacklistRestService blacklistRestService;

    @Autowired
    public BlacklistPaperRest(BlacklistRestService blacklistRestService) {
        this.blacklistRestService = blacklistRestService;
    }


    @PostMapping("blacklist")
    public ResponseEntity<String> addBlackList(@RequestBody BlacklistPaper blacklistPaper){
        boolean success = blacklistRestService.addBlackList(blacklistPaper);
        if (success)
            return ResponseEntity.ok("Successfully add to Blacklist");
        else
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Unknown Error Occur");
    }

    @DeleteMapping("blacklist")
    public ResponseEntity<String> deleteBlackList(@RequestBody BlacklistPaper blacklistPaper){
        boolean success = blacklistRestService.deleteBlackList(blacklistPaper);
        if (success)
            return ResponseEntity.ok("Successfully add to Blacklist");
        else
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Unknown Error Occur");
    }

}
