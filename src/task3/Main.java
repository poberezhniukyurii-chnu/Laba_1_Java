package task3;

import java.util.Scanner;

/**
 * ЛР1, завдання 3 (варіант 14): задана дійсна матриця A(n, n), n ≤ 20. Побудувати вектор X(n):
 * X(i) дорівнює сумі модулів елементів, розміщених між першим і останнім (включно) додатними
 * елементами i-го рядка; якщо додатних елементів немає або є тільки один, то X(i) = −1.
 * Побережнюк Ю.В., група 241б.
 */
public class Main {
    static Scanner in;

    static double readDouble() {
        return Double.parseDouble(in.next().replace(',', '.'));
    }

    static double[][] input() {
        System.out.print("Розмірність матриці n (n <= 20): ");
        int n = in.nextInt();
        if (n < 1 || n > 20) throw new IllegalArgumentException("n має бути від 1 до 20");
        double[][] a = new double[n][n];
        for (int i = 0; i < n; i++) {
            System.out.print("Рядок " + (i + 1) + " (елементів: " + n + "): ");
            for (int j = 0; j < n; j++) {
                a[i][j] = readDouble();
            }
        }
        return a;
    }

    static void print(double[][] a) {
        for (double[] row : a) {
            for (double v : row) System.out.printf("%8.2f", v);
            System.out.println();
        }
    }

    /** X(i) для одного рядка. */
    static double rowValue(double[] row) {
        int first = -1, last = -1;
        for (int j = 0; j < row.length; j++) {
            if (row[j] > 0) {
                if (first < 0) first = j;
                last = j;
            }
        }
        if (first < 0 || first == last) return -1;      // додатних немає або лише один
        double sum = 0;
        for (int j = first; j <= last; j++) sum += Math.abs(row[j]);
        return sum;
    }

    static double[] buildVector(double[][] a) {
        double[] x = new double[a.length];
        for (int i = 0; i < a.length; i++) x[i] = rowValue(a[i]);
        return x;
    }

    public static void main(String[] args) {
        in = new Scanner(System.in);
        try {
            double[][] a = input();
            System.out.println("Матриця A:");
            print(a);
            double[] x = buildVector(a);
            System.out.println("Вектор X:");
            for (int i = 0; i < x.length; i++) {
                System.out.printf("X(%d) = %.2f%n", i + 1, x[i]);
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Помилка: " + e.getMessage());
        }
    }
}
