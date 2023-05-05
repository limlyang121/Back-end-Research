package com.myapp.restapi.researchconference.DTO;

import com.myapp.restapi.researchconference.entity.Paper.Paper;
import com.myapp.restapi.researchconference.entity.Paper.PaperInfo;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
public class PaperDTO {
    private int paperID;
    private FileDTO file;
    private String status;
    private PaperInfo paperInfo;
    private int reviewedTimes;

    public PaperDTO() {
        file = new FileDTO();
        paperInfo = new PaperInfo();
    }

    public static List<PaperDTO> convertToDTO(List<Paper> paperList){
        List<PaperDTO> paperDTOList = new ArrayList<>(paperList.size());
        for (Paper paper : paperList){
            PaperDTO  paperDTO = new PaperDTO();
            paperDTO.setPaperID(paper.getPaperID());
            paperDTO.setStatus(paper.getStatus());
            paperDTO.setPaperInfo(paper.getPaperInfo());
            paperDTO.setReviewedTimes(paper.getReviewedTimes());
            paperDTOList.add(paperDTO);
        }
        return paperDTOList;
    }

    public static PaperDTO convertToDTOSingle(Paper paper){
        PaperDTO paperDTO = new PaperDTO();
        paperDTO.setPaperID(paper.getPaperID());
        paperDTO.setStatus(paper.getStatus());
        paperDTO.setPaperInfo(paper.getPaperInfo());
        paperDTO.setReviewedTimes(paper.getReviewedTimes());
        return paperDTO;
    }

    public static PaperDTO convertToDTOBid(Paper paper){
        PaperDTO paperDTO = new PaperDTO();
        paperDTO.setPaperID(paper.getPaperID());
        paperDTO.setStatus(paper.getStatus());
        paperDTO.setPaperInfo(paper.getPaperInfo());
        return paperDTO;
    }

    public static PaperDTO convertToDTODownload(Paper paper){
        PaperDTO paperDTO = new PaperDTO();
        paperDTO.setFile(FileDTO.DTOSingle(paper.getFileInfo()));
        paperDTO.setPaperInfo(paper.getPaperInfo());
        return paperDTO;
    }

    @Override
    public String toString() {
        return "PaperDTO{" +
                "paperID=" + paperID +
                ", file=" + file +
                ", status='" + status + '\'' +
                ", paperInfo=" + paperInfo +
                '}';
    }
}
