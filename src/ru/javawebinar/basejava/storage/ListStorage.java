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
    public Resume[] getAll() {
        return storageList.toArray(new Resume[0]);
    }

    @Override
    public void clearElement(int index) {
        storageList.remove(index);
    }

    @Override
    protected int getIndex(String uuid) {
        for (int i = 0; i < storageList.size(); i++) {
            if (storageList.get(i).getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public void insert(Resume resume, int index) {
        storageList.set(index, resume);
    }

    @Override
    public Resume getElement(int index) {
        return storageList.get(index);
    }

    @Override
    public void saveElement(Resume resume, int index) {
        storageList.add(resume);
    }
}
