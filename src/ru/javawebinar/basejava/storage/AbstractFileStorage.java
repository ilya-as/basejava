package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.Resume;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class AbstractFileStorage extends AbstractStorage<File> {
    private File directory;
    private ReaderWriterObject readerWriterObject;

    protected AbstractFileStorage(File directory, ReaderWriterObject readerWriterObject) {
        Objects.requireNonNull(directory, "directory must not be null");
        if (!directory.isDirectory()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + " is not directory");
        }
        if (!directory.canRead() || !directory.canWrite()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + " is not readable/writable");
        }
        this.directory = directory;
        this.readerWriterObject = readerWriterObject;
    }

    @Override
    public void clear() {
        File files[] = directory.listFiles();
        if (files == null) {
            throw new StorageException("directory is empty", null);
        }
        for (File file : files) {
            clearElement(file);
        }
    }

    @Override
    public int size() {
        if (directory.list() == null) {
            throw new StorageException("Read directory error", null);
        }
        return directory.list().length;
    }

    @Override
    protected File getKey(String uuid) {
        return new File(directory, uuid);
    }

    @Override
    protected void updateElement(Resume r, File file) {
        try {
            readerWriterObject.doWrite(r, new BufferedOutputStream(new FileOutputStream(file)));
        } catch (IOException e) {
            throw new StorageException("Write error", r.getUuid(), e);
        }
    }

    @Override
    protected boolean isExistElement(File file) {
        return file.exists();
    }

    @Override
    protected void saveElement(Resume r, File file) {
        try {
            file.createNewFile();
        } catch (IOException e) {
            throw new StorageException("IO error", file.getName(), e);
        }
        updateElement(r, file);
    }

    @Override
    protected Resume getElement(File file) {
        try {
            return readerWriterObject.doRead(new BufferedInputStream(new FileInputStream(file)));
        } catch (IOException e) {
            throw new StorageException("File read error", file.getName(), e);
        }
    }

    @Override
    protected void clearElement(File file) {
        if (!file.delete()) {
            throw new StorageException("Delete error", file.getName());
        }
    }

    @Override
    protected List<Resume> getAll() {
        List<Resume> resumeList = new ArrayList<>(size());
        File files[] = directory.listFiles();
        if (directory.list() == null) {
            throw new StorageException("Read directory error", null);
        }
        for (File file : files) {
            resumeList.add(getElement(file));
        }
        return resumeList;
    }
}
