package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.iotools.ObjectStreamStorage;

public class ObjectStreamStorageTest extends AbstractStorageTest {
    public ObjectStreamStorageTest() {
        super(new FileStorage(STORAGE_DIR, new ObjectStreamStorage()) {
        });
    }
}
