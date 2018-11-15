package ru.javawebinar.basejava.model;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.FIELD)
public class ListSection extends Section {

    private List<String> descriptions;
    private static final long serialVersionUID = 1L;

    public ListSection() {
    }

    public ListSection(String... descriptions) {
        this(Arrays.asList(descriptions));
    }

    public ListSection(List<String> descriptions) {
        Objects.requireNonNull(descriptions, "descriptions must not be null");
        this.descriptions = descriptions;
    }

    public List<String> getDescriptions() {
        return descriptions;
    }

    @Override
    public String toString() {
        return "ListSection{" +
                "descriptions=" + descriptions +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ListSection that = (ListSection) o;
        return Objects.equals(descriptions, that.descriptions);
    }

    @Override
    public int hashCode() {

        return Objects.hash(descriptions);
    }
}
