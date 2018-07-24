package ru.javawebinar.basejava;

import ru.javawebinar.basejava.model.Resume;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class MainReflection {

    public static void main(String[] args) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        Resume resume = new Resume("Ivanov");
        Method method = resume.getClass().getMethod("toString");
        System.out.println(method.invoke(resume));
        System.out.println(resume);
    }
}
