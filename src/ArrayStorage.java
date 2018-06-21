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
		int index = getIndex(resume.uuid);
		if ((size + 1) == storage.length) {
			System.out.println("storage overfull");
		}
		else if (index == -1) {
			storage[size] = resume;
			size++;
			}
		else {
			System.out.println("resume is exist");
		}
	}

	void update(Resume resume) {
		int index = getIndex(resume.uuid);
		if (index != -1)
			storage[index]=resume;
		else
			System.out.println("storage dosn't contains resume with uuid: " + resume.uuid);
	}

	Resume get(String uuid) {
		int index = getIndex(uuid);
		if (index != -1)
			return storage[index];
		else
			System.out.println("storage dosn't contains resume with uuid: " + uuid);
		return null;
	}

	void delete(String uuid) {
		int index = getIndex(uuid);
		if (index != -1) {
			storage[index] = storage[size - 1];
			storage[size - 1] = null;
			size--;
			}
		else
			System.out.println("storage dosn't contains resume with uuid: " + uuid);
	}

	int getIndex(String uuid) {
		int index = -1;
		for (int i = 0; i < size; i++) {
			if (storage[i].uuid.equals(uuid)) {
				return i;
			}
		}
		return index;
	}

	/**
	 * @return array, contains only Resumes in storage (without null)
	 */
	Resume[] getAll() {
		return Arrays.copyOf(storage, size);
	}

	int size() {
		return size;
	}
}
