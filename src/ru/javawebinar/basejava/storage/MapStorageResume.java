package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;
import ru.javawebinar.basejava.storage.AbstractStorage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapStorageResume extends AbstractStorage<Resume> {
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
    public void clearElement(Resume resume) {
        storageMap.remove(resume.getUuid());
    }

    @Override
    protected Resume getKey(String uuid) {
        return storageMap.get(uuid);
    }

    @Override
    public Resume getElement(Resume resume) {
        return resume;
    }

    @Override
    public void saveElement(Resume resume, Resume key) {
        storageMap.put(resume.getUuid(), resume);
    }

    @Override
    protected void updateElement(Resume resume, Resume key) {
        saveElement(resume, key);
    }

    @Override
    protected boolean isExistElement(Resume resume) {
        return resume != null;
    }
}