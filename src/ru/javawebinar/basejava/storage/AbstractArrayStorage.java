package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.Resume;

import java.util.Arrays;

public abstract class AbstractArrayStorage extends AbstractStorage {

    protected static final int STORAGE_LIMIT = 10000;
    protected Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int size = 0;

    @Override
    public int size() {
        return size;
    }

    @Override
    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    @Override
    public Resume[] getAll() {
        return Arrays.copyOf(storage, size);
    }

    @Override
    public Resume getElement(int index) {
        return storage[index];
    }

    @Override
    protected void saveElement(Resume resume, int index) {
        if (size == STORAGE_LIMIT) {
            throw new StorageException("Storage overflow", resume.getUuid());
        } else {
            insert(resume, index);
            size++;
        }
    }

    @Override
    protected void clearElement(int index) {
        removeElement(index);
        storage[size - 1] = null;
        size--;
    }

    protected abstract void insert(Resume resume, int index);

    protected abstract void removeElement(int index);

}