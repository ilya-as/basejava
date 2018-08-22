package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.storage.serializer.ObjectStreamStorage;

public class ObjectStreamStorageTest extends AbstractStorageTest {
    public ObjectStreamStorageTest() {
        super(new FileStorage(STORAGE_DIR, new ObjectStreamStorage()) {
        });
    }
}
