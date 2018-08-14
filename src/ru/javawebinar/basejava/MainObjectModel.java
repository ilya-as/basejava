package ru.javawebinar.basejava;

import ru.javawebinar.basejava.model.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class MainObjectModel {
    public static void main(String[] args) {

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

        Resume resume = new Resume("Petrov Sergey");
        resume.addContact(ContactType.TELEPHONE, "333-22-11");
        resume.addContact(ContactType.SKYPE, "grigory.kislin");
        resume.addContact(ContactType.EMAIL, "gkislin@yandex.ru");
        resume.addContact(ContactType.LINDEDIN, "gkislin.linkedin.ru");
        resume.addContact(ContactType.GITHUB, "gkislin.github.ru");
        resume.addContact(ContactType.HOMEPAGE, "javaops.ru");
        resume.addContact(ContactType.STACKOVERFLOW, "gkislin.stackoverflow.ru");
        resume.addSection(SectionType.EXPERIENCE, experienceSection);
        resume.addSection(SectionType.EDUCATION, educationSection);
        resume.addSection(SectionType.ACHIEVEMENT, listAchievement);
        resume.addSection(SectionType.QUALIFICATIONS, listQualifications);
        resume.addSection(SectionType.PERSONAL, new TextSection("good in job"));
        resume.addSection(SectionType.OBJECTIVE, new TextSection("objective description"));

        System.out.println("Resume: " + resume.toString());

        for (ContactType type : ContactType.values()) {
            System.out.println(type.getTitle() + resume.getContact(type).toString());
        }

        for (SectionType type : SectionType.values()) {
            System.out.println(type.getTitle() + resume.getSection(type).toString());
        }

    }
}
