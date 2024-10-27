package io.b3.Services;

import com.mongodb.client.gridfs.model.GridFSFile;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
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


    public String storeImage(MultipartFile file) throws IOException {

        InputStream inputStream = file.getInputStream();

        ObjectId fileId = gridFsTemplate.store(inputStream, file.getOriginalFilename(), file.getContentType());
        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/images/get/")
                .path(fileId.toString())
                .path("/")
                .toUriString();
        return fileDownloadUri;
    }

    public GridFsResource getImage(String fileId) throws IOException {
        GridFSFile gridFSFile = gridFsTemplate.findOne(
                new org.springframework.data.mongodb.core.query.Query(
                        org.springframework.data.mongodb.core.query.Criteria.where("_id").is(new ObjectId(fileId))));

        if (gridFSFile == null) {
            throw new IOException("No file found with the given ID: " + fileId);
        }

        return gridFsTemplate.getResource(gridFSFile);
    }
    public GridFsResource getFileByName(String filename) throws IOException {
        // Find the file by its filename
        GridFSFile gridFSFile = gridFsTemplate.findOne(new Query(Criteria.where("filename").is(filename)));

        if (gridFSFile == null) {
            throw new IOException("No file found with the given filename: " + filename);
        }

        return gridFsTemplate.getResource(gridFSFile);
    }
}
