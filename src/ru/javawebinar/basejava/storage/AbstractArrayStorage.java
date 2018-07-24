package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.Resume;

import java.util.Arrays;
import java.util.List;

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
    public List<Resume> getAll() {
        return Arrays.asList(Arrays.copyOf(storage, size));
    }

    @Override
    public Resume getElement(Object index) {
        return storage[(Integer) index];
    }

    @Override
    protected void saveElement(Resume resume, Object index) {
        if (size == STORAGE_LIMIT) {
            throw new StorageException("Storage overflow", resume.getUuid());
        } else {
            insert(resume, (Integer) index);
            size++;
        }
    }

    @Override
    protected void clearElement(Object index) {
        removeElement((Integer) index);
        storage[size - 1] = null;
        size--;
    }

    @Override
    protected boolean existElement(Object index) {
        return (Integer) index >= 0;
    }

    @Override
    protected void updateElement(Resume resume, Object index) {
        storage[(Integer) index] = resume;
    }

    protected abstract void insert(Resume resume, int index);

    protected abstract void removeElement(int index);

}