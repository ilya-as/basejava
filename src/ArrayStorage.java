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
   	    int index = -1;
		for (int j = 0; j < size; j++) {
			if (!storage[j].uuid.equals(uuid)) {
				index++;
				storage[index] = storage[j];
			}
		}
		if (index >= 0) {
		    System.arraycopy(storage, 0, storage, 0, size--);
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
