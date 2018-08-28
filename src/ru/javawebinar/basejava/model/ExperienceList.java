package ru.javawebinar.basejava.model;

import java.time.LocalDate;
import java.util.Objects;

public class ExperienceList {
    private LocalDate dataFrom;
    private LocalDate dataTo;
    private String description;
    private String position;

    public ExperienceList(LocalDate dataFrom, LocalDate dataTo, String description, String position) {
        Objects.requireNonNull(dataFrom, "dataFrom must not be null");
        Objects.requireNonNull(dataTo, "dataTo must not be null");
        Objects.requireNonNull(position, "position must not be null");
        this.dataFrom = dataFrom;
        this.dataTo = dataTo;
        this.description = description;
        this.position = position;
    }

    public LocalDate getDataFrom() {
        return dataFrom;
    }

    public LocalDate getDataTo() {
        return dataTo;
    }

    public String getDescription() {
        return description;
    }

    public String getPosition() {
        return position;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ExperienceList that = (ExperienceList) o;

        if (!dataFrom.equals(that.dataFrom)) return false;
        if (!dataTo.equals(that.dataTo)) return false;
        if (description != null ? !description.equals(that.description) : that.description != null) return false;
        return position.equals(that.position);
    }

    @Override
    public int hashCode() {
        int result = dataFrom.hashCode();
        result = 31 * result + dataTo.hashCode();
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + position.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "ExperienceList{" +
                "dataFrom=" + dataFrom +
                ", dataTo=" + dataTo +
                ", description='" + description + '\'' +
                ", position='" + position + '\'' +
                '}';
    }
}
