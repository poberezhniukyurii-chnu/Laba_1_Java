package task2;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;

/**
 * ЛР1, завдання 2 (варіант 14): задано масиви цілих чисел A(n), n ≤ 400, і B(m), m ≤ 200.
 * Знайти найменший елемент серед тих елементів масиву A, які не містяться у масиві B.
 * Для порівняння методів роботи з масивами задачу розв'язано трьома способами.
 * Побережнюк Ю.В., група 241б.
 */
public class Main {
    static Scanner in;

    static int[] input(String name, int maxSize) {
        System.out.print("Розмірність масиву " + name + " (не більше " + maxSize + "): ");
        int n = in.nextInt();
        if (n < 1 || n > maxSize) {
            throw new IllegalArgumentException("розмірність має бути від 1 до " + maxSize);
        }
        int[] a = new int[n];
        System.out.print("Елементи " + name + " через пробіл: ");
        for (int i = 0; i < n; i++) {
            a[i] = in.nextInt();
        }
        return a;
    }

    /** Спосіб 1: вкладені цикли — для кожного A[i] лінійний пошук у B. Складність O(n·m). */
    static Integer minNotInLoops(int[] a, int[] b) {
        Integer min = null;
        for (int x : a) {
            boolean found = false;
            for (int y : b) {
                if (x == y) {
                    found = true;
                    break;
                }
            }
            if (!found && (min == null || x < min)) min = x;
        }
        return min;
    }

    /** Спосіб 2: копію B сортуємо (Arrays.sort) і шукаємо двійковим пошуком. O((n + m)·log m). */
    static Integer minNotInBinarySearch(int[] a, int[] b) {
        int[] sortedB = Arrays.copyOf(b, b.length);
        Arrays.sort(sortedB);
        Integer min = null;
        for (int x : a) {
            if (Arrays.binarySearch(sortedB, x) < 0 && (min == null || x < min)) min = x;
        }
        return min;
    }

    /** Спосіб 3: множина HashSet — перевірка належності в середньому за O(1). */
    static Integer minNotInSet(int[] a, int[] b) {
        Set<Integer> setB = new HashSet<>();
        for (int y : b) setB.add(y);
        Integer min = null;
        for (int x : a) {
            if (!setB.contains(x) && (min == null || x < min)) min = x;
        }
        return min;
    }

    static String show(Integer v) {
        return v == null ? "таких елементів немає (усі елементи A є в B)" : String.valueOf(v);
    }

    public static void main(String[] args) {
        in = new Scanner(System.in);
        try {
            int[] a = input("A", 400);
            int[] b = input("B", 200);
            System.out.println("A = " + Arrays.toString(a));
            System.out.println("B = " + Arrays.toString(b));
            System.out.println("Вкладені цикли:          " + show(minNotInLoops(a, b)));
            System.out.println("Arrays.binarySearch:     " + show(minNotInBinarySearch(a, b)));
            System.out.println("HashSet.contains:        " + show(minNotInSet(a, b)));
        } catch (IllegalArgumentException e) {
            System.out.println("Помилка: " + e.getMessage());
            return;
        }

        // Порівняння швидкості на великих випадкових масивах (більших за умову, щоб різниця була помітна)
        Random rnd = new Random(14);
        int[] bigA = new int[40_000], bigB = new int[20_000];
        for (int i = 0; i < bigA.length; i++) bigA[i] = rnd.nextInt(100_000) - 50_000;
        for (int i = 0; i < bigB.length; i++) bigB[i] = rnd.nextInt(100_000) - 50_000;
        long t0 = System.nanoTime();
        Integer r1 = minNotInLoops(bigA, bigB);
        long t1 = System.nanoTime();
        Integer r2 = minNotInBinarySearch(bigA, bigB);
        long t2 = System.nanoTime();
        Integer r3 = minNotInSet(bigA, bigB);
        long t3 = System.nanoTime();
        System.out.println("Порівняння на масивах 40000 і 20000 елементів:");
        System.out.printf("  вкладені цикли: результат %d, %.1f мс%n", r1, (t1 - t0) / 1e6);
        System.out.printf("  сортування + двійковий пошук: результат %d, %.1f мс%n", r2, (t2 - t1) / 1e6);
        System.out.printf("  HashSet: результат %d, %.1f мс%n", r3, (t3 - t2) / 1e6);
    }
}
