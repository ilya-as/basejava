package ru.javawebinar.basejava.storage;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        ArrayStorageTest.class,
        SortedArrayStorageTest.class,
        ListStorageTest.class,
        MapStorageTest.class,
        MapStorageResumeTest.class,
        ObjectStreamStorageTest.class,
        ObjectStreamPathStorageTest.class,
       // XmlPathStorageTest.class,
        JsonPathStorageTest.class,
        DataPathStorageTest.class
})
public class RunAllTests {
}
