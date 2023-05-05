package com.myapp.restapi.researchconference.Restservice.Impl;

import com.myapp.restapi.researchconference.DAO.Interface.BidDAO;
import com.myapp.restapi.researchconference.DAO.Interface.PaperDAO;
import com.myapp.restapi.researchconference.DAO.Interface.ReviewerDAO;
import com.myapp.restapi.researchconference.DTO.BidDTO;
import com.myapp.restapi.researchconference.Restservice.Interface.BidRestService;
import com.myapp.restapi.researchconference.entity.Bid.Bid;
import com.myapp.restapi.researchconference.entity.Paper.Paper;
import com.myapp.restapi.researchconference.entity.Review.Reviewer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class BidRestServiceImpl implements BidRestService {

    private final BidDAO bidDAO;
    private final ReviewerDAO reviewerDAO;
    private final PaperDAO paperDAO;

    @Autowired
    public BidRestServiceImpl(BidDAO bidDAO, ReviewerDAO reviewerDAO, PaperDAO paperDAO) {
        this.bidDAO = bidDAO;
        this.reviewerDAO = reviewerDAO;
        this.paperDAO = paperDAO;
    }

    @Override
    @Transactional
    public List<BidDTO> findAllBidByStatus(String status) {
        return BidDTO.DTOList(bidDAO.findAllBidsByStatus(status)) ;
    }

    @Override
    @Transactional
    public List<BidDTO> findMyBidByStatus(int reviewerID, String status) {
        List<Bid> bid = bidDAO.findMyBidByStatus(reviewerID, status);
        return BidDTO.DTOList(bid);
    }

    @Override
    @Transactional
    public List<BidDTO> findMyAcceptedBid(int reviewerID) {
        List<Bid> bid = bidDAO.findMyAcceptedBid(reviewerID);
        return BidDTO.DTOList(bid);
    }

    @Override
    @Transactional
    public List<BidDTO> findMyCompletedBid(int reviewerID) {
        List<Bid> bid = bidDAO.findMyCompletedBid(reviewerID);
        return BidDTO.DTOList(bid);
    }

    @Override
    @Transactional
    public List<BidDTO> findAllBidsByPaperID(int paperID) {
        List<Bid> bidList = bidDAO.findAllBidForSpecifiedPapers(paperID);
        return BidDTO.DTOList(bidList);
    }

    @Override
    @Transactional
    public BidDTO addBid(Bid bid) {
        Optional<Paper> paperOptional = paperDAO.findPaperByID(bid.getPaper().getPaperID());
        Optional<Reviewer> reviewerOptional = reviewerDAO.findByID(bid.getReviewer().getReviewerID());
        if (paperOptional.isPresent() && reviewerOptional.isPresent()){
            bid.setPaper(paperOptional.get());
            bid.setReviewer(reviewerOptional.get());
            bid.setStatus("Pending");
            bid.setBidDate(new Date());
            BidDTO bidDTO = BidDTO.DTOSingle(bidDAO.addBid(bid));
            return bidDTO ;
        }
        return null;
    }

    @Override
    @Transactional
    public boolean deleteBid(int bidID) {
        return bidDAO.deleteBid(bidID);
    }

    @Override
    @Transactional
    public boolean acceptBid(int bidID) {
        return bidDAO.acceptBid(bidID);
    }

    @Override
    @Transactional
    public boolean rejectBid(int bidID) {
        return bidDAO.rejectBid(bidID);
    }

    @Override
    @Transactional
    public boolean cancelBid(int bidID) {
        return bidDAO.cancelBid(bidID);
    }
}
