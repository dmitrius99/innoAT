import org.example.HomeWork;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

public class HomeWorkTest {

    private static final Random RANDOM = new Random();

    static IntStream randomNumbers() {
        return RANDOM.ints(10, 1, 101);
    }

    // @Test

    @Test
    void isEvenTest() {
        int number = RANDOM.nextInt(101);
        boolean expected = number % 2 == 0;
        boolean actual = HomeWork.isEven(number);

        if (expected == actual) {
            System.out.println("isEven: TEST PASSED");
        } else {
            System.out.println("isEven: TEST FAILED");
        }
    }

    @Test
    void isPositiveTest() {
        int number = RANDOM.nextInt(201) - 100;
        boolean expected = number > 0;
        boolean actual = HomeWork.isPositive(number);

        if (expected == actual) {
            System.out.println("isPositive: TEST PASSED");
        } else {
            System.out.println("isPositive: TEST FAILED");
        }
    }

    @Test
    void checkAccessTest() {
        int age = RANDOM.nextInt(101);
        String expected = age > 18 ? "Allowed" : "Denied";
        String actual = HomeWork.checkAccess(age);

        if (expected.equals(actual)) {
            System.out.println("checkAccess: TEST PASSED");
        } else {
            System.out.println("checkAccess: TEST FAILED");
        }
    }

    @Test
    void getGradeTest() {
        int score = RANDOM.nextInt(101);
        String expected;

        if (score <= 20) {
            expected = "E";
        } else if (score <= 40) {
            expected = "D";
        } else if (score <= 60) {
            expected = "C";
        } else if (score <= 80) {
            expected = "B";
        } else {
            expected = "A";
        }

        String actual = HomeWork.getGrade(score);

        if (expected.equals(actual)) {
            System.out.println("getGrade: TEST PASSED");
        } else {
            System.out.println("getGrade: TEST FAILED");
        }
    }

    // @RepeatedTest

    @RepeatedTest(10)
    void blastOffTest() {
        int start = RANDOM.nextInt(10) + 1;
        StringBuilder expected = new StringBuilder();

        for (int i = start; i >= 1; i--) {
            expected.append(i).append(" ");
        }
        expected.append("Поехали!");

        String actual = HomeWork.blastOff(start);

        if (expected.toString().equals(actual)) {
            System.out.println("blastOff: TEST PASSED");
        } else {
            System.out.println("blastOff: TEST FAILED");
        }
    }

    @RepeatedTest(10)
    void sumToNTest() {
        int number = RANDOM.nextInt(100) + 1;
        int expected = number * (number + 1) / 2;
        int actual = HomeWork.sumToN(number);

        if (expected == actual) {
            System.out.println("sumToN: TEST PASSED");
        } else {
            System.out.println("sumToN: TEST FAILED");
        }
    }

    @RepeatedTest(10)
    void hasBugTest() {
        String[] allMessages = {"Info", "Warning", "Bug", "Error"};
        String[] messages = new String[5];
        boolean expected = false;

        for (int i = 0; i < messages.length; i++) {
            messages[i] = allMessages[RANDOM.nextInt(allMessages.length)];
            if (messages[i].equalsIgnoreCase("Bug")) {
                expected = true;
            }
        }

        boolean actual = HomeWork.hasBug(messages);

        if (expected == actual) {
            System.out.println("hasBug: TEST PASSED");
        } else {
            System.out.println("hasBug: TEST FAILED");
        }
    }

    @RepeatedTest(10)
    void getEvenInRangeTest() {
        int start = RANDOM.nextInt(50);
        int end = start + RANDOM.nextInt(20) + 1;
        StringBuilder expected = new StringBuilder();

        for (int i = start; i <= end; i++) {
            if (i % 2 == 0) {
                if (expected.length() > 0) {
                    expected.append(" ");
                }
                expected.append(i);
            }
        }

        String actual = HomeWork.getEvenInRange(start, end);

        if (expected.toString().equals(actual)) {
            System.out.println("getEvenInRange: TEST PASSED");
        } else {
            System.out.println("getEvenInRange: TEST FAILED");
        }
    }

    // @ParameterizedTest

    @ParameterizedTest
    @MethodSource("randomNumbers")
    void findMaxTest(int firstNumber) {
        int[] numbers = {
                firstNumber,
                RANDOM.nextInt(101),
                RANDOM.nextInt(101),
                RANDOM.nextInt(101),
                RANDOM.nextInt(101)
        };
        int expected = numbers[0];

        for (int number : numbers) {
            if (number > expected) {
                expected = number;
            }
        }

        int actual = HomeWork.findMax(numbers);

        if (expected == actual) {
            System.out.println("findMax: TEST PASSED");
        } else {
            System.out.println("findMax: TEST FAILED");
        }
    }

    @ParameterizedTest
    @MethodSource("randomNumbers")
    void reverseTest(int number) {
        String[] words = {"word" + number, "word" + RANDOM.nextInt(101), "word" + RANDOM.nextInt(101)};
        String[] expected = {words[2], words[1], words[0]};
        String[] actual = HomeWork.reverse(words);

        if (expected[0].equals(actual[0])
                && expected[1].equals(actual[1])
                && expected[2].equals(actual[2])) {
            System.out.println("reverse: TEST PASSED");
        } else {
            System.out.println("reverse: TEST FAILED");
        }
    }

    @ParameterizedTest
    @MethodSource("randomNumbers")
    void calcAverageTest(int firstNumber) {
        List<Integer> numbers = new ArrayList<>();
        numbers.add(firstNumber);
        numbers.add(RANDOM.nextInt(101));
        numbers.add(RANDOM.nextInt(101));
        numbers.add(RANDOM.nextInt(101));
        numbers.add(RANDOM.nextInt(101));

        int sum = 0;
        for (int number : numbers) {
            sum += number;
        }
        double expected = (double) sum / numbers.size();
        double actual = HomeWork.calcAverage(numbers);

        if (expected == actual) {
            System.out.println("calcAverage: TEST PASSED");
        } else {
            System.out.println("calcAverage: TEST FAILED");
        }
    }

    @ParameterizedTest
    @MethodSource("randomNumbers")
    void removeSpecificNameTest(int number) {
        String nameToRemove = "Name" + number;
        List<String> names = new ArrayList<>();
        List<String> expected = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            String name = "Name" + RANDOM.nextInt(3);
            if (RANDOM.nextBoolean()) {
                name = nameToRemove;
            }
            names.add(name);

            if (!name.equals(nameToRemove)) {
                expected.add(name);
            }
        }

        List<String> actual = HomeWork.removeSpecificName(names, nameToRemove);

        if (expected.equals(actual)) {
            System.out.println("removeSpecificName: TEST PASSED");
        } else {
            System.out.println("removeSpecificName: TEST FAILED");
        }
    }
}
