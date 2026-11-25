package task4;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * ЛР1, завдання 4 (варіант 14): задано текст, слова в якому розділені пробілами і розділовими
 * знаками. Вилучити з цього тексту всі слова, що починаються з голосної букви.
 * Побережнюк Ю.В., група 241б.
 */
public class Main {
    /** Голосні українські та латинські літери (у нижньому регістрі). */
    static final String VOWELS = "аеєиіїоуюяaeiou";

    /** Слово — послідовність літер (апостроф і дефіс усередині слова допускаються). */
    static final Pattern WORD = Pattern.compile("\\p{L}+(?:['’-]\\p{L}+)*");

    static boolean startsWithVowel(String word) {
        return VOWELS.indexOf(Character.toLowerCase(word.charAt(0))) >= 0;
    }

    /** Повертає текст без слів, що починаються з голосної; вилучені слова додає в removed. */
    static String removeVowelWords(String text, List<String> removed) {
        StringBuilder sb = new StringBuilder();
        Matcher m = WORD.matcher(text);
        int pos = 0;
        while (m.find()) {
            sb.append(text, pos, m.start());           // розділювачі між словами зберігаються
            if (startsWithVowel(m.group())) {
                removed.add(m.group());
            } else {
                sb.append(m.group());
            }
            pos = m.end();
        }
        sb.append(text.substring(pos));
        return sb.toString()
                .replaceAll("\\s+([,.;:!?])", "$1")     // пробіл перед розділовим знаком
                .replaceAll("([,;:])(?=[,;:.!?])", "")  // кома, після якої лишився інший знак
                .replaceAll("\\s{2,}", " ")
                .replaceAll("^[\\s,;:]+", "")
                .trim();
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        System.out.println("Введіть текст:");
        String text = in.nextLine();
        List<String> removed = new ArrayList<>();
        String result = removeVowelWords(text, removed);
        System.out.println("Вилучені слова (" + removed.size() + "): " + String.join(" ", removed));
        System.out.println("Текст після вилучення: " + result);
        in.close();
    }
}
