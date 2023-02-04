package com.example.demo.services.impl;

import com.example.demo.services.FileIngrService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class FileIngrServiceImpl implements FileIngrService {
    @Value("${path.to.date.ingredients.file}")
    private String dataFileIngredientPath;

    @Value("${name.of.data.ingredients.file}")
    private String dataFileIngredientName;

    @Override
    public boolean saveToIngrFile(String json){
        try {
            Files.writeString(Path.of(dataFileIngredientPath,dataFileIngredientName),json);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

    }

    @Override
    public String readFromIngrFile(){
        try {
           return Files.readString(Path.of(dataFileIngredientPath,dataFileIngredientName));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public boolean cleanFile(){
        try {
            Files.deleteIfExists(Path.of(dataFileIngredientPath,dataFileIngredientName));
            Files.createFile(Path.of(dataFileIngredientPath,dataFileIngredientName));
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public File getDataIngrFile() {
        return new File(dataFileIngredientPath+ "/" + dataFileIngredientName);
    }
}
