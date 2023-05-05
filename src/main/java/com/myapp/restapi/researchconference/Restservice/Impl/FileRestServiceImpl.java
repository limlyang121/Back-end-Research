package com.myapp.restapi.researchconference.Restservice.Impl;

import com.myapp.restapi.researchconference.Restservice.Google.GoogleDriveService;
import com.myapp.restapi.researchconference.Restservice.Interface.FileRestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FileRestServiceImpl implements FileRestService {
    private final GoogleDriveService googleDriveService;

    @Autowired
    public FileRestServiceImpl(GoogleDriveService googleDriveService) {
        this.googleDriveService = googleDriveService;
    }

    @Override
    public byte[] getFileData(String fileID) {
        return googleDriveService.downloadFile(fileID);
    }
}
