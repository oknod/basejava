package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.ArrayList;
import java.util.List;

public class ListStorage extends AbstractStorage {
    private final List<Resume> list = new ArrayList<>();

    @Override
    public void clear() {
        list.clear();
    }

    @Override
    protected void saveResume(Resume r, Object searchKey) {
        list.add(r);
    }

    @Override
    protected void updateResume(Resume r, Object searchKey) {
        list.set((Integer) searchKey, r);
    }

    @Override
    protected void removeResume(Object searchKey) {
        list.remove(((Integer) searchKey).intValue());
    }

    @Override
    protected Resume getResume(Object searchKey) {
        return list.get((Integer) searchKey);
    }

    @Override
    public Resume[] getAll() {
        return list.toArray(new Resume[0]);
    }

    @Override
    public int size() {
        return list.size();
    }

    @Override
    protected Integer getSearchKey(String uuid) {
        for (Resume r : list) {
            if (r.getUuid().equals(uuid)) {
                return list.indexOf(r);
            }
        }
        return null;
    }

    @Override
    protected boolean isExist(Object searchKey) {
        return searchKey != null;
    }


}
