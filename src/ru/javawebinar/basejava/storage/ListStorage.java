package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;
import ru.javawebinar.basejava.storage.AbstractStorage;

import java.util.ArrayList;
import java.util.List;

public class ListStorage extends AbstractStorage<Integer> {

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
    public void clearElement(Integer index) {
        storageList.remove(index.intValue());
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
    public void updateElement(Resume resume, Integer index) {
        storageList.set(index, resume);
    }

    @Override
    public Resume getElement(Integer index) {
        return storageList.get(index);
    }

    @Override
    public void saveElement(Resume resume, Integer index) {
        storageList.add(resume);
    }

    @Override
    protected boolean isExistElement(Integer key) {
        return (key) != -1;
    }
}