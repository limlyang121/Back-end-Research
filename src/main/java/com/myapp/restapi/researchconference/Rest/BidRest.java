package com.myapp.restapi.researchconference.Rest;

import com.myapp.restapi.researchconference.DTO.BidDTO;
import com.myapp.restapi.researchconference.Restservice.Interface.BidRestService;
import com.myapp.restapi.researchconference.Util.GetDataFromJWT;
import com.myapp.restapi.researchconference.entity.Bid.Bid;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api")
@CrossOrigin(origins = "*")
public class BidRest {
    private final BidRestService bidRestService;
    private final GetDataFromJWT getDataFromJWT;

    @Autowired
    public BidRest(BidRestService bidRestService, GetDataFromJWT getDataFromJWT) {
        this.bidRestService = bidRestService;
        this.getDataFromJWT = getDataFromJWT;
    }

    @GetMapping("bids/{status}")
    public List<BidDTO> findAllBidsByStatus(@PathVariable String status){
        return bidRestService.findAllBidByStatus(status);
    }

    @GetMapping("bids/accepted")
    public List<BidDTO> findMyAcceptedBids(HttpServletRequest request){
        int reviewerID = getDataFromJWT.getID(request);
        return bidRestService.findMyAcceptedBid(reviewerID);
    }

    @GetMapping("bids/completed")
    public List<BidDTO> findMyCompletedBids(HttpServletRequest request){
        int reviewerID = getDataFromJWT.getID(request);
        return bidRestService.findMyAcceptedBid(reviewerID);
    }

    @GetMapping("bids/{reviewerID}/{status}")
    public List<BidDTO> findMyBidByStatus(@PathVariable int reviewerID, @PathVariable String status){
        return bidRestService.findMyBidByStatus(reviewerID, status);
    }

    @GetMapping("bids/ready/{paperID}")
    public List<BidDTO> findAllBidsByPaperID (@PathVariable int paperID){
        return bidRestService.findAllBidsByPaperID(paperID);
    }


    @PostMapping("bids")
    public ResponseEntity<String> addBids(@RequestBody Bid bid) {
        BidDTO temp = bidRestService.addBid(bid);
        if (temp != null)
            return ResponseEntity.ok("Successfully Bid the Paper");
        else
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to bid the paper");
    }

    @DeleteMapping("bids/{bidID}")
    public ResponseEntity<String> deleteBids(@PathVariable int bidID) {
        boolean success = bidRestService.deleteBid(bidID);
        if (success)
            return ResponseEntity.ok("Successfully Unbid");
        else
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Fail to unbid");
    }

    @PatchMapping("bids/accept/{bidID}")
    public ResponseEntity<String> acceptBids(@PathVariable int bidID){
        boolean success = bidRestService.acceptBid(bidID);
        if (success)
            return ResponseEntity.ok("Successfully Allocate the bid");
        else
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Bid Can't be found");

    }

    @PatchMapping("bids/reject/{bidID}")
    public ResponseEntity<String> rejectBid(@PathVariable int bidID){
        boolean success = bidRestService.rejectBid(bidID);
        if (success)
            return ResponseEntity.ok("Successfully Reject the bid");
        else
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Bid Can't be found");

    }

    @PatchMapping("bids/cancel/{bidID}")
    public ResponseEntity<String> cancelBid(@PathVariable int bidID){
        boolean success = bidRestService.cancelBid(bidID);
        if (success)
            return ResponseEntity.ok("Successfully Cancel the bid");
        else
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Bid Can't be found");

    }

}
