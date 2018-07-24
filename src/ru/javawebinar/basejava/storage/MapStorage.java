package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.*;

public class MapStorage extends AbstractStorage {
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
    public void clearElement(Object key) {
        storageMap.remove((String) key);
    }

    @Override
    protected String getKey(String uuid) {
        return uuid;
    }

    @Override
    public Resume getElement(Object key) {
        return storageMap.get((String) key);
    }

    @Override
    public void saveElement(Resume resume, Object key) {
        storageMap.put((String) key, resume);
    }

    @Override
    protected void updateElement(Resume resume, Object key) {
        saveElement(resume, key);
    }

    @Override
    protected boolean existElement(Object key) {
        return storageMap.containsKey((String) key);
    }
}
