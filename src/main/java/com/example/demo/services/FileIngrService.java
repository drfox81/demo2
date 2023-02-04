package com.example.demo.services;

import java.io.File;

public interface FileIngrService {
    boolean saveToIngrFile(String json);

    String readFromIngrFile();

    boolean cleanFile();

    File getDataIngrFile();
}
