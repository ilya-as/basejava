package ru.javawebinar.basejava.model;

import java.util.List;
import java.util.Objects;

public class ExperienceSection extends Section {
    private static final long serialVersionUID = 1L;
    private List<Experience> experiencesList;

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
