package com.myapp.restapi.researchconference.Restservice.Interface;

import com.myapp.restapi.researchconference.DTO.PaperDTO;
import com.myapp.restapi.researchconference.DTO.PublishDTO;

import java.util.List;

public interface PublishRestService {
    List<PublishDTO> findReadyToPublishOrRejectPapers();

    boolean readyPaper(int paperID);

    boolean acceptPaper(int paperID);
    boolean rejectPaper(int paperID);

}
