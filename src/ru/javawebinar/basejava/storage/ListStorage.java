package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.ArrayList;
import java.util.List;

public class ListStorage extends ArrayStorage {

    protected List<Resume> storageList = new ArrayList<>();

    @Override
    public int size() {
        return storageList.size();
    }

    @Override
    public void clear() {
        storageList.clear();
    }

    @Override
    public List<Resume> getAll() {
        return new ArrayList<>(storageList);
    }

    @Override
    public void clearElement(Object index) {
        storageList.remove(((Integer) index).intValue());
    }

    @Override
    protected Integer getKey(String uuid) {
        for (int i = 0; i < storageList.size(); i++) {
            if (storageList.get(i).getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public void updateElement(Resume resume, Object index) {
        storageList.set((Integer) index, resume);
    }

    @Override
    public Resume getElement(Object index) {
        return storageList.get((Integer) index);
    }

    @Override
    public void saveElement(Resume resume, Object index) {
        storageList.add(resume);
    }
}
