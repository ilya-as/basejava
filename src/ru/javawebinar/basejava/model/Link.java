package ru.javawebinar.basejava.model;

import java.util.Objects;
import java.io.Serializable;

public class Link implements Serializable {
    private final String name;
    private final String url;
    private static final long serialVersionUID = 1L;

    public Link(String name, String url) {
        Objects.requireNonNull(name, "name must be not null");
        this.name = name;
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Link Link = (Link) o;

        if (!name.equals(Link.name)) return false;
        return url != null ? url.equals(Link.url) : Link.url == null;
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + (url != null ? url.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Link{" +
                "name='" + name + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}