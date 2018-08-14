package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;
import ru.javawebinar.basejava.storage.AbstractStorage;

import java.util.*;

public class MapStorage extends AbstractStorage<String> {
    protected Map<String, Resume> storageMap = new HashMap<>();

    @Override
    public int size() {
        return storageMap.size();
    }

    @Override
    public void clear() {
        storageMap.clear();
    }

    @Override
    public List<Resume> getAll() {
        return new ArrayList<>(storageMap.values());
    }

    @Override
    public void clearElement(String key) {
        storageMap.remove(key);
    }

    @Override
    protected String getKey(String uuid) {
        return uuid;
    }

    @Override
    public Resume getElement(String key) {
        return storageMap.get(key);
    }

    @Override
    public void saveElement(Resume resume, String key) {
        storageMap.put(key, resume);
    }

    @Override
    protected void updateElement(Resume resume, String key) {
        saveElement(resume, key);
    }

    @Override
    protected boolean isExistElement(String key) {
        return storageMap.containsKey(key);
    }
}