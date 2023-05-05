package com.myapp.restapi.researchconference.DTO;

import com.myapp.restapi.researchconference.entity.Bid.Bid;
import com.myapp.restapi.researchconference.entity.Paper.Paper;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
public class BidDTO {
    private int bidID;
    private PaperDTO paper;
    private ReviewerDTO reviewer;
    private String status;
    private Date bidDate;

    public BidDTO() {
        paper = new PaperDTO();
        reviewer = new ReviewerDTO();
    }

    public static BidDTO DTOIDOnly(Bid bid){
        BidDTO bidDTO = new BidDTO();
        bidDTO.setBidID(bid.getBidID());
        bidDTO.setReviewer(ReviewerDTO.convertToDTO(bid.getReviewer()));
        bidDTO.setBidDate(bid.getBidDate());
        return bidDTO;
    }

    public static BidDTO DTOSingle(Bid bid){
        BidDTO bidDTO = new BidDTO();
        bidDTO.setBidID(bid.getBidID());
        bidDTO.setPaper(PaperDTO.convertToDTOBid(bid.getPaper()));
        bidDTO.setReviewer(ReviewerDTO.convertToDTO(bid.getReviewer()));
        bidDTO.setStatus(bid.getStatus());
        bidDTO.setBidDate(bid.getBidDate());
        return bidDTO;
    }

    public static List<BidDTO> DTOList(List<Bid> bidList  ){
        List<BidDTO> bidDTOList = new ArrayList<>(bidList.size());
        for (Bid bid : bidList){
            BidDTO bidDTO = DTOSingle(bid);
            bidDTOList.add(bidDTO);
        }
        return bidDTOList;
    }

    @Override
    public String toString() {
        return "BidDTO{" +
                "bidID=" + bidID +
                ", paperDTO=" + paper +
                ", reviewerDTO=" + reviewer +
                ", status='" + status + '\'' +
                '}';
    }
}
