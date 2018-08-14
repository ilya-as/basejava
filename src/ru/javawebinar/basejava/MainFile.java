package ru.javawebinar.basejava;

import java.io.File;

public class MainFile {
    public static void main(String[] args) {
        String filePath = "./src/ru/javawebinar/basejava/";
        File file = new File(filePath);
        displayAll(file);
    }

    public static void displayAll(File path) {
        if (path.isFile()) {
            System.out.println(path.getName());
        } else {
            System.out.println(path.getName());
            File files[] = path.listFiles();
            for (File dirOrFile : files) {
                displayAll(dirOrFile);
            }
        }
    }
}
