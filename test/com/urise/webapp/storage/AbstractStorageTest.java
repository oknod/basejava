package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

public abstract class AbstractStorageTest {

    protected Storage storage;

    protected AbstractStorageTest(Storage storage) {
        this.storage = storage;
    }

    private static final String UUID_1 = "uuid1";
    private static final Resume R1;
    private static final String UUID_2 = "uuid2";
    private static final Resume R2;
    private static final String UUID_3 = "uuid3";
    private static final Resume R3;
    private static final String UUID_4 = "uuid4";
    private static final Resume R4;
    private static final String UUID_NOT_EXIST = "";

    static {
        R1 = new Resume(UUID_1, "Name1");
        R2 = new Resume(UUID_2, "Name2");
        R3 = new Resume(UUID_3, "Name3");
        R4 = new Resume(UUID_4, "Name4");
    }

    @BeforeEach
    public void setUp() {
        storage.clear();
        storage.save(R1);
        storage.save(R2);
        storage.save(R3);
    }

    @Test
    public void size() {
        assertSize(3);
    }

    @Test
    public void clear() {
        Resume [] arr = {};
        storage.clear();
        assertSize(0);
        Assertions.assertArrayEquals(arr, storage.getAllSorted().toArray());
    }

    @Test
    public void save() {
        storage.save(R4);
        assertSize(4);
        assertGet(R4);
    }

    @Test
    public void saveExist() {
        Assertions.assertThrows(ExistStorageException.class, () -> storage.save(R1));
    }

    @Test
    public void update() {
        Resume updateResume = new Resume(UUID_1, "new name");
        storage.update(updateResume);
        Assertions.assertEquals(updateResume, storage.get(UUID_1));
    }

    @Test
    public void updateNotExist() {
        Assertions.assertThrows(NotExistStorageException.class, () -> storage.update(R4));
    }

    @Test
    public void delete() {
        Assertions.assertThrows(NotExistStorageException.class, () -> {
            storage.delete(UUID_3);
            assertSize(2);
            storage.get(UUID_3);
        });
    }

    @Test
    public void deleteNotExist() {
        Assertions.assertThrows(NotExistStorageException.class, () -> storage.delete(UUID_NOT_EXIST));
    }

    @Test
    public void getAllSorted() {
        List<Resume> list = storage.getAllSorted();
        Assertions.assertEquals(list, Arrays.asList(R1, R2, R3));
    }

    @Test
    public void get() {
        assertGet(R1);
    }

    @Test
    public void getNotExist() {
        Assertions.assertThrows(NotExistStorageException.class, () -> storage.get(UUID_NOT_EXIST));
    }

    private void assertSize(int size) {
        Assertions.assertEquals(size, storage.size());
    }

    private void assertGet(Resume resume) {
        Assertions.assertEquals(resume, storage.get(resume.getUuid()));
    }
}