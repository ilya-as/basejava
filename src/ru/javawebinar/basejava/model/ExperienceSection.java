package ru.javawebinar.basejava.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@XmlAccessorType(XmlAccessType.FIELD)
public class ExperienceSection extends Section {
    private static final long serialVersionUID = 1L;
    private List<Experience> experiencesList;

    public ExperienceSection() {
    }

    public ExperienceSection(Experience... content) {
        this(Arrays.asList(content));
    }

    public ExperienceSection(List<Experience> experiencesList) {
        Objects.requireNonNull(experiencesList, "experiencesList must not be null");
        this.experiencesList = experiencesList;
    }

    public List<Experience> getexperiencesList() {
        return experiencesList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ExperienceSection that = (ExperienceSection) o;
        return Objects.equals(experiencesList, that.experiencesList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(experiencesList);
    }

    @Override
    public String toString() {
        return "ExperienceSection{" +
                "experiencesList=" + experiencesList +
                '}';
    }
}
