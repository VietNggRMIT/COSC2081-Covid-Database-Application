package com.data.covid.utils;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Collection;
import java.util.Scanner;
import java.util.function.Function;
import java.util.function.Predicate;


//user inputs loops
public class InputUtils {

    private static final Scanner SCANNER = new Scanner(System.in);

    public static <T> T getCollectionItem(String instruction, String errorText, Collection<T> collection, Function<String, T> transformer) {
        while (true) {
            System.out.print(instruction);
            String raw = SCANNER.nextLine().trim();
            T value = transformer.apply(raw);
            if (collection.contains(value)) return value;
            System.out.println(errorText + " Please try again.");
        }
    }

    public static <E extends Enum<E>> E getEnum(String name, Class<E> enumClass) {
        E[] values = enumClass.getEnumConstants();
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < values.length; i++) builder.append(i).append(". ").append(values[i]).append(' ');
        String instruction = builder.append("\nChoose ").append(name).append(" (number): ").toString();
        String errorText = String.format("Must be in range 0-%d.", values.length - 1);
        int ordinal = getInt(instruction, errorText, i -> i >= 0 && i <= values.length);
        return values[ordinal];
    }

    public static int getInt(String instruction, String errorText, Predicate<Integer> predicate) {
        while (true) {
            System.out.print(instruction);
            try {
                int i = Integer.parseInt(SCANNER.nextLine().trim());
                if (!predicate.test(i)) throw new UnsupportedOperationException();
                return i;
            } catch (Exception e) {
                System.out.println(errorText + " Please try again.");
            }
        }
    }

    public static LocalDate getDate(String instruction, String errorText) {
        while (true) {
            System.out.print(instruction);
            try {
                return LocalDate.parse(SCANNER.nextLine().trim(), DateUtils.OUT_FORMATTER);
            } catch (DateTimeParseException e) {
                System.out.println(errorText + " Please try again.");
            }
        }
    }

    public static void repeat(String text, Runnable runnable) {
        String input;
        while (true) {
            System.out.printf("%s (y/n)?: ", text);
            input = SCANNER.nextLine().trim();
            if (input.isEmpty() || Character.toLowerCase(input.charAt(0)) != 'n') break;
            runnable.run();
        }
    }
}
