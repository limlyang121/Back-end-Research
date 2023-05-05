package com.myapp.restapi.researchconference.Rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.myapp.restapi.researchconference.DTO.PaperDTO;
import com.myapp.restapi.researchconference.DTO.ReviewDTO;
import com.myapp.restapi.researchconference.Exception.IllegalAccessException;
import com.myapp.restapi.researchconference.Restservice.Interface.FileRestService;
import com.myapp.restapi.researchconference.Restservice.Interface.PapersRestService;
import com.myapp.restapi.researchconference.Util.GetDataFromJWT;
import com.myapp.restapi.researchconference.entity.Paper.Paper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("api")
@CrossOrigin(origins = "*")
public class PaperRest {
    private final PapersRestService papersRestService;
    private final FileRestService fileRestService;
    private final GetDataFromJWT getDataFromJWT;
    @Autowired
    public PaperRest(PapersRestService papersRestService, FileRestService fileRestService, GetDataFromJWT getDataFromJWT) {
        this.papersRestService = papersRestService;
        this.fileRestService = fileRestService;
        this.getDataFromJWT = getDataFromJWT;
    }

    @GetMapping("papers")
    public List<PaperDTO> findAll(){
        return papersRestService.findAll();
    }

    @GetMapping("papers/myPapers")
    public List<PaperDTO> findMyPapers(HttpServletRequest request){
        int myID = getDataFromJWT.getID(request);
        return papersRestService.findMyPaper(myID);
    }


    @GetMapping("papers/myPapers/publish")
    public List<PaperDTO> findMyPublishedPapers(HttpServletRequest request){
        int myID = getDataFromJWT.getID(request);
        return papersRestService.findMyPublishedPapers(myID);
    }

    @GetMapping("papers/{paperID}/review")
    public List<ReviewDTO> findReviewsByPaperID(@PathVariable int paperID) {
        return papersRestService.findPapersReviews(paperID) ;
    }

    @GetMapping("papers/{paperID}")
    public PaperDTO findPaperByID(@PathVariable int paperID, HttpServletRequest request) throws IllegalAccessException {
        int authorID = getDataFromJWT.getID(request);
        return papersRestService.findPaperByID(paperID, authorID);
    }

    @GetMapping("papers/bid")
    public List<PaperDTO> findBidPapers(HttpServletRequest request) {
        int reviewerID = getDataFromJWT.getID(request);
        return papersRestService.findBidPapers(reviewerID);
    }

    @GetMapping("papers/ban")
    public List<PaperDTO> findBanPapers(HttpServletRequest request){
        int reviewerID = getDataFromJWT.getID(request);
        return papersRestService.findBanPapers(reviewerID);
    }

    @GetMapping("papers/complete")
    public List<PaperDTO> findReadyToPublishOrRejectPapers(){
        return papersRestService.findCompletedPapers();
    }

    @GetMapping("papers/pending")
    public List<PaperDTO> findReviewedPapers(){
        return papersRestService.findPapersThatReviewed();
    }

    @PostMapping(value = "papers", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> add(@RequestParam MultipartFile file, @RequestParam String paperData, HttpServletRequest request) throws IOException {
        int authorID = getDataFromJWT.getID(request);
        ObjectMapper objectMapper = new ObjectMapper();
        Paper paper = objectMapper.readValue(paperData, Paper.class);
        paper.getPaperInfo().getAuthorID().setId(authorID);

        Paper tempPaper = papersRestService.add(file, paper);
        if (tempPaper != null)
            return ResponseEntity.ok("Successfully Added the Paper");
        else
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unknown error have occur");

    }

    @PutMapping("papers/{paperID}")
    public ResponseEntity<String> update(@RequestBody Paper paper, @PathVariable int paperID){
        Paper temp = papersRestService.update(paper, paperID);
        if (temp != null){
            return ResponseEntity.ok("Successfully Update the Paper");
        }else{
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to update");
        }
    }

    @GetMapping ("papers/download/{paperID}")
    public ResponseEntity<byte[]> downloadPaper(@PathVariable int paperID) {
        PaperDTO temp = papersRestService.downloadPdf(paperID);
        if (temp == null){
            throw new RuntimeException("Paper not found");
        }

        byte[] fileData = fileRestService.getFileData(temp.getFile().getFileData());

        HttpHeaders headers = new HttpHeaders();

        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDisposition(ContentDisposition
                .builder("attachment")
                .filename(temp.getPaperInfo().getFilename().replaceAll(" ", "_"))
                .build());
        headers.setContentLength(fileData.length);

        return ResponseEntity.ok().headers(headers).body(fileData);
    }

    @DeleteMapping("papers/delete/{paperID}")
    public ResponseEntity<String> removePapers(@PathVariable int paperID, HttpServletRequest request) throws IllegalAccessException {
        int userID = getDataFromJWT.getID(request);
        boolean deleted = papersRestService.deletePaper(paperID, userID);
        if (deleted){
            return ResponseEntity.ok("Successfully Deleted");
        }
        else
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No paper found with the ID");
    }
}
