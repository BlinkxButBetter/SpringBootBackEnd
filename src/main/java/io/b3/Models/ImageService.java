package io.b3.Models;

import com.mongodb.client.gridfs.model.GridFSFile;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.io.InputStream;

@Service
public class ImageService {

    @Autowired
    private GridFsTemplate gridFsTemplate;

    // Method to store the image, setting content type as "image/*"
    public String storeImage(MultipartFile file) throws IOException {
        // Get the file input stream and save it using GridFS
        InputStream inputStream = file.getInputStream();

        // Set content type as "image/*"
//        String contentType = "image/jpeg"; // or a specific type like "image/png", "image/jpeg", etc.

        ObjectId fileId = gridFsTemplate.store(inputStream, file.getOriginalFilename(), file.getContentType());
        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/images/get/")
                .path(fileId.toString())
                .path("/")
                .toUriString();
        return fileDownloadUri;  // Return the file ID as a reference
    }

    // Method to retrieve the image by file ID
    public GridFsResource getImage(String fileId) throws IOException {
        GridFSFile gridFSFile = gridFsTemplate.findOne(
                new org.springframework.data.mongodb.core.query.Query(
                        org.springframework.data.mongodb.core.query.Criteria.where("_id").is(new ObjectId(fileId))));

        if (gridFSFile == null) {
            throw new IOException("No file found with the given ID: " + fileId);
        }

        return gridFsTemplate.getResource(gridFSFile);
    }
}
