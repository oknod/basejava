package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.*;

public class MapStorage extends AbstractStorage {
    private final Map<String, Resume> map = new LinkedHashMap<>();

    @Override
    protected String getSearchKey(String uuid) {
        return uuid;
    }

    @Override
    protected boolean isExist(Object searchKey) {
        return map.containsKey((String) searchKey);
    }

    @Override
    protected void saveResume(Resume r, Object searchKey) {
        map.put((String) searchKey, r);
    }

    @Override
    protected void updateResume(Resume r, Object searchKey) {
        map.replace((String) searchKey, r);
    }

    @Override
    protected void removeResume(Object searchKey) {
        map.remove((String) searchKey);
    }

    @Override
    protected Resume getResume(Object searchKey) {
        return map.get((String) searchKey);
    }

    @Override
    public void clear() {
        map.clear();
    }

    @Override
    public List<Resume> getAll() {
        return new ArrayList<>(map.values());
    }

    @Override
    public int size() {
        return map.size();
    }
}
