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
    public void clearElement(Object index) {
    }

    @Override
    protected Integer getKey(String uuid) {
        return -1;
    }


    @Override
    public Resume getElement(Object index) {
        return new Resume();
    }

    @Override
    public void saveElement(Resume resume, Object index) {
    }

    @Override
    protected void updateElement(Resume resume, Object index) {
    }

    @Override
    protected boolean existElement(Object key) {
        return false;
    }
}
