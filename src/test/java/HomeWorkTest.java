import org.example.HomeWork;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Tag("homework")
public class HomeWorkTest {

    private static final Random RANDOM = new Random();

    static IntStream randomNumbers() {
        return RANDOM.ints(10, 0, 101);
    }

    @RepeatedTest(10)
    void basicMethodsTest() {
        int number = RANDOM.nextInt(101);
        int age = RANDOM.nextInt(101);

        assertExpected(number % 2 == 0, HomeWork.isEven(number), "isEven", number);
        assertExpected(number > 0, HomeWork.isPositive(number), "isPositive", number);
        assertExpected(age > 18 ? "Allowed" : "Denied", HomeWork.checkAccess(age), "checkAccess", age);
    }

    @RepeatedTest(10)
    void gradeAndBlastOffTest() {
        int score = RANDOM.nextInt(101);
        assertExpected(expectedGrade(score), HomeWork.getGrade(score), "getGrade", score);

        int start = RANDOM.nextInt(10) + 1;
        assertExpected(expectedBlastOff(start), HomeWork.blastOff(start), "blastOff", start);
    }

    @RepeatedTest(10)
    void getGradeForNegativeScoreShouldFail() {
        int score = -1;
        String expected = "Error";
        String actual = HomeWork.getGrade(score);

        assertEquals(expected, actual,
                () -> "getGrade failed for score=" + score + ": expected=" + expected + ", actual=" + actual);
    }

    @RepeatedTest(10)
    void numbersTest() {
        int number = RANDOM.nextInt(100) + 1;
        assertExpected(number * (number + 1) / 2, HomeWork.sumToN(number), "sumToN", number);

        int end = number + 10;
        assertExpected(expectedEvenRange(number, end), HomeWork.getEvenInRange(number, end),
                "getEvenInRange", "start=" + number + ", end=" + end);
    }

    @RepeatedTest(10)
    void collectionsTest() {
        assertTrue(HomeWork.hasBug(new String[]{"Info", "Bug", "Warning"}),
                "hasBug: expected=true, actual=false for messages [Info, Bug, Warning]");
        assertExpected(10, HomeWork.findMax(new int[]{5, 8, 2, 10, 4}), "findMax", "[5, 8, 2, 10, 4]");
        assertArrayExpected(new String[]{"Three", "Two", "One"},
                HomeWork.reverse(new String[]{"One", "Two", "Three"}), "reverse", "[One, Two, Three]");
        assertExpected(4.0, HomeWork.calcAverage(List.of(2, 4, 6)), 0.0, "calcAverage", "[2, 4, 6]");
        assertExpected(List.of("Bob", "Tom"), HomeWork.removeSpecificName(List.of("Ann", "Bob", "Ann", "Tom"), "Ann"),
                "removeSpecificName", "names=[Ann, Bob, Ann, Tom], name=Ann");
    }

    @RepeatedTest(10)
    void basicMethodsRepeatedTest() {
        runBasicMethodsAssertions();
    }

    @RepeatedTest(10)
    void gradeAndBlastOffRepeatedTest() {
        runGradeAndBlastOffAssertions();
    }

    @RepeatedTest(10)
    void numbersRepeatedTest() {
        runNumbersAssertions();
    }

    @RepeatedTest(10)
    void collectionsRepeatedTest() {
        runCollectionsAssertions();
    }

    @ParameterizedTest
    @MethodSource("randomNumbers")
    void isEvenParameterizedTest(int number) {
        assertExpected(number % 2 == 0, HomeWork.isEven(number), "isEven", number);
    }

    @ParameterizedTest
    @MethodSource("randomNumbers")
    void checkAccessParameterizedTest(int age) {
        assertExpected(age > 18 ? "Allowed" : "Denied", HomeWork.checkAccess(age), "checkAccess", age);
        assertExpected(age > 0, HomeWork.isPositive(age), "isPositive", age);
    }

    @ParameterizedTest
    @MethodSource("randomNumbers")
    void gradeAndSumParameterizedTest(int value) {
        assertExpected(expectedGrade(value), HomeWork.getGrade(value), "getGrade", value);
        assertExpected(value * (value + 1) / 2, HomeWork.sumToN(value), "sumToN", value);
    }

