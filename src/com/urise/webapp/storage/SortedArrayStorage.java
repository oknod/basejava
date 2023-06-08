package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {

    @Override
    protected int getIndex(String uuid) {
        Resume searchKey = new Resume();
        searchKey.setUuid(uuid);
        return Arrays.binarySearch(storage, 0, size, searchKey);
    }

    @Override
    protected void insertElm(Resume r, int index) {
        int nElm = -index - 1;
        System.arraycopy(storage, nElm, storage, nElm + 1, size - nElm);
        storage[nElm] = r;
    }

    @Override
    protected void deleteElm(int index) {
        int nLng = size - index - 1;
        System.arraycopy(storage, index + 1, storage, index, nLng);
    }

}
