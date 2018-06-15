/*
 * Array based storage for Resumes
 */

import java.util.Arrays;

public class ArrayStorage {
    private Resume[] storage = new Resume[10000];
    private int size = 0;

    void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    void save(Resume resume) {
        storage[size] = resume;
        size++;
    }

    Resume get(String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].uuid.equals(uuid)) {
                return storage[i];
            }
        }
        return null;
    }

    void delete(String uuid) {
   	   for (int i = 0; i < size; i++) {
		if (storage[i].uuid.equals(uuid)) {
		    System.arraycopy(storage, i+1, storage, i, size-i-1);
		    size--;
		}
	    }
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    Resume[] getAll() {
        return  Arrays.copyOf(storage, size);
    }

    int size() {
        return size;
    }
}
