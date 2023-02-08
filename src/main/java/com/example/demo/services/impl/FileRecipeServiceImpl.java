package com.example.demo.services.impl;

import com.example.demo.services.FileRecipeService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class FileRecipeServiceImpl implements FileRecipeService {

    @Value("${path.to.date.recipe.file}")
    private String dataFileRecipePath;

    @Value("${name.of.data.recipe.file}")
    private String dataFileRecipeName;


    @Override
    public boolean saveToRecipeFile(String json) {
        if (!getDataRecipeFile().exists()) {
            try {
                Files.createFile(Path.of(dataFileRecipePath,dataFileRecipeName));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        try {
            cleanFile();
            Files.writeString(Path.of(dataFileRecipePath, dataFileRecipeName), json);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public String readFromRecipeFile() {
        try {
            if (Files.exists(Path.of(dataFileRecipePath, dataFileRecipeName))) {
                return Files.readString(Path.of(dataFileRecipePath, dataFileRecipeName));
            } else {return "Файла нет";}
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean cleanFile() {
        try {
            Path path = Path.of(dataFileRecipePath, dataFileRecipeName);
            Files.deleteIfExists(path);
            Files.createFile(path);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public File getDataRecipeFile() {
        return new File(dataFileRecipePath + "/" + dataFileRecipeName);
    }

    @Override
    public ResponseEntity<InputStreamResource> uploadAllRecipe() {
        File dataRecipeFile = getDataRecipeFile();
        if (dataRecipeFile.exists()) {
            try {
                InputStreamResource inputStreamResource = new InputStreamResource(
                        new FileInputStream(dataRecipeFile));
                return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=\"RecipeLong.json\"").
                        contentLength(dataRecipeFile.length()).
                        contentType(MediaType.APPLICATION_OCTET_STREAM).
                        body(inputStreamResource);

            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
        return ResponseEntity.noContent().build();
    }

//    @Override
//    public Path creatTempFile(String suffix) {
//        try {
//            return Files.createTempFile(Path.of(dataFileRecipePath), "tempFile", suffix);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//    }

}
