package com.myapp.restapi.researchconference.entity.Paper;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DownloadFileWrapper {
    private byte[] blob;
    private String applicationType;
    private String fileName;


}
