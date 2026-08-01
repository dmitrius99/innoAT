package org.example;
import java.util.ArrayList;
import java.util.List;

public class HomeWork {
    // Задача 1
    public static boolean isEven(int n) {
        return n % 2 == 0;
    }

    // Задача 2
    public static String checkAccess(int age) {
        return age > 18 ? "Allowed" : "Denied";
    }

    // Задача 3
    public static boolean isPositive(int n) {
        return n > 0 ? true : false;
    }

    // Задача 4
    public static String getGrade(int score) {
        if (score >= 0 && score <= 20) {
            return "E";
        } else if (score <= 40) {
            return "D";
        } else if (score <= 60) {
            return "C";
        } else if (score <= 80) {
            return "B";
        } else if (score <= 100) {
            return "A";
        } else {
            return "Error";
        }
    }

    // Задача 5
    public static String blastOff(int start) {
        StringBuilder phrase = new StringBuilder();

        for (int i = start; i >= 1; i--) {
            phrase.append(i).append(" ");
        }

        phrase.append("Поехали!");
        return phrase.toString();
    }

    // Задача 6
    public static int sumToN(int n) {
        int sum = 0;

        for (int i = 1; i <= n; i++) {
            sum += i;
        }

        return sum;
    }

    // Задача 7
    public static boolean hasBug(String[] messages) {
        for (String message : messages) {
            if ("Bug".equalsIgnoreCase(message)) {
                return true;
            }
        }
        return false;
    }

    // Задача 8
    public static String getEvenInRange(int start, int end) {
        StringBuilder phrase = new StringBuilder();

        for (int i = start; i <= end; i++) {
            if (i % 2 == 0) {
                if (phrase.length() > 0) {
                    phrase.append(" ");
                }
                phrase.append(i);
            }
        }

        return phrase.toString();
    }

    // Задача 9
    public static int findMax(int[] arr) {
        int max = arr[0];

        for (int i = 0; i < arr.length; i++) {
            if (arr[i] > max) {
                max = arr[i];
            }
        }

        return max;
    }

    // Задача 10
    public static String[] reverse(String[] arr) {
        String[] result = new String[arr.length];

        for (int i = 0; i < arr.length; i++) {
            result[i] = arr[arr.length - 1 - i];
        }

        return result;
    }

    // Задача 11
    public static double calcAverage(List<Integer> list) {
        if (list.isEmpty()) {
            return 0;
        }

        int sum = 0;

        for (int i = 0; i < list.size(); i++) {
            sum += list.get(i);
        }

        return (double) sum / list.size();
    }

    // Задача 12
    public static List<String> removeSpecificName(List<String> list, String nameToRemove) {
        List<String> result = new ArrayList<>();

        for (String name : list) {
            if (!name.equals(nameToRemove)) {
                result.add(name);
            }
        }

        return result;
    }
}

