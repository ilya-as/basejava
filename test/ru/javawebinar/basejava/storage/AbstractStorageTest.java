package ru.javawebinar.basejava.storage;

import org.junit.Before;
import org.junit.Test;
import ru.javawebinar.basejava.Config;
import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.model.*;

import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class AbstractStorageTest {

    public Storage storage;
    protected static final File STORAGE_DIR = Config.get().getStorageDir();
    // protected static final File STORAGE_DIR = new File("D:\\javaops\\basejava\\storage");
    // protected static final File STORAGE_DIR = new File("С:\\java\\javaops\\HW3\\storage");

    private static final String UUID_1 = "uuid1";
    private static final String UUID_2 = "uuid2";
    private static final String UUID_3 = "uuid3";
    private static final String UUID_4 = "uuid4";

    private static final Resume RESUME_1;
    private static final Resume RESUME_2;
    private static final Resume RESUME_3;
    private static final Resume RESUME_4;

    static {
        RESUME_1 = new Resume(UUID_1, "ivanov1");
        RESUME_2 = new Resume(UUID_2, "ivanov2");
        RESUME_3 = new Resume(UUID_3, "ivanov3");
        RESUME_4 = new Resume(UUID_4, "ivanov4");

        String descriptionExperience = "content organization";
        LocalDate dataFrom = LocalDate.of(2016, 2, 3);
        LocalDate dataTo = LocalDate.of(2018, 7, 4);
        Organization.Position positionList = new Organization.Position(dataFrom, dataTo, descriptionExperience, "java progammer");
        Organization organization = new Organization("Wrike", "wrike.com", positionList);
        List<Organization> organizationList = new ArrayList<>();
        organizationList.add(organization);
        Section experienceSection = new OrganizationSection(organizationList);

        String descriptionEducation = "content education";
        LocalDate educationFrom = LocalDate.of(2011, 6, 1);
        LocalDate educationTo = LocalDate.of(2016, 7, 31);
        Organization.Position positionEducationList = new Organization.Position(educationFrom, educationTo, descriptionEducation, "engineer");
        Organization education = new Organization("MGU", "mgu.com", positionEducationList);

        List<Organization> educationsList = new ArrayList<>();
        educationsList.add(education);
        Section educationSection = new OrganizationSection(educationsList);

        List<String> descriptionsAchievement = new ArrayList<>();
        descriptionsAchievement.add("content achievement 1");
        descriptionsAchievement.add("content achievement 2");
        Section listAchievement = new ListSection(descriptionsAchievement);

        List<String> descriptionsQualifications = new ArrayList<>();
        descriptionsQualifications.add("descriptionsQualifications1");
        descriptionsQualifications.add("descriptionsQualifications2");
        Section listQualifications = new ListSection(descriptionsQualifications);

        RESUME_1.setContact(ContactType.TELEPHONE, "333-22-11");
        RESUME_1.setContact(ContactType.SKYPE, "grigory.kislin");
        RESUME_1.setContact(ContactType.EMAIL, "gkislin@yandex.ru");
        RESUME_1.setContact(ContactType.LINDEDIN, "gkislin.linkedin.ru");
        RESUME_1.setContact(ContactType.GITHUB, "gkislin.github.ru");
        RESUME_1.setContact(ContactType.HOMEPAGE, "javaops.ru");
        RESUME_1.setContact(ContactType.STACKOVERFLOW, "gkislin.stackoverflow.ru");
        RESUME_2.setContact(ContactType.TELEPHONE, "222-222");
        RESUME_2.setContact(ContactType.SKYPE, "ivanov");
        RESUME_2.setContact(ContactType.EMAIL, "ivanov@yandex.ru");
        RESUME_3.setContact(ContactType.TELEPHONE, "333-333");
        RESUME_3.setContact(ContactType.SKYPE, "petrov");
        RESUME_3.setContact(ContactType.EMAIL, "ivanov3@yandex.ru");
        RESUME_1.setSection(SectionType.EXPERIENCE, experienceSection);
        RESUME_1.setSection(SectionType.EDUCATION, educationSection);
        RESUME_1.setSection(SectionType.ACHIEVEMENT, listAchievement);
        RESUME_1.setSection(SectionType.QUALIFICATIONS, listQualifications);
        RESUME_1.setSection(SectionType.PERSONAL, new TextSection("good in job"));
        RESUME_1.setSection(SectionType.OBJECTIVE, new TextSection("objective content"));
        RESUME_2.setSection(SectionType.PERSONAL, new TextSection("good in job"));
        RESUME_2.setSection(SectionType.OBJECTIVE, new TextSection("objective content"));
    }

    public AbstractStorageTest(Storage storage) {
        this.storage = storage;
    }

    @Before
    public void setUp() {
        storage.clear();
        storage.save(RESUME_1);
        storage.save(RESUME_2);
        storage.save(RESUME_3);
    }

    @Test
    public void size() {
        assertEquals(3, storage.size());
    }

    @Test
    public void clear() {
        storage.clear();
        assertEquals(0, storage.size());
    }

    @Test
    public void get() {
        assertEquals(RESUME_1, storage.get(UUID_1));
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExist() {
        storage.get(UUID_4);
    }

    @Test
    public void getAll() {
        List<Resume> storageTest = storage.getAllSorted();
        assertEquals(Arrays.asList(RESUME_1, RESUME_2, RESUME_3), storageTest);
    }

    @Test
    public void update() {
        Resume resume = new Resume(UUID_2, "ivanov5");
        storage.update(resume);
        assertEquals(resume, storage.get(UUID_2));
    }

    @Test(expected = NotExistStorageException.class)
    public void updateNotExist() {
        storage.update(RESUME_4);
    }

    @Test
    public void save() {
        storage.save(RESUME_4);
        assertEquals(RESUME_4, storage.get(UUID_4));
    }

    @Test(expected = ExistStorageException.class)
    public void saveExist() {
        storage.save(RESUME_1);
    }

    @Test
    public void delete() {
        storage.delete(UUID_1);
        assertEquals(2, storage.size());
    }

    @Test(expected = NotExistStorageException.class)
    public void deleteNotExist() {
        storage.delete(UUID_4);
    }

}
