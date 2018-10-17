package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.sql.SQLException;
import java.util.List;

public interface Storage {

    void clear();

    void update(Resume resume);

    void save(Resume resume);

    Resume get(String uuid);

    void delete(String uuid);

    List<Resume> getAllSorted();

    int size();
}