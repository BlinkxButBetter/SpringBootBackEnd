package io.b3.Controllers;

import io.b3.Models.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.lang.model.type.NullType;
import java.io.IOException;

@RestController
@RequestMapping("/images")
public class ImageController {

    @Autowired
    private ImageService imageService;

    // Endpoint to upload an image and return its URL
    @PostMapping("/upload")
    public ResponseEntity<String> uploadImage(@RequestParam("file") MultipartFile file) throws IOException {
        String fileId = imageService.storeImage(file);  // Store the image and get its ID

        // Generate the URL for the uploaded image
        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/images/get/")
                .path(fileId)
                .path("/")
                .toUriString();

        // Return the URL as the response
        return ResponseEntity.ok(fileDownloadUri);
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
