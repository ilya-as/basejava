package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.HashMap;
import java.util.Map;

public class MapStorage extends AbstractStorage {
    protected Map<String, Resume> storageMap = new HashMap<>();

    @Override
    public int size() {
        return 0;
    }

    @Override
    public void clear() {
    }

    @Override
    public Resume[] getAll() {
        return null;
    }

    @Override
    public void clearElement(int index) {
    }

    @Override
    protected int getIndex(String uuid) {
        return -1;
    }

    @Override
    public void insert(Resume resume, int index) {
    }

    @Override
    public Resume getElement(int index) {
        return new Resume();
    }

    @Override
    public void saveElement(Resume resume, int index) {
    }
}