    @ParameterizedTest
    @MethodSource("randomNumbers")
    void collectionsParameterizedTest(int value) {
        String[] messages = {"Info", "Bug", "Warning"};
        assertTrue(HomeWork.hasBug(messages),
                "hasBug: expected=true, actual=false for messages " + Arrays.toString(messages));

        int[] array = {value, RANDOM.nextInt(101), RANDOM.nextInt(101), RANDOM.nextInt(101), RANDOM.nextInt(101)};
        int expectedMax = Arrays.stream(array).max().orElseThrow();
        assertExpected(expectedMax, HomeWork.findMax(array), "findMax", Arrays.toString(array));

        String[] source = {"One", "Two", "Three"};
        assertArrayExpected(new String[]{"Three", "Two", "One"}, HomeWork.reverse(source), "reverse", Arrays.toString(source));

        List<Integer> numbers = List.of(RANDOM.nextInt(100), RANDOM.nextInt(100), RANDOM.nextInt(100),
                RANDOM.nextInt(100), RANDOM.nextInt(100));
        double expectedAverage = numbers.stream().mapToInt(Integer::intValue).average().orElseThrow();
        assertExpected(expectedAverage, HomeWork.calcAverage(numbers), 0.0, "calcAverage", numbers);

        List<String> names = List.of("Ann", "Bob", "Ann", "Tom");
        assertExpected(List.of("Bob", "Tom"), HomeWork.removeSpecificName(names, "Ann"),
                "removeSpecificName", "names=" + names + ", name=Ann");

        int start = RANDOM.nextInt(10) + 1;
        assertExpected(expectedBlastOff(start), HomeWork.blastOff(start), "blastOff", start);
        int end = start + 10;
        assertExpected(expectedEvenRange(start, end), HomeWork.getEvenInRange(start, end),
                "getEvenInRange", "start=" + start + ", end=" + end);
    }

    private void runBasicMethodsAssertions() {
        int number = RANDOM.nextInt(101);
        int age = RANDOM.nextInt(101);
        assertExpected(number % 2 == 0, HomeWork.isEven(number), "isEven", number);
        assertExpected(number > 0, HomeWork.isPositive(number), "isPositive", number);
        assertExpected(age > 18 ? "Allowed" : "Denied", HomeWork.checkAccess(age), "checkAccess", age);
    }

    private void runGradeAndBlastOffAssertions() {
        int score = RANDOM.nextInt(101);
        assertExpected(expectedGrade(score), HomeWork.getGrade(score), "getGrade", score);
        int start = RANDOM.nextInt(10) + 1;
        assertExpected(expectedBlastOff(start), HomeWork.blastOff(start), "blastOff", start);
    }

    private void runNumbersAssertions() {
        int number = RANDOM.nextInt(100) + 1;
        assertExpected(number * (number + 1) / 2, HomeWork.sumToN(number), "sumToN", number);
        int end = number + 10;
        assertExpected(expectedEvenRange(number, end), HomeWork.getEvenInRange(number, end),
                "getEvenInRange", "start=" + number + ", end=" + end);
    }

    private void runCollectionsAssertions() {
        assertTrue(HomeWork.hasBug(new String[]{"Info", "Bug", "Warning"}),
                "hasBug: expected=true, actual=false for messages [Info, Bug, Warning]");
        assertExpected(10, HomeWork.findMax(new int[]{5, 8, 2, 10, 4}), "findMax", "[5, 8, 2, 10, 4]");
        assertArrayExpected(new String[]{"Three", "Two", "One"},
                HomeWork.reverse(new String[]{"One", "Two", "Three"}), "reverse", "[One, Two, Three]");
        assertExpected(4.0, HomeWork.calcAverage(List.of(2, 4, 6)), 0.0, "calcAverage", "[2, 4, 6]");
        assertExpected(List.of("Bob", "Tom"), HomeWork.removeSpecificName(List.of("Ann", "Bob", "Ann", "Tom"), "Ann"),
                "removeSpecificName", "names=[Ann, Bob, Ann, Tom], name=Ann");
    }

    private static String expectedGrade(int score) {
        if (score <= 20) return "E";
        if (score <= 40) return "D";
        if (score <= 60) return "C";
        if (score <= 80) return "B";
        return "A";
    }

    private static String expectedBlastOff(int start) {
        StringBuilder expected = new StringBuilder();
        for (int i = start; i >= 1; i--) {
            expected.append(i).append(" ");
        }
        return expected.append("\u0420\u045f\u0420\u0455\u0420\u00b5\u0421\u2026\u0420\u00b0\u0420\u00bb\u0420\u0451!").toString();
    }

    private static String expectedEvenRange(int start, int end) {
        StringBuilder expected = new StringBuilder();
        for (int i = start; i <= end; i++) {
            if (i % 2 == 0) {
                if (expected.length() > 0) expected.append(" ");
                expected.append(i);
            }
        }
        return expected.toString();
    }

    private static <T> void assertExpected(T expected, T actual, String method, Object input) {
        assertEquals(expected, actual,
                () -> method + " failed for input " + input + ": expected=" + expected + ", actual=" + actual);
    }

    private static void assertExpected(double expected, double actual, double delta, String method, Object input) {
        assertEquals(expected, actual, delta,
                () -> method + " failed for input " + input + ": expected=" + expected + ", actual=" + actual);
    }

    private static void assertArrayExpected(String[] expected, String[] actual, String method, Object input) {
        assertArrayEquals(expected, actual,
                () -> method + " failed for input " + input + ": expected=" + Arrays.toString(expected)
                        + ", actual=" + Arrays.toString(actual));
    }
}
