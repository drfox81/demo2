package com.example.demo.services;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;

public interface FileRecipeService {
    boolean saveToRecipeFile(String json);

    String readFromRecipeFile();

    boolean cleanFile();

    File getDataRecipeFile();

    //Path creatTempFile(String suffix);
}
