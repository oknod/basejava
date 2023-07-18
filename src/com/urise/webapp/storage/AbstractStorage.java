package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;

public abstract class AbstractStorage implements Storage {

    protected abstract Object getSearchKey(String uuid);

    protected abstract boolean relevantKey(Object searchKey);

    protected abstract void saveResume(Resume r, Object searchKey);

    protected abstract void updateResume(Resume r, Object searchKey);

    protected abstract void removeResume(Object searchKey);

    protected abstract Resume getResume(Object searchKey);

    public final void save(Resume r) {
        Object searchKey = getSearchKey(r.getUuid());
        if (relevantKey(searchKey)) throw new ExistStorageException(r.getUuid());
        saveResume(r, searchKey);
    }

    public final void update(Resume r) {
        Object searchKey = getRelevantKey(r.getUuid());
        updateResume(r, searchKey);
    }

    public final void delete(String uuid) {
        Object searchKey = getRelevantKey(uuid);
        removeResume(searchKey);
    }

    public final Resume get(String uuid) {
        Object searchKey = getRelevantKey(uuid);
        return getResume(searchKey);
    }

    private Object getRelevantKey(String uuid) {
        Object searchKey = getSearchKey(uuid);
        if (!relevantKey(searchKey)) {
            throw new NotExistStorageException(uuid);
        }
        return searchKey;
    }

}



