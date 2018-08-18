package ru.javawebinar.basejava;

import java.io.File;

public class MainFile {
    public static void main(String[] args) {
        String filePath = "./src/ru/javawebinar/basejava/";
        File file = new File(filePath);
        System.out.println("Files list");
        displayAllFiles(file);
        System.out.println("Directory list");
        displayAllDirectory(file);
    }

    public static void displayAllFiles(File path) {
        if (path.isFile()) {
            System.out.println(path.getName());
        } else {
            File files[] = path.listFiles();
            for (File file : files) {
                displayAllFiles(file);
            }
        }
    }

    public static void displayAllDirectory(File path) {
        File files[] = path.listFiles();
        for (File file : files) {
            if (file.isDirectory()) {
                System.out.println(file.getName());
                displayAllDirectory(file);
            }
        }
    }
}


