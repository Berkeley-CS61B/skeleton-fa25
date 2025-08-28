import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.util.Arrays;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.Assert.fail;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ArrayExercisesTest {
    @Test
    @Order(0)
    @DisplayName("Test secondToLastItem correctness")
    public void testSecondToLastItem() {
        String[] items = {"apple", "banana", "cherry", "date"};
        String expected = "cherry";

        String result = ArrayExercises.secondToLastItem(items);

        assertThat(result).isEqualTo(expected);
    }

    @Test
    @Order(1)
    @DisplayName("Test minMaxDifference correctness (test one)")
    public void testMinMaxDifference() {
        int[] items = {1, 2, 3, 4, 5};
        int expected = 4;

        int result = ArrayExercises.minMaxDifference(items);

        if (result != expected) {            
            String errorMessage = String.format("For input %s, expected minMaxDifference to be %s but got %s", Arrays.toString(items), expected, result);
            fail(errorMessage);
        }
    }

    @Test
    @Order(2)
    @DisplayName("Test minMaxDifference correctness (test two)")
    public void testMinMaxDifferenceTwo() {
        int[] items = {-5, -10, -3, -8, -1, 12, 14, 16};
        int expected = 26;

        int result = ArrayExercises.minMaxDifference(items);

        if (result != expected) {            
            String errorMessage = String.format("For input %s, expected minMaxDifference to be %s but got %s", Arrays.toString(items), expected, result);  
            fail(errorMessage);
        }
    }
}