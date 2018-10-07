package ru.javawebinar.basejava;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.partitioningBy;

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
        return IntStream.of(values)
                .sorted()
                .distinct()
                .reduce(0, (s1, s2) -> (s1 *= 10) + s2);
    }


    static List<Integer> oddOrEven(List<Integer> integers) {
        return integers.stream()
                .collect(partitioningBy(i -> i % 2 == 0))
                .get(integers.stream().mapToInt((a) -> a).sum() % 2 != 0);
    }

}
