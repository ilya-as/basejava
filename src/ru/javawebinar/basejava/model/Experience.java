package ru.javawebinar.basejava.model;

import ru.javawebinar.basejava.util.LocalDateAdapter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

@XmlAccessorType(XmlAccessType.FIELD)
public class Experience implements Serializable {
    private static final long serialVersionUID = 1L;

    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    private LocalDate dataFrom;
    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    private LocalDate dataTo;
    private String description;
    private String position;
    private Link homePage;

    public Experience() {
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

    public Link getHomePage() {
        return homePage;
    }

    public Experience(LocalDate dataFrom, LocalDate dataTo, String description, String position, String url, String name) {
        Objects.requireNonNull(dataFrom, "dataFrom must not be null");
        Objects.requireNonNull(dataTo, "dataTo must not be null");
        Objects.requireNonNull(position, "position must not be null");
        this.dataFrom = dataFrom;
        this.dataTo = dataTo;
        this.description = description;
        this.position = position;
        this.homePage = new Link(name, url);
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
                ", homePage=" + homePage +
                '}';
    }
}