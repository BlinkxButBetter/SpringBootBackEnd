package io.b3.Controllers;

import io.b3.Services.ImageService;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;

@RestController
@RequestMapping("/")
public class ImageController {

    @Autowired
    private ImageService imageService;

    @PostMapping("/images/uploads")
    public ResponseEntity<ArrayList<String>> uploadImage(
            @RequestParam("files") MultipartFile[] files ) throws IOException {
//        String fileId = imageService.storeImage(file);
        ArrayList<String> result = new ArrayList<>();
        for (MultipartFile file : files) {
            String fileUrl = imageService.storeImage(file);
            result.add(fileUrl);
        }

        return ResponseEntity.ok(result);
    }
    @PostMapping("/images/upload")
    public ResponseEntity<String> uploadImage(
            @RequestParam("files")
            @Schema(type = "file", description = "The image file to upload")
            MultipartFile files ) throws IOException {
        String fileId = imageService.storeImage(files);

        return ResponseEntity.ok(fileId);
    }


    @GetMapping({"/images/get/{fileId}", "/images/get/{fileId}/"})
    public ResponseEntity<byte[]> viewImage(@PathVariable String fileId) throws IOException {
        GridFsResource resource = imageService.getImage(fileId);


        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(resource.getContentType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + resource.getFilename() + "\"")
                .body(resource.getInputStream().readAllBytes());
    }

    @GetMapping({"/get/{fileName}", "/get/{fileName}/"})
    public ResponseEntity<byte[]> viewFile(@PathVariable String fileName) throws IOException {
        GridFsResource resource = imageService.getFileByName(fileName);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(resource.getContentType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\""+resource.getFilename()+"\"")
                .body(resource.getInputStream().readAllBytes());
    }

}
