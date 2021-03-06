package ru.javawebinar.basejava.model;

public enum ContactType {
    TELEPHONE("Телефон: "),
    SKYPE("Skype: ") {
        @Override
        public String toHtml0(String value) {
            return getTitle() + " " + toLink("skype:" + value, value);
        }
    },
    EMAIL("Почта: ") {
        @Override
        public String toHtml0(String value) {
            return getTitle() + " " + toLink("mailto:" + value, value);
        }
    },
    LINDEDIN("Профиль Linkedin: ") {
        @Override
        public String toHtml0(String value) {
            return getTitle() + " " + toLink("URL:" + value, value);
        }
    },
    GITHUB("Профиль Github: ") {
        @Override
        public String toHtml0(String value) {
            return getTitle() + " " + toLink("URL:" + value, value);
        }
    },
    STACKOVERFLOW("Профиль Stackoverflow: ") {
        @Override
        public String toHtml0(String value) {
            return getTitle() + " " + toLink("URL:" + value, value);
        }
    },
    HOMEPAGE("Домашняя страница: ") {
        @Override
        public String toHtml0(String value) {
            return getTitle() + " " + toLink("URL:" + value, value);
        }
    };

    private String title;

    ContactType(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    protected String toHtml0(String value) {
        return title + ": " + value;
    }

    public String toHtml(String value) {
        return (value == null) ? "" : toHtml0(value);
    }

    public String toLink(String href) {
        return toLink(href, title);
    }

    public static String toLink(String href, String title) {
        return "<a href='" + href + "'>" + title + "</a>";
    }
}
