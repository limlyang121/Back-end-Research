package com.myapp.restapi.researchconference.Restservice.Impl;

import com.myapp.restapi.researchconference.DAO.Interface.BidDAO;
import com.myapp.restapi.researchconference.DAO.Interface.PaperDAO;
import com.myapp.restapi.researchconference.DAO.Interface.ReviewDAO;
import com.myapp.restapi.researchconference.DAO.Interface.UserRepo;
import com.myapp.restapi.researchconference.DTO.PaperDTO;
import com.myapp.restapi.researchconference.DTO.ReviewDTO;
import com.myapp.restapi.researchconference.Exception.IllegalAccessException;
import com.myapp.restapi.researchconference.Exception.NoDataFoundException;
import com.myapp.restapi.researchconference.Restservice.Google.GoogleDriveService;
import com.myapp.restapi.researchconference.Restservice.Interface.PapersRestService;
import com.myapp.restapi.researchconference.entity.Admin.Userdetails;
import com.myapp.restapi.researchconference.entity.Paper.Paper;
import com.myapp.restapi.researchconference.entity.Review.Review;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class PapersRestServiceImpl implements PapersRestService {
    private final PaperDAO paperDAO;
    private final UserRepo userRepo;
    private final ReviewDAO reviewDAO;
    private final BidDAO bidDAO;

    private final GoogleDriveService googleDriveService;

    @Autowired
    public PapersRestServiceImpl(PaperDAO paperDAO, UserRepo userRepo, ReviewDAO reviewDAO, BidDAO bidDAO, GoogleDriveService googleDriveService) {
        this.paperDAO = paperDAO;
        this.userRepo = userRepo;
        this.reviewDAO = reviewDAO;
        this.bidDAO = bidDAO;
        this.googleDriveService = googleDriveService;
    }

    @Override
    @Transactional
    public List<PaperDTO> findAll() {
        return PaperDTO.convertToDTO(paperDAO.findAll());
    }

    @Override
    @Transactional
    public List<PaperDTO> findMyPaper(int userID) {
        List<Paper> paper = paperDAO.findMyPaper(userID);
        return PaperDTO.convertToDTO(paper);
    }

    @Override
    @Transactional
    public List<PaperDTO> findMyPublishedPapers(String status, int userID) {
        List<Paper> paperList;
        if (status.equalsIgnoreCase("ALL"))
            paperList = paperDAO.findAllMyPublishedPapers(userID);
        else
            paperList = paperDAO.findMyPublishedPapersByStatus(status, userID);
        return PaperDTO.convertToDTO(paperList);
    }

    @Override
    @Transactional
    public PaperDTO findPaperByID(int paperID, int authorID) throws IllegalAccessException {
        Optional<Paper> paper = paperDAO.findPaperByID(paperID);
        if (paper.isPresent()) {
            if (paper.get().getPaperInfo().getAuthorID().getId() == authorID) {
                return PaperDTO.convertToDTOSingle(paper.get());
            } else
                throw new IllegalAccessException("You are not authorized to modify papers that do not belong to you");
        } else
            throw new NoDataFoundException("Unknown Paper ID");
    }

    @Override
    @Transactional
    public List<ReviewDTO> findPapersReviews(int paperID) {
        List<Review> reviewList = reviewDAO.findReviewsByPaperID(paperID);
        return ReviewDTO.DTOList(reviewList);
    }

    @Override
    @Transactional
    public List<PaperDTO> findBidPapers(int reviewerID) {
        List<Paper> paperList = paperDAO.findBidPapers(reviewerID);
        return PaperDTO.convertToDTO(paperList);
    }

    @Override
    @Transactional
    public List<PaperDTO> findBanPapers(int reviewerID) {
        List<Paper> paperList = paperDAO.findBanPapers(reviewerID);
        return PaperDTO.convertToDTO(paperList);
    }

    @Override
    @Transactional
    public List<PaperDTO> findPapersThatReviewed() {
        List<Paper> paperList = paperDAO.findPapersThatReviewed();
        return PaperDTO.convertToDTO(paperList);
    }

    @Override
    @Transactional
    public List<PaperDTO> findCompletedPapers() {
        List<Paper> paperList = paperDAO.findCompletedPapers();
        return PaperDTO.convertToDTO(paperList);
    }

    @Override
    @Transactional
    public Paper add(MultipartFile file, Paper paper) {
        String fileID = googleDriveService.uploadFile(file, paper.getPaperInfo().getAuthorID().getId());
        if (fileID == null) {
            return null;
        }

        File tempFile = new File(paper.getPaperInfo().getFilename());
        tempFile.delete();
        Date currentTime = new Date();
        paper.getPaperInfo().setUpload(currentTime);
        paper.setStatus("Pending");
        paper.getFileInfo().setFileDataId(fileID);
        paper.getFileInfo().setFileType(file.getContentType());
        Userdetails userdetails = userRepo.findByID(paper.getPaperInfo().getAuthorID().getId()).getUserdetails();
        paper.getPaperInfo().setAuthorID(userdetails);

//        return paper;
        return paperDAO.add(paper);
    }

    @Override
    @Transactional
    public Paper update(Paper paper, int paperID) {
        Optional<Paper> paperOptional = paperDAO.findPaperByID(paperID);
        if (paperOptional.isPresent()) {
            Paper tempPaper = paperOptional.get();
            tempPaper.setPaperInfo(paper.getPaperInfo());
            return paperDAO.updatePaper(tempPaper);
        } else {
            return null;
        }

    }

    @Override
    @Transactional
    public PaperDTO downloadPdf(int paperID) {
        Optional<Paper> paper = paperDAO.findPaperByID(paperID);
        return paper.map(PaperDTO::convertToDTODownload).orElse(null);
    }

    @Override
    @Transactional
    public boolean deletePaper(int paperID, int userID) throws IllegalAccessException {
        Optional<Paper> paperOptional = paperDAO.findPaperByID(paperID);
        if (paperOptional.isPresent()) {
            Paper paper = paperOptional.get();
            if (paper.getPaperInfo().getAuthorID().getId() != userID) {
                throw new IllegalAccessException("Can't delete other user Papers");
            }
            boolean success = bidDAO.deleteBidAssociatedWithThisPaperID(paperID);
            if (success) {
                googleDriveService.deleteFile(paper.getFileInfo().getFileDataId());
                paperDAO.deletePaper(paperID);
                return true;
            }
            return false;
        }
        throw new NoDataFoundException("No Paper found with that ID");
    }


}
