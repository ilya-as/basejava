/*
 * Array based storage for Resumes
 */

import java.util.Arrays;

public class ArrayStorage {
    private Resume[] storage = new Resume[10000];

    void clear() {
        Arrays.fill(storage, null);
    }

    void save(Resume resume) {
        for (int i = 0; i < storage.length; i++) {
            if (storage[i] == null) {
                storage[i] = resume;
                break;
            }
        }
    }

    Resume get(String uuid) {
        for (int j = 0; j < size(); j++) {
            if (storage[j].uuid.equals(uuid)) {
                return storage[j];
            }
        }
        return new Resume();
    }

    void delete(String uuid) {
        int currentSize = size();
        int i = 0;
        for (int j = 0; j < currentSize; j++) {
            if (!storage[j].uuid.equals(uuid)) {
                storage[i++] = storage[j];
            }
        }

        while (i < storage.length) {
            storage[i++] = null;
        }
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    Resume[] getAll() {
        int currentSize = size();
        Resume[] dataStorage = new Resume[currentSize];
        System.arraycopy(storage, 0, dataStorage, 0, currentSize);
        return dataStorage;
    }

    int size() {
        int j = 0;
        for (int i = 0; i < storage.length; i++) {
            if (storage[i] == null) {
                j = i;
                break;
            }
        }
        return j;
    }
}
