package com.myapp.restapi.researchconference.Rest;


import com.myapp.restapi.researchconference.DTO.PublishDTO;
import com.myapp.restapi.researchconference.Restservice.Interface.PublishRestService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("api")
public class PublishRest {
    private final PublishRestService publishRestService;

    public PublishRest(PublishRestService publishRestService) {
        this.publishRestService = publishRestService;
    }

    @GetMapping("publish/papers/final")
    public List<PublishDTO> findReadyToPublishOrRejectPapers(){
        return publishRestService.findReadyToPublishOrRejectPapers();
    }

    @PatchMapping("publish/ready/{paperID}")
    public ResponseEntity<String> readyPaper(@PathVariable int paperID){
        boolean success = publishRestService.readyPaper(paperID);
        if (success){
            return ResponseEntity.ok("Successfully Close the bidding for this Paper");
        }else
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to Close the bid for this paper");
    }

    @PatchMapping("publish/accept/{paperID}")
    public ResponseEntity<String> acceptPaper(@PathVariable int paperID){
        boolean success = publishRestService.acceptPaper(paperID);
        if (success){
            return ResponseEntity.ok("Successfully Accept the Paper to be publish");
        }else
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to Accept the paper");
    }

    @PatchMapping("publish/reject/{paperID}")
    public ResponseEntity<String> rejectPaper(@PathVariable int paperID){
        boolean success = publishRestService.rejectPaper(paperID);
        if (success){
            return ResponseEntity.ok("Successfully Reject the Paper to be publish");
        }else
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to Reject the paper");
    }
}
