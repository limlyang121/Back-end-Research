package com.myapp.restapi.researchconference.DTO;

import com.myapp.restapi.researchconference.entity.Paper.FileInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FileDTO {
    private int fileID;
    private String fileData;
    private String fileType;

    public static FileDTO DTOSingle(FileInfo fileInfo){
        FileDTO fileDTO = new FileDTO();
        fileDTO.setFileID(fileDTO.fileID);
        fileDTO.setFileData(fileInfo.getFileDataId());
        fileDTO.setFileType(fileInfo.getFileType());
        return fileDTO;
    }

}
