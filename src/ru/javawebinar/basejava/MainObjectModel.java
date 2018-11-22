package ru.javawebinar.basejava;

import ru.javawebinar.basejava.model.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class MainObjectModel {
    public static void main(String[] args) {

        String descriptionExperience = "content organization";
        LocalDate dataFrom = LocalDate.of(2016, 2, 3);
        LocalDate dataTo = LocalDate.of(2018, 7, 4);
        Organization.Position positionList = new Organization.Position(dataFrom, dataTo,descriptionExperience, "java progammer");
        Organization organization = new Organization("Wrike", "wrike.com", positionList);
        List<Organization> experiencesList = new ArrayList<>();
        experiencesList.add(organization);
        Section experienceSection = new OrganizationSection(experiencesList);

        String descriptionEducation = "content education";
        LocalDate educationFrom = LocalDate.of(2011, 6, 1);
        LocalDate educationTo = LocalDate.of(2016, 7, 31);
        Organization.Position positionEducationList = new Organization.Position(educationFrom, educationTo,descriptionEducation,  "engineer" );
        Organization education = new Organization("MGU", "mgu.com", positionEducationList);

        List<Organization> educationsList = new ArrayList<>();
        educationsList.add(education);
        Section educationSection = new OrganizationSection(educationsList);

        List<String> descriptionsAchievement = new ArrayList<>();
        descriptionsAchievement.add("content achievement 1");
        descriptionsAchievement.add("content achievement 2");
        Section listAchievement = new ListSection(descriptionsAchievement);

        List<String> descriptionsQualifications = new ArrayList<>();
        descriptionsQualifications.add("descriptionsQualifications" + "1");
        descriptionsQualifications.add("descriptionsQualifications" + "2");
        Section listQualifications = new ListSection(descriptionsQualifications);

        Resume resume = new Resume("Petrov Sergey");
        resume.setContact(ContactType.TELEPHONE, "333-22-11");
        resume.setContact(ContactType.SKYPE, "grigory.kislin");
        resume.setContact(ContactType.EMAIL, "gkislin@yandex.ru");
        resume.setContact(ContactType.LINDEDIN, "gkislin.linkedin.ru");
        resume.setContact(ContactType.GITHUB, "gkislin.github.ru");
        resume.setContact(ContactType.HOMEPAGE, "javaops.ru");
        resume.setContact(ContactType.STACKOVERFLOW, "gkislin.stackoverflow.ru");
        resume.setSection(SectionType.EXPERIENCE, experienceSection);
        resume.setSection(SectionType.EDUCATION, educationSection);
        resume.setSection(SectionType.ACHIEVEMENT, listAchievement);
        resume.setSection(SectionType.QUALIFICATIONS, listQualifications);
        resume.setSection(SectionType.PERSONAL, new TextSection("good in job"));
        resume.setSection(SectionType.OBJECTIVE, new TextSection("objective content"));

        System.out.println("Resume: " + resume.toString());

        for (ContactType type : ContactType.values()) {
            System.out.println(type.getTitle() + resume.getContact(type).toString());
        }

        for (SectionType type : SectionType.values()) {
            System.out.println(type.getTitle() + resume.getSection(type).toString());
        }

    }
}
