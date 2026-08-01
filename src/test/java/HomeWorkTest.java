import org.example.HomeWork;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

public class HomeWorkTest {

    private static final Random RANDOM = new Random();

    static IntStream randomNumbers() {
        return RANDOM.ints(10, 0, 101);
    }

    // ==========================================
    // @Test
    // ==========================================

    @Test
    void basicMethodsTest() {

        int number = RANDOM.nextInt(101);
        int age = RANDOM.nextInt(101);

        // isEven
        boolean expectedEven = number % 2 == 0;
        boolean actualEven = HomeWork.isEven(number);

        if (expectedEven == actualEven) {
            System.out.println("isEven -> TEST PASSED");
        } else {
            System.out.println("isEven -> TEST FAILED");
        }

        // isPositive
        boolean expectedPositive = number > 0;
        boolean actualPositive = HomeWork.isPositive(number);

        if (expectedPositive == actualPositive) {
            System.out.println("isPositive -> TEST PASSED");
        } else {
            System.out.println("isPositive -> TEST FAILED");
        }

        // checkAccess
        String expectedAccess = age > 18 ? "Allowed" : "Denied";
        String actualAccess = HomeWork.checkAccess(age);

        if (expectedAccess.equals(actualAccess)) {
            System.out.println("checkAccess -> TEST PASSED");
        } else {
            System.out.println("checkAccess -> TEST FAILED");
        }
    }

    @Test
    void gradeAndBlastOffTest() {

        int score = RANDOM.nextInt(101);

        String expectedGrade;

        if (score <= 20)
            expectedGrade = "E";
        else if (score <= 40)
            expectedGrade = "D";
        else if (score <= 60)
            expectedGrade = "C";
        else if (score <= 80)
            expectedGrade = "B";
        else
            expectedGrade = "A";

        if (expectedGrade.equals(HomeWork.getGrade(score))) {
            System.out.println("getGrade -> TEST PASSED");
        } else {
            System.out.println("getGrade -> TEST FAILED");
        }

        int start = RANDOM.nextInt(10) + 1;

        StringBuilder expectedBlastOff = new StringBuilder();

        for (int i = start; i >= 1; i--) {
            expectedBlastOff.append(i).append(" ");
        }

        expectedBlastOff.append("Поехали!");

        if (expectedBlastOff.toString().equals(HomeWork.blastOff(start))) {
            System.out.println("blastOff -> TEST PASSED");
        } else {
            System.out.println("blastOff -> TEST FAILED");
        }
    }

    @Test
    void numbersTest() {

        int number = RANDOM.nextInt(100) + 1;

        int expectedSum = number * (number + 1) / 2;

        if (expectedSum == HomeWork.sumToN(number)) {
            System.out.println("sumToN -> TEST PASSED");
        } else {
            System.out.println("sumToN -> TEST FAILED");
        }

        int end = number + 10;

        StringBuilder expected = new StringBuilder();

        for (int i = number; i <= end; i++) {

            if (i % 2 == 0) {

                if (expected.length() > 0) {
                    expected.append(" ");
                }

                expected.append(i);
            }
        }

        if (expected.toString().equals(HomeWork.getEvenInRange(number, end))) {
            System.out.println("getEvenInRange -> TEST PASSED");
        } else {
            System.out.println("getEvenInRange -> TEST FAILED");
        }
    }

    @Test
    void collectionsTest() {

        String[] messages = {"Info", "Bug", "Warning"};

        if (HomeWork.hasBug(messages)) {
            System.out.println("hasBug -> TEST PASSED");
        } else {
            System.out.println("hasBug -> TEST FAILED");
        }

        int[] array = {5, 8, 2, 10, 4};

        if (HomeWork.findMax(array) == 10) {
            System.out.println("findMax -> TEST PASSED");
        } else {
            System.out.println("findMax -> TEST FAILED");
        }

        String[] reversed = HomeWork.reverse(new String[]{"One", "Two", "Three"});

        if (reversed[0].equals("Three")
                && reversed[1].equals("Two")
                && reversed[2].equals("One")) {

            System.out.println("reverse -> TEST PASSED");

        } else {

            System.out.println("reverse -> TEST FAILED");
        }

        List<Integer> numbers = List.of(2, 4, 6);

        if (HomeWork.calcAverage(numbers) == 4.0) {
            System.out.println("calcAverage -> TEST PASSED");
        } else {
            System.out.println("calcAverage -> TEST FAILED");
        }

        List<String> names = List.of("Ann", "Bob", "Ann", "Tom");

        List<String> expected =
                List.of("Bob", "Tom");

        if (expected.equals(HomeWork.removeSpecificName(names, "Ann"))) {
            System.out.println("removeSpecificName -> TEST PASSED");
        } else {
            System.out.println("removeSpecificName -> TEST FAILED");
        }
    }
    // ==========================================
    // @RepeatedTest
    // ==========================================

