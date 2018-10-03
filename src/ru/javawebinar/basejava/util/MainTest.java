package ru.javawebinar.basejava.util;

public class MainTest {
    public static void main(String[] args) {
      String v = " ww";
      String m = null;
        System.out.println(checkValue(v));
        System.out.println(checkValue(m));
    }
    private static String checkValue(String value) {
        if (value == null) {
            return " !!! ";
        } else
            return value;
    }
}
