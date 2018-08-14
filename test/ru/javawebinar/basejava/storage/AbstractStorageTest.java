package ru.javawebinar.basejava.storage;

import org.junit.Before;
import org.junit.Test;
import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.model.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class AbstractStorageTest {

    public Storage storage;

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

        String descriptionExperience = "description experience";
        LocalDate dataFrom = LocalDate.of(2016, 02, 3);
        LocalDate dataTo = LocalDate.of(2018, 07, 4);
        Experience experience = new Experience("Wrike", "wrike.com", dataFrom,
                dataTo, descriptionExperience, "java progammer");
        List<Experience> experiencesList = new ArrayList<>();
        experiencesList.add(experience);
        Section experienceSection = new ExperienceSection(experiencesList);

        String descriptionEducation = "description education";
        LocalDate educationFrom = LocalDate.of(2011, 06, 1);
        LocalDate educationTo = LocalDate.of(2016, 07, 31);
        Experience education = new Experience("MGU", "", educationFrom, educationTo, descriptionEducation, "engineer");
        List<Experience> educationsList = new ArrayList<>();
        educationsList.add(education);
        Section educationSection = new ExperienceSection(educationsList);

        List<String> descriptionsAchievement = new ArrayList<>();
        descriptionsAchievement.add("description achievement 1");
        descriptionsAchievement.add("description achievement 2");
        Section listAchievement = new ListSection(descriptionsAchievement);

        List<String> descriptionsQualifications = new ArrayList<>();
        descriptionsQualifications.add("descriptionsQualifications" + "1");
        descriptionsQualifications.add("descriptionsQualifications" + "2");
        Section listQualifications = new ListSection(descriptionsQualifications);

        RESUME_1.addContact(ContactType.TELEPHONE, "333-22-11");
        RESUME_1.addContact(ContactType.SKYPE, "grigory.kislin");
        RESUME_1.addContact(ContactType.EMAIL, "gkislin@yandex.ru");
        RESUME_1.addContact(ContactType.LINDEDIN, "gkislin.linkedin.ru");
        RESUME_1.addContact(ContactType.GITHUB, "gkislin.github.ru");
        RESUME_1.addContact(ContactType.HOMEPAGE, "javaops.ru");
        RESUME_1.addContact(ContactType.STACKOVERFLOW, "gkislin.stackoverflow.ru");
        RESUME_1.addSection(SectionType.EXPERIENCE, experienceSection);
        RESUME_1.addSection(SectionType.EDUCATION, educationSection);
        RESUME_1.addSection(SectionType.ACHIEVEMENT, listAchievement);
        RESUME_1.addSection(SectionType.QUALIFICATIONS, listQualifications);
        RESUME_1.addSection(SectionType.PERSONAL, new TextSection("good in job"));
        RESUME_1.addSection(SectionType.OBJECTIVE, new TextSection("objective description"));
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
    public void getContact() {
        assertEquals("gkislin.github.ru", RESUME_1.getContact(ContactType.GITHUB));
    }

    @Test
    public void getSection() {
        assertEquals(new TextSection("good in job"), RESUME_1.getSection(SectionType.PERSONAL));
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