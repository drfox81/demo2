package com.example.demo.services;

import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;

public interface FileRecipeService {
    boolean saveToRecipeFile(String json);

    String readFromRecipeFile();

    boolean cleanFile();

    File getDataRecipeFile();

    ResponseEntity<InputStreamResource> uploadAllRecipe();

    Path creatTempFile(String suffix);

    //Path creatTempFile(String suffix);
}
