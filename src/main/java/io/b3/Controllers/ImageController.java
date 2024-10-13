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
@RequestMapping("/images")
public class ImageController {

    @Autowired
    private ImageService imageService;

    // Endpoint to upload an image and return its URL
    @PostMapping("/uploads")
    public ResponseEntity<ArrayList<String>> uploadImage(
            @RequestParam("files") MultipartFile[] files ) throws IOException {
//        String fileId = imageService.storeImage(file);  // Store the image and get its ID
        ArrayList<String> result = new ArrayList<>();
        for (MultipartFile file : files) {
            String fileUrl = imageService.storeImage(file);
            result.add(fileUrl);
        }
        // Generate the URL for the uploaded image


        // Return the URL as the response
        return ResponseEntity.ok(result);
    }
    @PostMapping("/upload")
    public ResponseEntity<String> uploadImage(
            @RequestParam("files")
            @Schema(type = "file", description = "The image file to upload")
            MultipartFile files ) throws IOException {
        String fileId = imageService.storeImage(files);
        // Generate the URL for the uploaded image


        // Return the URL as the response
        return ResponseEntity.ok(fileId);
    }

    // Endpoint to download an image
    @GetMapping({"/get/{fileId}", "/get/{fileId}/"})
    public ResponseEntity<byte[]> viewImage(@PathVariable String fileId) throws IOException {
        GridFsResource resource = imageService.getImage(fileId);

        // Return the image file as a byte array for viewing
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(resource.getContentType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + resource.getFilename() + "\"")  // Set to inline for viewing
                .body(resource.getInputStream().readAllBytes());
    }

}
