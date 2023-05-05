package com.myapp.restapi.researchconference.Restservice.Google;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.http.FileContent;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Service
public class GoogleDriveService {
    private static final String APPLICATION_NAME = "google-drive-demo";
    private static final String SERVICE_ACCOUNT_EMAIL = "service-account@valencia-384403.iam.gserviceaccount.com";
    private static final String SERVICE_ACCOUNT_JSON_FILE_PATH = "classpath:/serviceAccount.json";

    private static final String parentFolderId = "1aFIZ3sw9h159h8A46CwsKW1DG3OA7_8k";
    private final HttpTransport httpTransport;
    private final JsonFactory jsonFactory;
    private final Drive drive;


    @Autowired
    public GoogleDriveService(HttpTransport httpTransport, JsonFactory jsonFactory) throws GeneralSecurityException, IOException {
        this.httpTransport = httpTransport;
        this.jsonFactory = jsonFactory;
        this.drive = getDriveService();
    }

    private Drive getDriveService() throws GeneralSecurityException, IOException {
        GoogleCredential credential = GoogleCredential.fromStream(Objects.requireNonNull(getClass().getResourceAsStream("/serviceAccount.json")))
                .createScoped(Collections.singleton(DriveScopes.DRIVE_FILE));
        return new Drive.Builder(httpTransport, jsonFactory, credential)
                .setHttpRequestInitializer(credential)
                .setApplicationName(APPLICATION_NAME)
                .build();
    }

    private java.io.File convertMultipartFileToFile(MultipartFile multipartFile) throws IOException {
        java.io.File file = new java.io.File(multipartFile.getOriginalFilename());
        file.createNewFile();
        FileOutputStream fos = new FileOutputStream(file);
        fos.write(multipartFile.getBytes());
        fos.close();
        return file;
    }

    public boolean isAuthenticated() {
        try {
            Drive drive = getDriveService();
            drive.about().get().setFields("kind,user").execute();
            return true;
        } catch (IOException | GeneralSecurityException e) {
            return false;
        }
    }


    public String uploadFile(MultipartFile multipartFile, int userID) {
        try{
            java.io.File myFile = convertMultipartFileToFile(multipartFile);

            // Create the subfolder
            String subFolderName = "User "+userID;
            String subFolderId = createSubFolder(parentFolderId, subFolderName);

            // Upload the file to the subfolder
            File fileMetadata = new File();
            fileMetadata.setName(myFile.getName());
            fileMetadata.setParents(Collections.singletonList(subFolderId));

            FileContent mediaContent = new FileContent(null, myFile);
            File uploadedFile = drive.files().create(fileMetadata, mediaContent)
                    .setFields("id")
                    .execute();
            return uploadedFile.getId();

        }catch (Exception e){
            return null;
        }

    }

    private String createSubFolder(String folderId, String subFolderName) throws IOException {
        String subFolderId = null;

        // Check if the subfolder already exists
        String query = "mimeType='application/vnd.google-apps.folder' and trashed=false and '" + folderId + "' in parents and name='" + subFolderName + "'";
        FileList result = drive.files().list().setQ(query).execute();
        List<File> files = result.getFiles();
        if (files != null && !files.isEmpty()) {
            subFolderId = files.get(0).getId();
        } else {
            // Create the subfolder
            File fileMetadata = new File();
            fileMetadata.setName(subFolderName);
            fileMetadata.setParents(Collections.singletonList(folderId));
            fileMetadata.setMimeType("application/vnd.google-apps.folder");
            File subFolder = drive.files().create(fileMetadata).setFields("id").execute();
            subFolderId = subFolder.getId();
        }

        return subFolderId;
    }

//    public String createRandomFolder() throws IOException {
//        String folderName = "TestID";
//        String parentFolderId = "1aFIZ3sw9h159h8A46CwsKW1DG3OA7_8k"; // Replace with the ID of your "ResearchData" folder
//        File fileMetadata = new File();
//        fileMetadata.setName(folderName);
//        fileMetadata.setParents(Collections.singletonList(parentFolderId));
//        fileMetadata.setMimeType("application/vnd.google-apps.folder");
//        File folder = drive.files().create(fileMetadata).setFields("id").execute();
//        return folder.getId();
//    }
    private String getFolderId(String folderName, String parentFolderId) throws IOException {
        String query = "mimeType='application/vnd.google-apps.folder' and trashed=false and name='" + folderName + "' and '" + parentFolderId + "' in parents";
        FileList result = drive.files().list().setQ(query).setSpaces("drive").execute();
        List<File> files = result.getFiles();
        if (files != null && !files.isEmpty()) {
            return files.get(0).getId();
        } else {
            throw new FileNotFoundException("Folder not found: " + folderName);
        }
    }

    public byte[] downloadFile(String itemId) {
        try{
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            drive.files().get(itemId).executeMediaAndDownloadTo(outputStream);
            return outputStream.toByteArray();
        }catch (IOException exception){
            System.out.println(exception);
            return null;
        }
    }

    public boolean deleteFile(String fileId) {
        try{
            drive.files().delete(fileId).execute();
            return true;
        }catch (IOException e){
            return false;
        }
    }

}
