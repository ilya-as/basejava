package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.storage.serializer.ReaderWriterObject;
import ru.javawebinar.basejava.model.Resume;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class PathStorage extends AbstractStorage<Path> {
    private Path directory;
    private ReaderWriterObject readerWriterObject;

    protected PathStorage(String dir, ReaderWriterObject readerWriterObject) {
        directory = Paths.get(dir);
        this.readerWriterObject = readerWriterObject;
        Objects.requireNonNull(dir, "directory must not be null");
        if (!Files.isDirectory(directory) || !Files.isWritable(directory)) {
            throw new IllegalArgumentException(dir + " is not directory or is not writable");
        }
    }

    @Override
    public void clear() {
        try {
            Files.list(directory).forEach(this::clearElement);
        } catch (IOException e) {
            throw new StorageException("Path delete error", null);
        }
    }

    @Override
    public int size() {
        try {
            return (int) Files.list(directory).count();
        } catch (IOException e) {
            throw new StorageException("Directory read error", null, e);
        }
    }

    @Override
    protected Path getKey(String uuid) {
        return directory.resolve(uuid);
    }

    @Override
    protected void updateElement(Resume r, Path path) {
        try {
            readerWriterObject.doWrite(r, new BufferedOutputStream(Files.newOutputStream(path)));
        } catch (IOException e) {
            throw new StorageException("Path update error", r.getUuid(), e);
        }
    }

    @Override
    protected boolean isExistElement(Path path) {
        return Files.isRegularFile(path);
    }

    @Override
    protected void saveElement(Resume r, Path path) {
        try {
            Files.createFile(path);
        } catch (IOException e) {
            throw new StorageException("Couldn't create Path " + path, path.toString(), e);
        }
        updateElement(r, path);
    }

    @Override
    protected Resume getElement(Path path) {
        try {
            return readerWriterObject.doRead(new BufferedInputStream(Files.newInputStream(path)));
        } catch (IOException e) {
            throw new StorageException("Path read error", path.toString(), e);
        }
    }

    @Override
    protected void clearElement(Path path) {
        try {
            Files.delete(path);
        } catch (IOException e) {
            throw new StorageException("Path delete error", path.toString(), e);
        }
    }

    @Override
    protected List<Resume> getAll() {
        try {
            return Files.list(directory).map(this::getElement).collect(Collectors.toList());
        } catch (IOException e) {
            throw new StorageException("Directory read error", null, e);
        }
    }
}
