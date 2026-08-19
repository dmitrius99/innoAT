import org.example.HomeWork;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MainTest {

    @BeforeEach
    void startTestMethood() { System.out.println("========================\nTest method start"); }
    @AfterEach
    void endTestMethood() { System.out.println("Test method end\n========================"); }

    @Test
            void isEvenTest() {
        Random random = new Random();
        int value = random.nextInt(1, 101);

        System.out.println(value + " is even? "+ HomeWork.isEven(value));
    }

    @RepeatedTest(20)
            void checkAccessTest() {
        Random random = new Random();
        int value = random.nextInt(0, 99);
        System.out.println(value + " is accessible? "+ HomeWork.checkAccess(value));
    }

    static IntStream randomNumbers() {
        Random random = new Random();
        int[] numbers = new int[3];

        for (int i = 0; i < numbers.length; i++) {
            numbers[i] = random.nextInt(101);
        }

        return Arrays.stream(numbers);
    }

    @ParameterizedTest
    @MethodSource("randomNumbers")
    void getGradeTest(int value) {
        System.out.println(value + " is score = "+ HomeWork.getGrade(value));
    }
}

