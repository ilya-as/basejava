
package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {

    @Override
    public void moveElemets(int index) {
        int copyLength = size - 1 - index;
        System.arraycopy(storage, index + 1, storage, index, copyLength);
        storage[index] = storage[size - 1];
    }

    @Override
    protected int getIndex(String uuid) {
        Resume searchKey = new Resume();
        searchKey.setUuid(uuid);
        return Arrays.binarySearch(storage, 0, size, searchKey);
    }

    @Override
    public void insert(Resume r) {
        storage[size] = r;
        size++;
       // Arrays.sort(storage);
    }
}

