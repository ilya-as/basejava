package ru.javawebinar.basejava.model;

import java.time.LocalDate;
import java.util.Objects;

public class Experience {
    private LocalDate dataFrom;
    private LocalDate dataTo;
    private String description;
    private String position;

    public Experience(LocalDate dataFrom, LocalDate dataTo, String description, String position) {
        this.dataFrom = dataFrom;
        this.dataTo = dataTo;
        this.description = description;
        this.position = position;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Experience that = (Experience) o;
        return Objects.equals(dataFrom, that.dataFrom) &&
                Objects.equals(dataTo, that.dataTo) &&
                Objects.equals(description, that.description) &&
                Objects.equals(position, that.position);
    }

    @Override
    public int hashCode() {

        return Objects.hash(dataFrom, dataTo, description, position);
    }

    @Override
    public String toString() {
        return "Experience{" +
                "dataFrom=" + dataFrom +
                ", dataTo=" + dataTo +
                ", description='" + description + '\'' +
                ", position='" + position + '\'' +
                '}';
    }
}
