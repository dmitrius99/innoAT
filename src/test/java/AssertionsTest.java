import org.example.HomeWork;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AssertionsTest {

    @Test
    void isEvenReturnsBooleanValue() {
        boolean expected = true;
        boolean actual = HomeWork.isEven(6);

        assertEquals(expected, actual,
                "isEven: expected = " + expected + ", actual = " + actual);
    }

    @Test
    void removeSpecificNameReturnsListWithoutName() {
        List<String> names = List.of("Ann", "Bob", "Ann", "Tom");
        List<String> expected = List.of("Bob", "Tom");
        List<String> actual = HomeWork.removeSpecificName(names, "Ann");

        assertEquals(expected, actual,
                "removeSpecificName: expected = " + expected + ", actual = " + actual);
    }

    @Test
    void sumToNReturnsCorrectSum() {
        int expected = 15;
        int actual = HomeWork.sumToN(5);

        assertEquals(expected, actual,
                "sumToN: expected = " + expected + ", actual = " + actual);
    }

    @Test
    void isEvenFailsForOddNumber() {
        boolean expected = true;
        boolean actual = HomeWork.isEven(3);

        assertEquals(expected, actual,
                "isEven: expected = " + expected + ", actual = " + actual);
    }
}
