package com.myapp.restapi.researchconference.entity.Paper;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Arrays;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "file", schema = "public")
public class FileInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "file_seq")
    @SequenceGenerator(name = "file_seq", sequenceName = "file_id_seq", allocationSize = 1)
    private int fileID;

    @Column(name = "file_data_id")
    private String fileDataId;

    @Column(name = "file_type")
    private String fileType;

    @Override
    public String toString() {
        return "File{" +
                "fileID=" + fileID +
                ", fileData=" + fileDataId +
                ", fileType='" + fileType + '\'' +
                '}';
    }
}
