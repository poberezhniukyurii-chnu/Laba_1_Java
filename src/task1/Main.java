package task1;

import java.util.Scanner;

/**
 * ЛР1, завдання 1 (вираз №4): y = (ab − (a + b)(a − b)) / (b⁴ + a³) + 5b.
 * Обчислення у трьох варіантах: 1) дійсні вхідні дані — дійсний результат;
 * 2) цілі вхідні дані — дійсний результат; 3) дійсні вхідні дані — цілий результат.
 * Побережнюк Ю.В., група 241б.
 */
public class Main {

    /** Читає дійсне число; допускає і кому, і крапку. */
    static double readDouble(Scanner in) {
        return Double.parseDouble(in.next().replace(',', '.'));
    }

    /** Варіант 1: double → double. */
    static double calcDouble(double a, double b) {
        return (a * b - (a + b) * (a - b)) / (Math.pow(b, 4) + Math.pow(a, 3)) + 5 * b;
    }

    /** Варіант 2: int → double. Чисельник і знаменник — цілі, тому перед діленням
     *  потрібне явне приведення до double, інакше буде цілочисельне ділення. */
    static double calcFromInt(int a, int b) {
        long numerator = (long) a * b - (long) (a + b) * (a - b);
        long denominator = (long) b * b * b * b + (long) a * a * a;
        return (double) numerator / denominator + 5 * b;
    }

    /** Варіант 3: double → int (дробова частина відкидається приведенням типу). */
    static int calcToInt(double a, double b) {
        return (int) calcDouble(a, b);
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        System.out.print("1) Введіть дійсні a і b: ");
        double a = readDouble(in), b = readDouble(in);
        if (Math.pow(b, 4) + Math.pow(a, 3) == 0) {
            System.out.println("Знаменник b^4 + a^3 дорівнює нулю — вираз не визначений");
        } else {
            System.out.println("   y = " + calcDouble(a, b));
            System.out.printf("   y ≈ %.4f (форматоване виведення)%n", calcDouble(a, b));
        }

        System.out.print("2) Введіть цілі a і b: ");
        int ia = in.nextInt(), ib = in.nextInt();
        if ((long) ib * ib * ib * ib + (long) ia * ia * ia == 0) {
            System.out.println("Знаменник дорівнює нулю — вираз не визначений");
        } else {
            System.out.println("   y = " + calcFromInt(ia, ib));
            long num = (long) ia * ib - (long) (ia + ib) * (ia - ib);
            long den = (long) ib * ib * ib * ib + (long) ia * ia * ia;
            System.out.println("   (для порівняння: без приведення типу num / den = " + (num / den)
                    + ", тобто дріб втрачено)");
        }

        System.out.print("3) Введіть дійсні a і b: ");
        double ra = readDouble(in), rb = readDouble(in);
        if (Math.pow(rb, 4) + Math.pow(ra, 3) == 0) {
            System.out.println("Знаменник дорівнює нулю — вираз не визначений");
        } else {
            System.out.println("   y = " + calcToInt(ra, rb) + "  (точне значення " + calcDouble(ra, rb) + ")");
            System.out.println("   округлення Math.round: " + Math.round(calcDouble(ra, rb)));
        }
        in.close();
    }
}
