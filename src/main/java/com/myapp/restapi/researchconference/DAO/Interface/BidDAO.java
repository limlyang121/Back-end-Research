package com.myapp.restapi.researchconference.DAO.Interface;

import com.myapp.restapi.researchconference.entity.Bid.Bid;

import java.util.List;
import java.util.Optional;

public interface BidDAO {
    List<Bid> findAllBidsByStatus(String Status);

    List<Bid> findMyBidByStatus(int reviewerID, String status);

    List<Bid> findMyAcceptedBid(int reviewerID);
    List<Bid> findMyCompletedBid(int reviewerID);

    List<Bid> findReadyPapersBid();

    List<Bid> findAllBidForSpecifiedPapers(int paperID);

    Optional<Bid> findBidByID(int bidID);


    Optional<Bid> findBidByForeignKey(int reviewerID, int paperID);

    boolean hideBid(int bidID);
    Bid addBid(Bid bid);

    boolean deleteBid(int bidID);
    boolean deleteBidAssociatedWithThisPaperID(int paperID);

    boolean deleteRejectedBidOncePaperPublish(int paperID);

    boolean acceptBid(int bidID);
    boolean rejectBid(int bidID);

    boolean completeBid(int bidID);

    boolean cancelBid(int bidID);

}
