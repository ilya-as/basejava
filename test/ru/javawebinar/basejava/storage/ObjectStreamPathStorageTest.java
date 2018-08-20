package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.iotools.ObjectStreamStorage;

public class ObjectStreamPathStorageTest extends AbstractStorageTest {
    public ObjectStreamPathStorageTest() {
        super(new PathStorage(STORAGE_DIR.getAbsolutePath(), new ObjectStreamStorage()) {
        });
    }
}