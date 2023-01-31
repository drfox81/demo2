package com.example.demo.services;

public interface FileIngrService {
    boolean saveToIngrFile(String json);

    String readFromIngrFile();

    boolean cleanFile();
}
