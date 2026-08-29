import org.example.HomeWork;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

@Tag("homework")
public class HomeWorkTest {

    private static final Random RANDOM = new Random();

    static IntStream randomNumbers() {
        return RANDOM.ints(10, 1, 101);
    }

    @RepeatedTest(10)
    void isEvenTest() {
        int number = RANDOM.nextInt(101);
        boolean expected = number % 2 == 0;
        boolean actual = HomeWork.isEven(number);

        assertEquals(expected, actual,
                "isEven: expected = " + expected + ", actual = " + actual);
    }

    @RepeatedTest(10)
    void isPositiveTest() {
        int number = RANDOM.nextInt(201) - 100;
        boolean expected = number > 0;
        boolean actual = HomeWork.isPositive(number);

        assertEquals(expected, actual,
                "isPositive: expected = " + expected + ", actual = " + actual);
    }

    @RepeatedTest(10)
    void checkAccessTest() {
        int age = RANDOM.nextInt(101);
        String expected = age > 18 ? "Allowed" : "Denied";
        String actual = HomeWork.checkAccess(age);

        assertEquals(expected, actual,
                "checkAccess: expected = " + expected + ", actual = " + actual);
    }

    @RepeatedTest(10)
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

        assertEquals(expected, actual,
                "getGrade: expected = " + expected + ", actual = " + actual);
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

        assertEquals(expected.toString(), actual,
                "blastOff: expected = " + expected + ", actual = " + actual);
    }

    @RepeatedTest(10)
    void sumToNTest() {
        int number = RANDOM.nextInt(100) + 1;
        int expected = number * (number + 1) / 2;
        int actual = HomeWork.sumToN(number);

        assertEquals(expected, actual,
                "sumToN: expected = " + expected + ", actual = " + actual);
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

        assertEquals(expected, actual,
                "hasBug: expected = " + expected + ", actual = " + actual);
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

        assertEquals(expected.toString(), actual,
                "getEvenInRange: expected = " + expected + ", actual = " + actual);
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

        assertEquals(expected, actual,
                "findMax: expected = " + expected + ", actual = " + actual);
    }

    @ParameterizedTest
    @MethodSource("randomNumbers")
    void reverseTest(int number) {
        String[] words = {"word" + number, "word" + RANDOM.nextInt(101), "word" + RANDOM.nextInt(101)};
        String[] expected = {words[2], words[1], words[0]};
        String[] actual = HomeWork.reverse(words);

        assertArrayEquals(expected, actual,
                "reverse: expected = " + Arrays.toString(expected)
                        + ", actual = " + Arrays.toString(actual));
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

        assertEquals(expected, actual,
                "calcAverage: expected = " + expected + ", actual = " + actual);
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

        assertEquals(expected, actual,
                "removeSpecificName: expected = " + expected + ", actual = " + actual);
    }
}
