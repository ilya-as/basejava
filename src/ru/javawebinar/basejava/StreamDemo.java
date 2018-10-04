package ru.javawebinar.basejava;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class StreamDemo {
    public static void main(String[] args) {
        //выбрать уникальные и вернуть минимально возможное число, составленное из этих уникальных цифр
        int[] values = {1, 2, 3, 3, 2, 3};
        int result = minValue(values);
        System.out.println(result);

        // если сумма всех чисел нечетная - удалить все нечетные, если четная - удалить все четные
        List<Integer> integers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8);
        List<Integer> resultList = oddOrEven(integers);
        resultList.forEach(System.out::print);
    }

    static int minValue(int[] values) {
        Integer result = IntStream.of(values).mapToObj(Integer::valueOf).sorted()
                .distinct().reduce((s1, s2) -> (s1 *= 10) + s2).orElse(0);
        return result;
    }

    static List<Integer> oddOrEven(List<Integer> integers) {

        Predicate<Integer> evenFunc = (a) -> a % 2 == 0;
        Predicate<Integer> oddFunc = evenFunc.negate();

        List<Integer> result = integers.stream()
                .filter((integers.stream().mapToInt((a) -> a).sum() % 2 == 0) ? oddFunc : evenFunc)
                .collect(Collectors.toList());

        return result;
    }
}
