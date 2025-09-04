import org.junit.jupiter.api.Test;
import static org.junit.Assert.*;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

import jh61b.utils.RuntimeInstrumentation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Timeout;

import java.util.function.Function;
import java.util.function.Consumer;

@Timeout(value = 10, threadMode = Timeout.ThreadMode.SEPARATE_THREAD)
public class TestStack {

    // /** Helper that pops all items from s into an int[] (top to bottom). */
    // private static int[] popAll(Stack s) {
    //     int n = s.size();
    //     int[] a = new int[n];
    //     for (int i = 0; i < n; i += 1) {
    //         a[i] = s.pop();
    //     }
    //     return a;
    // }

    // @Test
    // @Order(1)
    // public void testEmptySizeAndSum() {
    //     Stack s = new Stack();
    //     assertEquals(0, s.size());
    //     assertEquals(0, s.sum());
    // }

    // @Test
    // @Order(2)
    // public void testPushPopSize() {
    //     Stack s = new Stack();
    //     s.push(1);
    //     s.push(10);
    //     s.push(100);
    //     assertEquals(3, s.size());

    //     assertEquals(100, s.pop());
    //     assertEquals(2, s.size());
    //     assertEquals(10, s.pop());
    //     assertEquals(1, s.size());
    //     assertEquals(1, s.pop());
    //     assertEquals(0, s.size());
    // }

    // @Test
    // @Order(3)
    // public void testSum() {
    //     Stack s = new Stack();
    //     s.push(1);
    //     s.push(10);
    //     s.push(100);
    //     assertEquals(111, s.sum());

    //     // Negative values
    //     Stack t = new Stack();
    //     t.push(-5);
    //     t.push(2);
    //     t.push(-7);
    //     assertEquals(-10, t.sum());

    //     // Sum after pops
    //     Stack u = new Stack();
    //     u.push(3);
    //     u.push(4);
    //     u.push(5); // top
    //     assertEquals(12, u.sum());
    //     u.pop();   // remove 5
    //     assertEquals(7, u.sum());
    // }

    // @Test
    // @Order(4)
    // public void testFlipped() {
    //     Stack s = new Stack();
    //     s.push(1);    // bottom
    //     s.push(10);
    //     s.push(100);  // top

    //     Stack f = StackClient.flipped(s);

    //     int[] popped = popAll(f); // should get 1, 10, 100 in that order
    //     assertArrayEquals(new int[]{1, 10, 100}, popped);
    // }

    // @Test
    // @Order(5)
    // public void testFlippedSingleAndEmpty() {
    //     Stack empty = new Stack();
    //     Stack flippedEmpty = StackClient.flipped(empty);
    //     assertEquals(0, flippedEmpty.size());

    //     Stack one = new Stack();
    //     one.push(42);
    //     Stack flippedOne = StackClient.flipped(one);
    //     assertEquals(0, one.size());
    //     assertEquals(1, flippedOne.size());
    //     assertEquals(42, flippedOne.pop());
    // }

    // @Test
    // @Order(6)
    // public void testNoPublicInstanceVariables() {
    //     Field[] fields = Stack.class.getDeclaredFields();
    //     List<String> offenders = new ArrayList<>();
    //     for (Field f : fields) {
    //         int mods = f.getModifiers();
    //         boolean isInstance = !Modifier.isStatic(mods);
    //         boolean isPublic = Modifier.isPublic(mods);
    //         if (isInstance && isPublic) {
    //             offenders.add(f.getName());
    //         }
    //     }
    //     assertTrue("Stack has public instance variables: " + offenders, offenders.isEmpty());
    // }

    // @Test
    // @Order(7)
    // public void testPushPopConstantTime() {
    //     // Provide a Stack prefilled with n+1 items (ensures non-empty for pop at n=0).
    //     Function<Integer, Stack> provide = (Integer n) -> {
    //         Stack s = new Stack();
    //         for (int i = 0; i < n + 1; i += 1) {
    //             s.push(i);
    //         }
    //         return s;
    //     };

    //     Consumer<Stack> doPush = (Stack s) -> s.push(0);
    //     Consumer<Stack> doPop  = (Stack s) -> s.pop();

    //     RuntimeInstrumentation.assertAtMost(
    //             "push", RuntimeInstrumentation.ComplexityType.CONSTANT, provide, doPush, 8);
    //     RuntimeInstrumentation.assertAtMost(
    //             "pop", RuntimeInstrumentation.ComplexityType.CONSTANT, provide, doPop, 8);
    // }    
}
