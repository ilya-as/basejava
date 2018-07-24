package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.model.Resume;

import java.util.Collections;
import java.util.List;

public abstract class AbstractStorage implements Storage {

    @Override
    public void update(Resume resume) {
        Object key = getExisted(resume.getUuid());
        updateElement(resume, key);
    }

    @Override
    public Resume get(String uuid) {
        Object key = getExisted(uuid);
        return getElement(key);
    }

    @Override
    public void save(Resume resume) {
        Object key = getNotExisted(resume.getUuid());
        saveElement(resume, key);
    }

    @Override
    public void delete(String uuid) {
        Object key = getExisted(uuid);
        clearElement(key);
    }

    protected Object getExisted(String uuid) {
        Object key = getKey(uuid);
        if (!existElement(key)) {
            throw new NotExistStorageException(uuid);
        }
        return key;
    }

    protected Object getNotExisted(String uuid) {
        Object key = getKey(uuid);
        if (existElement(key)) {
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

    protected abstract boolean existElement(Object key);

    protected abstract void clearElement(Object key);

    protected abstract Object getKey(String uuid);

    protected abstract void updateElement(Resume resume, Object index);

    protected abstract Resume getElement(Object key);

    protected abstract void saveElement(Resume resume, Object key);
}
