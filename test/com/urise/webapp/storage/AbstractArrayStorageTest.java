package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public abstract class AbstractArrayStorageTest {

    private final Storage storage;

    protected AbstractArrayStorageTest(Storage storage) {
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

    @BeforeEach
    void setUp() {
        storage.clear();
        storage.save(R1);
        storage.save(R2);
        storage.save(R3);
    }

    @Test
    public void size() {
        Assertions.assertEquals(3, storage.size());
    }

    @Test
    public void clear() {
        storage.clear();
        Assertions.assertEquals(0, storage.size());
    }

    @Test
    public void save() {
        storage.save(R4);
        Assertions.assertEquals(4, storage.size());
    }

    @Test
    public void saveExist() {
        Assertions.assertThrows(ExistStorageException.class, () -> storage.save(R1));
    }

    @Test
    public void saveOverflow() {
        Assertions.assertThrows(StorageException.class, () -> {
            for (int i = 4; i <= AbstractArrayStorage.STORAGE_LIMIT; i++) {
                storage.save(new Resume());
            }
            storage.save(new Resume());
        });
    }


    @Test
    public void update() {
        Resume updateResume = new Resume(UUID_1);
        storage.update(updateResume);
        Assumptions.assumeTrue(updateResume.equals(storage.get(UUID_1)));
    }

    @Test
    public void updateNotExist() {
        Assertions.assertThrows(NotExistStorageException.class, () -> storage.update(R4));
    }

    @Test
    public void delete() {
        Assertions.assertThrows(NotExistStorageException.class, () -> {
            storage.delete(UUID_3);
            Assertions.assertEquals(2, storage.size());
            storage.get(UUID_3);
        });
    }

    @Test
    public void deleteNotExist() {
        Assertions.assertThrows(NotExistStorageException.class, () -> storage.delete("dummy"));
    }

    @Test
    void getAll() {
        Resume[] arr = storage.getAll();
        Assertions.assertEquals(3, arr.length);
        Assertions.assertEquals(R1, arr[0]);
        Assertions.assertEquals(R2, arr[1]);
        Assertions.assertEquals(R3, arr[2]);
    }

    @Test
    void get() {
        storage.get(R1.getUuid());
    }

    @Test
    void getNotExist() {
        Assertions.assertThrows(NotExistStorageException.class, () -> storage.get("dummy"));
    }
}