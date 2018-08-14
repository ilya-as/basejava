package ru.javawebinar.basejava.model;

public enum ContactType {
    TELEPHONE("Телефон: "),
    SKYPE("Skype: "),
    EMAIL("Почта: "),
    LINDEDIN("Профиль Linkedin: "),
    GITHUB("Профиль Github: "),
    STACKOVERFLOW("Профиль Stackoverflow: "),
    HOMEPAGE("Домашняя страница: ");

    private String title;

    ContactType(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
