package com.example.demo.services.impl;

import com.example.demo.services.FileRecipeService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
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
            return Files.readString(Path.of(dataFileRecipePath, dataFileRecipeName));
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

}
