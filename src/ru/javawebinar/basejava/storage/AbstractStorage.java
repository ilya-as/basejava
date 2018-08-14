package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.model.Resume;

import java.util.Collections;
import java.util.List;

public abstract class AbstractStorage<SK> implements Storage {

    @Override
    public void update(Resume resume) {
        SK key = getExistedKey(resume.getUuid());
        updateElement(resume, key);
    }

    @Override
    public Resume get(String uuid) {
        SK key = getExistedKey(uuid);
        return getElement(key);
    }

    @Override
    public void save(Resume resume) {
        SK key = getNotExistedKey(resume.getUuid());
        saveElement(resume, key);
    }

    @Override
    public void delete(String uuid) {
        SK key = getExistedKey(uuid);
        clearElement(key);
    }

    protected SK getExistedKey(String uuid) {
        SK key = getKey(uuid);
        if (!isExistElement(key)) {
            throw new NotExistStorageException(uuid);
        }
        return key;
    }

    protected SK getNotExistedKey(String uuid) {
        SK key = getKey(uuid);
        if (isExistElement(key)) {
            throw new ExistStorageException(uuid);
        }
        return key;
    }

    @Override
    public List<Resume> getAllSorted() {
        List<Resume> storageList = getAll();
        Collections.sort(storageList);
        return storageList;
    }

    protected abstract List<Resume> getAll();

    protected abstract boolean isExistElement(SK key);

    protected abstract void clearElement(SK key);

    protected abstract SK getKey(String uuid);

    protected abstract void updateElement(Resume resume, SK index);

    protected abstract Resume getElement(SK key);

    protected abstract void saveElement(Resume resume, SK key);
}