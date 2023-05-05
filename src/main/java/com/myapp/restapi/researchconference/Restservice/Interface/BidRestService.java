package com.myapp.restapi.researchconference.Restservice.Interface;

import com.myapp.restapi.researchconference.DTO.BidDTO;
import com.myapp.restapi.researchconference.entity.Bid.Bid;

import java.util.List;

public interface BidRestService {
    List<BidDTO> findAllBidByStatus(String status);

    List<BidDTO> findMyBidByStatus(int reviewerID, String status);

    List<BidDTO> findMyAcceptedBid(int reviewerID);
    List<BidDTO> findMyCompletedBid(int reviewerID);
    List<BidDTO> findAllBidsByPaperID(int paperID);




    BidDTO addBid(Bid bid);
    boolean deleteBid(int bidID);

    boolean acceptBid(int bidID);
    boolean rejectBid(int bidID);
    boolean cancelBid(int bidID);

}
