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
public class Organization implements Serializable {
    private static final long serialVersionUID = 1L;
    public static final Organization EMPTY = new Organization("", "", Position.EMPTY);

    private Link homePage;
    private List<Position> positions = new ArrayList<>();

    public Organization() {
    }

    public Organization(String name, String url, Position... positions) {
        this(new Link(name, url), Arrays.asList(positions));
    }

    public Organization(String name, String url, List<Position> positions) {
        this(new Link(name, url), positions);
    }

    public Organization(Link homePage, List<Position> positions) {
        this.homePage = homePage;
        this.positions = positions;
    }

    public Link getHomePage() {
        return homePage;
    }

    public List<Position> getPositions() {
        return positions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Organization that = (Organization) o;
        return Objects.equals(homePage, that.homePage) &&
                Objects.equals(positions, that.positions);
    }

    @Override
    public int hashCode() {

        return Objects.hash(homePage, positions);
    }

    @Override
    public String toString() {
        return "Organization{" +
                "homePage=" + homePage +
                ", positions=" + positions +
                '}';
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    public static class Position implements Serializable {

        public static final Position EMPTY = new Position();

        @XmlJavaTypeAdapter(LocalDateAdapter.class)
        private LocalDate dataFrom;
        @XmlJavaTypeAdapter(LocalDateAdapter.class)
        private LocalDate dataTo;
        private String description;
        private String title;

        public Position(LocalDate dataFrom, LocalDate dataTo, String description, String title) {
            Objects.requireNonNull(dataFrom, "dataFrom must not be null");
            Objects.requireNonNull(dataTo, "dataTo must not be null");
            Objects.requireNonNull(title, "title must not be null");
            this.dataFrom = dataFrom;
            this.dataTo = dataTo;
            this.description = description;
            this.title = title;
        }

        public Position() {
        }

        public LocalDate getStartDate() {
            return dataFrom;
        }

        public LocalDate getEndDate() {
            return dataTo;
        }

        public String getDescription() {
            return description;
        }

        public String getTitle() {
            return title;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Position that = (Position) o;
            return Objects.equals(dataFrom, that.dataFrom) &&
                    Objects.equals(dataTo, that.dataTo) &&
                    Objects.equals(description, that.description) &&
                    Objects.equals(title, that.title);
        }

        @Override
        public int hashCode() {

            return Objects.hash(dataFrom, dataTo, description, title);
        }

        @Override
        public String toString() {
            return "Position{" +
                    "dataFrom=" + dataFrom +
                    ", dataTo=" + dataTo +
                    ", content='" + description + '\'' +
                    ", title='" + title + '\'' +
                    '}';
        }
    }
}