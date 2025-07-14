package com.ddd.oi.common.controller;

import java.net.URL;

import com.ddd.oi.contents.dto.PresignedUrlRequest;
import com.ddd.oi.contents.dto.PresignedUrlResponse;
import com.ddd.oi.common.response.CustomApiResponse;
import com.ddd.oi.common.service.S3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/s3")
@RequiredArgsConstructor
public class S3Controller {
    private final S3Service s3Service;

    @PostMapping("/presigned")
    public CustomApiResponse<String> getPresignedUrl(@RequestBody PresignedUrlRequest request) {
        return CustomApiResponse.success(
            s3Service.createPresignedUrl(request.fileName(), request.contentType()).toString(),
            200, "Presigned URL 발급 성공");
    }

    @GetMapping("/images/{fileName}")
    public ResponseEntity<byte[]> getImage(@PathVariable String fileName) {
        byte[] imageBytes = s3Service.getImageBytes(fileName);
        MediaType mediaType = getMediaType(fileName);
        return ResponseEntity.ok().contentType(mediaType).body(imageBytes);
    }

    private MediaType getMediaType(String fileName) {
        String ext = fileName.substring(fileName.lastIndexOf('.') + 1).toLowerCase();
        return switch (ext) {
            case "jpg", "jpeg" -> MediaType.IMAGE_JPEG;
            case "png" -> MediaType.IMAGE_PNG;
            case "webp" -> MediaType.valueOf("image/webp");
            default -> MediaType.APPLICATION_OCTET_STREAM;
        };
    }
}
