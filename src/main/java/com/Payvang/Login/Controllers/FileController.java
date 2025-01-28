package com.Payvang.Login.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.Payvang.Login.Services.FileStorageService;

@RestController
@RequestMapping("/api/files")
public class FileController {

	@Autowired
	private FileStorageService fileStorageService;

	@PostMapping("/upload")
	public ResponseEntity<String> uploadFile(@RequestParam("businessType") String businessType,
			@RequestParam("documentType") String documentType, @RequestParam("file") MultipartFile file,
			@RequestParam("appId") String appId) {

		try {
			 String fileName = file.getOriginalFilename();
		        if (fileName == null || fileName.isBlank()) {
		            throw new IllegalArgumentException("Uploaded file must have a valid name.");
		        }
			fileStorageService.validateAndSaveFile(businessType, documentType, file, fileName, appId);
			return ResponseEntity.status(HttpStatus.OK).body("File uploaded successfully");

		} catch (IllegalArgumentException e) {
			// Return error response with bad request status if validation fails
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());

		} catch (RuntimeException e) {
			// Return error response if any unexpected error occurs
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("An error occurred while uploading the file: " + e.getMessage());
		}
	}
}
