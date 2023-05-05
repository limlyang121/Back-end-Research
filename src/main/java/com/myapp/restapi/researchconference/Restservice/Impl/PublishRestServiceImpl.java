package com.myapp.restapi.researchconference.Restservice.Impl;

import com.myapp.restapi.researchconference.DAO.Interface.BidDAO;
import com.myapp.restapi.researchconference.DAO.Interface.BlacklistDAO;
import com.myapp.restapi.researchconference.DAO.Interface.PaperDAO;
import com.myapp.restapi.researchconference.DAO.Interface.PublishDAO;
import com.myapp.restapi.researchconference.DTO.PaperDTO;
import com.myapp.restapi.researchconference.DTO.PublishDTO;
import com.myapp.restapi.researchconference.Restservice.Interface.PublishRestService;
import com.myapp.restapi.researchconference.entity.Bid.Bid;
import com.myapp.restapi.researchconference.entity.Paper.Paper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class PublishRestServiceImpl implements PublishRestService {
    private final PaperDAO paperDAO;
    private final PublishDAO publishDAO;
    private final BidDAO bidDAO;
    private final BlacklistDAO blacklistDAO;

    @Autowired
    public PublishRestServiceImpl(PaperDAO paperDAO, PublishDAO publishDAO, BidDAO bidDAO, BlacklistDAO blacklistDAO) {
        this.paperDAO = paperDAO;
        this.publishDAO = publishDAO;
        this.bidDAO = bidDAO;
        this.blacklistDAO = blacklistDAO;
    }

    @Override
    @Transactional
    public List<PublishDTO> findReadyToPublishOrRejectPapers() {
        List<Paper> paperList = paperDAO.findReadyPapers();
        List<PublishDTO> publishDTOS = new ArrayList<>(paperList.size());

        for (Paper paper : paperList){
            PublishDTO publishDTO = new PublishDTO();
            List<Bid> bidList = bidDAO.findAllBidForSpecifiedPapers(paper.getPaperID());
            PaperDTO paperDTO = PaperDTO.convertToDTOSingle(paper);
            publishDTO.setPaper(paperDTO);
            publishDTO.setAllReviewed(bidList);
            publishDTOS.add(publishDTO);
        }

        return publishDTOS;

    }

    @Override
    @Transactional
    public boolean readyPaper(int paperID) {
        return paperDAO.readyPaper(paperID);
    }

    @Override
    @Transactional
    public boolean acceptPaper(int paperID) {
        boolean success = paperDAO.acceptPaper(paperID);
        if (success){
            if (blacklistDAO.deleteBlackListAssociatedWithSpecifiedPaperID(paperID))
                return bidDAO.deleteRejectedBidOncePaperPublish(paperID);
        }
        return false;
    }

    @Override
    @Transactional
    public boolean rejectPaper(int paperID) {
        boolean success = paperDAO.rejectPaper(paperID);
        if (success){
            if  (blacklistDAO.deleteBlackListAssociatedWithSpecifiedPaperID(paperID))
                return bidDAO.deleteRejectedBidOncePaperPublish(paperID);
        }
        return false;
    }
}