    @RepeatedTest(10)
    void basicMethodsRepeatedTest() {

        int number = RANDOM.nextInt(101);
        int age = RANDOM.nextInt(101);

        // isEven
        boolean expectedEven = number % 2 == 0;
        boolean actualEven = HomeWork.isEven(number);

        if (expectedEven == actualEven) {
            System.out.println("isEven -> TEST PASSED");
        } else {
            System.out.println("isEven -> TEST FAILED");
        }

        // isPositive
        boolean expectedPositive = number > 0;
        boolean actualPositive = HomeWork.isPositive(number);

        if (expectedPositive == actualPositive) {
            System.out.println("isPositive -> TEST PASSED");
        } else {
            System.out.println("isPositive -> TEST FAILED");
        }

        // checkAccess
        String expectedAccess = age > 18 ? "Allowed" : "Denied";
        String actualAccess = HomeWork.checkAccess(age);

        if (expectedAccess.equals(actualAccess)) {
            System.out.println("checkAccess -> TEST PASSED");
        } else {
            System.out.println("checkAccess -> TEST FAILED");
        }
    }

    @RepeatedTest(10)
    void gradeAndBlastOffRepeatedTest() {

        int score = RANDOM.nextInt(101);

        String expectedGrade;

        if (score <= 20)
            expectedGrade = "E";
        else if (score <= 40)
            expectedGrade = "D";
        else if (score <= 60)
            expectedGrade = "C";
        else if (score <= 80)
            expectedGrade = "B";
        else
            expectedGrade = "A";

        if (expectedGrade.equals(HomeWork.getGrade(score))) {
            System.out.println("getGrade -> TEST PASSED");
        } else {
            System.out.println("getGrade -> TEST FAILED");
        }

        int start = RANDOM.nextInt(10) + 1;

        StringBuilder expectedBlastOff = new StringBuilder();

        for (int i = start; i >= 1; i--) {
            expectedBlastOff.append(i).append(" ");
        }

        expectedBlastOff.append("Поехали!");

        if (expectedBlastOff.toString().equals(HomeWork.blastOff(start))) {
            System.out.println("blastOff -> TEST PASSED");
        } else {
            System.out.println("blastOff -> TEST FAILED");
        }
    }

    @RepeatedTest(10)
    void numbersRepeatedTest() {

        int number = RANDOM.nextInt(100) + 1;

        int expectedSum = number * (number + 1) / 2;

        if (expectedSum == HomeWork.sumToN(number)) {
            System.out.println("sumToN -> TEST PASSED");
        } else {
            System.out.println("sumToN -> TEST FAILED");
        }

        int end = number + 10;

        StringBuilder expectedRange = new StringBuilder();

        for (int i = number; i <= end; i++) {

            if (i % 2 == 0) {

                if (expectedRange.length() > 0) {
                    expectedRange.append(" ");
                }

                expectedRange.append(i);
            }
        }

        if (expectedRange.toString().equals(HomeWork.getEvenInRange(number, end))) {
            System.out.println("getEvenInRange -> TEST PASSED");
        } else {
            System.out.println("getEvenInRange -> TEST FAILED");
        }
    }

    @RepeatedTest(10)
    void collectionsRepeatedTest() {

        String[] messages = {"Info", "Bug", "Warning"};

        if (HomeWork.hasBug(messages)) {
            System.out.println("hasBug -> TEST PASSED");
        } else {
            System.out.println("hasBug -> TEST FAILED");
        }

        int[] array = {5, 8, 2, 10, 4};

        if (HomeWork.findMax(array) == 10) {
            System.out.println("findMax -> TEST PASSED");
        } else {
            System.out.println("findMax -> TEST FAILED");
        }

        String[] reversed = HomeWork.reverse(new String[]{"One", "Two", "Three"});

        if (reversed[0].equals("Three")
                && reversed[1].equals("Two")
                && reversed[2].equals("One")) {

            System.out.println("reverse -> TEST PASSED");

        } else {

            System.out.println("reverse -> TEST FAILED");
        }

        List<Integer> numbers = List.of(2, 4, 6);

        if (HomeWork.calcAverage(numbers) == 4.0) {
            System.out.println("calcAverage -> TEST PASSED");
        } else {
            System.out.println("calcAverage -> TEST FAILED");
        }

        List<String> names = List.of("Ann", "Bob", "Ann", "Tom");

        List<String> expected =
                List.of("Bob", "Tom");

        if (expected.equals(HomeWork.removeSpecificName(names, "Ann"))) {
            System.out.println("removeSpecificName -> TEST PASSED");
        } else {
            System.out.println("removeSpecificName -> TEST FAILED");
        }
    }
    // ==========================================
    // @ParameterizedTest
    // ==========================================

    @ParameterizedTest
    @MethodSource("randomNumbers")
    void isEvenParameterizedTest(int number) {

        boolean expected = number % 2 == 0;

        if (expected == HomeWork.isEven(number)) {
            System.out.println("isEven -> TEST PASSED");
        } else {
            System.out.println("isEven -> TEST FAILED");
        }
    }

