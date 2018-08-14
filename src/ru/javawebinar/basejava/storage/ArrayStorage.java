package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

public class ArrayStorage extends AbstractArrayStorage {

    @Override
    protected Integer getKey(String uuid) {
        for (int i = 0; i < size; i++) {
            if (uuid.equals(storage[i].getUuid())) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public void insert(Resume resume, int index) {
        storage[size] = resume;
    }

    @Override
    public void removeElement(int index) {
        storage[index] = storage[size - 1];
    }
}