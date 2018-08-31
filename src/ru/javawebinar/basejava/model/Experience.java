package ru.javawebinar.basejava.model;

import ru.javawebinar.basejava.util.LocalDateAdapter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.*;
import java.util.Objects;

@XmlAccessorType(XmlAccessType.FIELD)
public class Experience implements Serializable {
    private static final long serialVersionUID = 1L;

    private Link homePage;
    private List<ExperienceList> positions = new ArrayList<>();

    public Experience() {
    }

    public Experience(String name, String url, ExperienceList... positions) {
        this(new Link(name, url), Arrays.asList(positions));
    }

    public Experience(String name, String url, List<ExperienceList> positions) {
        this(new Link(name, url), positions);
    }

    public Experience(Link homePage, List<ExperienceList> positions) {
        this.homePage = homePage;
        this.positions = positions;
    }

    public Link getHomePage() {
        return homePage;
    }

    public List<ExperienceList> getPositions() {
        return positions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Experience that = (Experience) o;
        return Objects.equals(homePage, that.homePage) &&
                Objects.equals(positions, that.positions);
    }

    @Override
    public int hashCode() {

        return Objects.hash(homePage, positions);
    }

    @Override
    public String toString() {
        return "Experience{" +
                "homePage=" + homePage +
                ", positions=" + positions +
                '}';
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    public static class ExperienceList implements Serializable {
        @XmlJavaTypeAdapter(LocalDateAdapter.class)
        private LocalDate dataFrom;
        @XmlJavaTypeAdapter(LocalDateAdapter.class)
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

        public ExperienceList() {
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
            return "ExperienceList{" +
                    "dataFrom=" + dataFrom +
                    ", dataTo=" + dataTo +
                    ", description='" + description + '\'' +
                    ", position='" + position + '\'' +
                    '}';
        }
    }
}