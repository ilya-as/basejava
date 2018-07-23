package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.model.Resume;

public abstract class AbstractStorage implements Storage {

    @Override
    public void update(Resume resume) {
        int index = getExisted(resume.getUuid());
        insert(resume, index);
    }

    @Override
    public Resume get(String uuid) {
        int index = getExisted(uuid);
        return getElement(index);
    }

    @Override
    public void save(Resume resume) {
        int index = getNotExisted(resume.getUuid());
        saveElement(resume, index);
    }

    @Override
    public void delete(String uuid) {
        int index = getExisted(uuid);
        clearElement(index);
    }

    protected int getExisted(String uuid) {
        int index = getIndex(uuid);
        if (index < 0) {
            throw new NotExistStorageException(uuid);
        }
        return index;
    }

    protected int getNotExisted(String uuid) {
        int index = getIndex(uuid);
        if (index >= 0) {
            throw new ExistStorageException(uuid);
        }
        return index;
    }

    protected abstract void clearElement(int index);

    protected abstract int getIndex(String uuid);

    protected abstract void insert(Resume resume, int index);

    protected abstract Resume getElement(int index);

    protected abstract void saveElement(Resume resume, int index);
}
