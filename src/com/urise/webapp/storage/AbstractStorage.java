package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;

import java.util.Collections;
import java.util.List;

public abstract class AbstractStorage implements Storage {

    protected abstract Object getSearchKey(String uuid);

    protected abstract boolean isExist(Object searchKey);

    protected abstract void saveResume(Resume r, Object searchKey);

    protected abstract void updateResume(Resume r, Object searchKey);

    protected abstract void removeResume(Object searchKey);

    protected abstract Resume getResume(Object searchKey);

    protected abstract List<Resume> getAll();

    public final void save(Resume r) {
        Object searchKey = getNotExistingSearchKey(r.getUuid());
        saveResume(r, searchKey);
    }

    public final void update(Resume r) {
        Object searchKey = getExistingSearchKey(r.getUuid());
        updateResume(r, searchKey);
    }

    public final void delete(String uuid) {
        Object searchKey = getExistingSearchKey(uuid);
        removeResume(searchKey);
    }

    public final Resume get(String uuid) {
        Object searchKey = getExistingSearchKey(uuid);
        return getResume(searchKey);
    }

    @Override
    public List<Resume> getAllSorted() {
        List<Resume> list = getAll();
        Collections.sort(list);
        return list;
    }

    private Object getExistingSearchKey(String uuid) {
        Object searchKey = getSearchKey(uuid);
        if (!isExist(searchKey)) {
            throw new NotExistStorageException(uuid);
        }
        return searchKey;
    }

    private Object getNotExistingSearchKey(String uuid) {
        Object searchKey = getSearchKey(uuid);
        if (isExist(searchKey)) {
            throw new ExistStorageException(uuid);
        }
        return searchKey;
    }

}



