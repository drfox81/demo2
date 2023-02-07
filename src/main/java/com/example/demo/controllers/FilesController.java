package com.example.demo.controllers;

import com.example.demo.services.FileIngrService;
import com.example.demo.services.FileRecipeService;
import org.apache.commons.io.IOUtils;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;

@RestController
@RequestMapping("/files")
public class FilesController {

    private final FileRecipeService fileRecipeService;
    private final FileIngrService fileIngrService;

    public FilesController(FileRecipeService fileRecipeService, FileIngrService fileIngrService) {
        this.fileRecipeService = fileRecipeService;
        this.fileIngrService = fileIngrService;
    }


    @GetMapping("/export")
    public ResponseEntity<InputStreamResource> uploadAllRecipe() {
        if (fileRecipeService.getDataRecipeFile().exists()) {
            return fileRecipeService.uploadAllRecipe();
        } else {
            return ResponseEntity.notFound().build();
        }
//        File dataRecipeFile = fileRecipeService.getDataRecipeFile();
//        if (dataRecipeFile.exists()) {
//            try {
//                InputStreamResource inputStreamResource = new InputStreamResource(
//                        new FileInputStream(dataRecipeFile));
//                return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=\"RecipeLong.json\"").
//                        contentLength(dataRecipeFile.length()).
//                        contentType(MediaType.APPLICATION_OCTET_STREAM).
//                        body(inputStreamResource);
//
//            } catch (FileNotFoundException e) {
//                throw new RuntimeException(e);
//            }
//        }
//        return ResponseEntity.noContent().build();
    }

    @PostMapping(value = "/import", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> uploadRecipeDataFile(@RequestParam MultipartFile file) {
        fileRecipeService.cleanFile();
        File dataFile = fileRecipeService.getDataRecipeFile();
        try (FileOutputStream fos = new FileOutputStream(dataFile)) {
            IOUtils.copy(file.getInputStream(), fos);
            return ResponseEntity.ok().build();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();

    }

    @GetMapping("/exportIngr")
    public ResponseEntity<InputStreamResource> uploadAllIngr() {
        File dataIngrFile = fileIngrService.getDataIngrFile();
        if (dataIngrFile.exists()) {
            try {
                InputStreamResource inputStreamResource = new InputStreamResource(
                        new FileInputStream(dataIngrFile));
                return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=\"IngredientLong.json\"").
                        contentLength(dataIngrFile.length()).
                        contentType(MediaType.APPLICATION_OCTET_STREAM).
                        body(inputStreamResource);

            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
        return ResponseEntity.noContent().build();
    }


    @PostMapping(value = "/importIngr", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> uploadIngrDataFile(@RequestParam MultipartFile fileIngr) {
        fileIngrService.cleanFile();
        File dataFile = fileIngrService.getDataIngrFile();
        try (FileOutputStream fos = new FileOutputStream(dataFile)) {
            IOUtils.copy(fileIngr.getInputStream(), fos);
            return ResponseEntity.ok().build();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();

    }
}
