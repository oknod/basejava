package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public abstract class AbstractStorageTest {

    private final Storage storage;

    protected AbstractStorageTest(Storage storage) {
        this.storage = storage;
    }

    private static final String UUID_1 = "uuid1";
    private static final Resume R1 = new Resume(UUID_1);
    private static final String UUID_2 = "uuid2";
    private static final Resume R2 = new Resume(UUID_2);
    private static final String UUID_3 = "uuid3";
    private static final Resume R3 = new Resume(UUID_3);
    private static final String UUID_4 = "uuid4";
    private static final Resume R4 = new Resume(UUID_4);
    private static final String UUID_NOT_EXIST = "";

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
        Resume[] array = {};
        storage.clear();
        assertSize(0);
        Assertions.assertArrayEquals(array, storage.getAll());
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
    public void saveOverflow() {
        storage.clear();
        try {
            for (int i = 0; i < AbstractArrayStorage.STORAGE_LIMIT; i++) {
                storage.save(new Resume());
            }
        } catch (StorageException e) {
            Assertions.fail("early Storage overflow");
        }
        Assertions.assertThrows(StorageException.class, () -> storage.save(new Resume()));
    }


    @Test
    public void update() {
        Resume updateResume = new Resume(UUID_1);
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
    public void getAll() {
        Resume[] array = {R1, R2, R3};
        Assertions.assertArrayEquals(array, storage.getAll());
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