    @ParameterizedTest
    @MethodSource("randomNumbers")
    void checkAccessParameterizedTest(int age) {

        String expected = age > 18 ? "Allowed" : "Denied";

        if (expected.equals(HomeWork.checkAccess(age))) {
            System.out.println("checkAccess -> TEST PASSED");
        } else {
            System.out.println("checkAccess -> TEST FAILED");
        }

        boolean expectedPositive = age > 0;

        if (expectedPositive == HomeWork.isPositive(age)) {
            System.out.println("isPositive -> TEST PASSED");
        } else {
            System.out.println("isPositive -> TEST FAILED");
        }
    }

    @ParameterizedTest
    @MethodSource("randomNumbers")
    void gradeAndSumParameterizedTest(int value) {

        String expectedGrade;

        if (value <= 20)
            expectedGrade = "E";
        else if (value <= 40)
            expectedGrade = "D";
        else if (value <= 60)
            expectedGrade = "C";
        else if (value <= 80)
            expectedGrade = "B";
        else
            expectedGrade = "A";

        if (expectedGrade.equals(HomeWork.getGrade(value))) {
            System.out.println("getGrade -> TEST PASSED");
        } else {
            System.out.println("getGrade -> TEST FAILED");
        }

        int expectedSum = value * (value + 1) / 2;

        if (expectedSum == HomeWork.sumToN(value)) {
            System.out.println("sumToN -> TEST PASSED");
        } else {
            System.out.println("sumToN -> TEST FAILED");
        }
    }

    @ParameterizedTest
    @MethodSource("randomNumbers")
    void collectionsParameterizedTest(int value) {

        String[] messages = {"Info", "Bug", "Warning"};

        if (HomeWork.hasBug(messages)) {
            System.out.println("hasBug -> TEST PASSED");
        } else {
            System.out.println("hasBug -> TEST FAILED");
        }

        int[] array = {
                value,
                RANDOM.nextInt(101),
                RANDOM.nextInt(101),
                RANDOM.nextInt(101),
                RANDOM.nextInt(101)
        };

        int expectedMax = array[0];

        for (int i = 1; i < array.length; i++) {
            if (array[i] > expectedMax) {
                expectedMax = array[i];
            }
        }

        if (expectedMax == HomeWork.findMax(array)) {
            System.out.println("findMax -> TEST PASSED");
        } else {
            System.out.println("findMax -> TEST FAILED");
        }

        String[] reversed =
                HomeWork.reverse(new String[]{"One", "Two", "Three"});

        if (reversed[0].equals("Three")
                && reversed[1].equals("Two")
                && reversed[2].equals("One")) {

            System.out.println("reverse -> TEST PASSED");

        } else {

            System.out.println("reverse -> TEST FAILED");
        }

        List<Integer> numbers = Arrays.asList(
                RANDOM.nextInt(100),
                RANDOM.nextInt(100),
                RANDOM.nextInt(100),
                RANDOM.nextInt(100),
                RANDOM.nextInt(100)
        );

        double expectedAverage = 0;

        for (Integer number : numbers) {
            expectedAverage += number;
        }

        expectedAverage /= numbers.size();

        if (expectedAverage == HomeWork.calcAverage(numbers)) {
            System.out.println("calcAverage -> TEST PASSED");
        } else {
            System.out.println("calcAverage -> TEST FAILED");
        }

        List<String> names =
                Arrays.asList("Ann", "Bob", "Ann", "Tom");

        List<String> expected =
                Arrays.asList("Bob", "Tom");

        if (expected.equals(HomeWork.removeSpecificName(names, "Ann"))) {
            System.out.println("removeSpecificName -> TEST PASSED");
        } else {
            System.out.println("removeSpecificName -> TEST FAILED");
        }

        int start = RANDOM.nextInt(10) + 1;

        StringBuilder expectedBlastOff = new StringBuilder();

        for (int i = start; i >= 1; i--) {
            expectedBlastOff.append(i).append(" ");
        }

        expectedBlastOff.append("Поехали!");

        if (expectedBlastOff.toString().equals(HomeWork.blastOff(start))) {
            System.out.println("blastOff -> TEST PASSED");
        } else {
            System.out.println("blastOff -> TEST FAILED");
        }

        int end = start + 10;

        StringBuilder expectedRange = new StringBuilder();

        for (int i = start; i <= end; i++) {

            if (i % 2 == 0) {

                if (expectedRange.length() > 0) {
                    expectedRange.append(" ");
                }

                expectedRange.append(i);
            }
        }

        if (expectedRange.toString().equals(HomeWork.getEvenInRange(start, end))) {
            System.out.println("getEvenInRange -> TEST PASSED");
        } else {
            System.out.println("getEvenInRange -> TEST FAILED");
        }
    }
}
