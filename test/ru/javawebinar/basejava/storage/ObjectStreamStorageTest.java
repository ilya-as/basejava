package ru.javawebinar.basejava.storage;

public class ObjectStreamStorageTest extends AbstractStorageTest {
    public ObjectStreamStorageTest() {
        super(new AbstractFileStorage(STORAGE_DIR, new ObjectStreamStorage()) {
        });
    }
}
