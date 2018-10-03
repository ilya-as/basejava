package ru.javawebinar.basejava;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class StreamDemo {
    public static void main(String[] args) {
        int[] values = {1,2,3,3,2,3};
        int result = minValue(values);
        System.out.println(result);

        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8);
        Predicate<Integer> evenFunc = (a) -> a%2 == 0;
        Predicate<Integer> oddFunc = evenFunc.negate();

        int evenSum = numbers.stream().filter(evenFunc).mapToInt((a) -> a).sum();
        int oddSum = numbers.stream().filter(oddFunc).mapToInt((a) -> a).sum();
        System.out.println(evenSum);
        System.out.println(oddSum);
    }
    static int minValue(int[] values){
        //преобразуем в массив Integer
        ArrayList<Integer> arrayList =
                new ArrayList<>(Arrays.asList(Arrays.stream( values ).boxed().toArray( Integer[]::new )));
        //отсортируем и удалим дубли
        List<Integer> numbers =  arrayList.stream().sorted().distinct().collect(Collectors.toList());
       //"склеим" числа
        Integer result = numbers.stream().reduce((s1, s2) -> (s1*= 10) + s2).orElse(0);
        return result;
    }
    static List<Integer> oddOrEven(List<Integer> integers){
       // integers.stream().mapToInt((s) -> Integer.parseInt(s)).sum();
        return integers;
    }

}
