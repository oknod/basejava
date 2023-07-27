package com.urise.webapp.storage;

import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;

import java.util.Arrays;

public abstract class AbstractArrayStorage extends AbstractStorage {
    protected static final int STORAGE_LIMIT = 10000;
    protected final Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int size;

    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    @Override
    protected void saveResume(Resume r, Object index) {
        if (size == STORAGE_LIMIT) {
            throw new StorageException("Storage overflow", r.getUuid());
        } else {
            insertResume(r, (Integer) index);
            size++;
        }
    }

    @Override
    protected void updateResume(Resume r, Object index) {
        {
            storage[(Integer) index] = r;
        }
    }

    @Override
    protected void removeResume(Object index) {
        deleteResume((Integer) index);
        size--;
    }

    @Override
    protected Resume getResume(Object index) {
        return storage[(Integer) index];
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    public Resume[] getAll() {
        return Arrays.copyOfRange(storage, 0, size);
    }

    public int size() {
        return size;
    }

    @Override
    protected boolean isExist(Object index) {
        return (Integer) index >= 0;
    }

    protected abstract void insertResume(Resume r, int index);

    protected abstract void deleteResume(int index);

}
