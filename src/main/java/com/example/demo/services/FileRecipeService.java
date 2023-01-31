package com.example.demo.services;

public interface FileRecipeService {
    boolean saveToRecipeFile(String json);

    String readFromRecipeFile();

    boolean cleanFile();
}
