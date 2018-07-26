package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapStorageResume extends AbstractStorage {
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
    public void clearElement(Object resume) {
        storageMap.remove(((Resume) resume).getUuid());
    }

    @Override
    protected Resume getKey(String uuid) {
        return storageMap.get(uuid);
    }

    @Override
    public Resume getElement(Object resume) {
        return (Resume) resume;
    }

    @Override
    public void saveElement(Resume resume, Object key) {
        storageMap.put(resume.getUuid(), resume);
    }

    @Override
    protected void updateElement(Resume resume, Object key) {
        saveElement(resume, key);
    }

    @Override
    protected boolean isExistElement(Object resume) {
        return resume != null;
    }
}
