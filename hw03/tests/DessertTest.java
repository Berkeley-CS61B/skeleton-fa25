import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.Assert.fail;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class DessertTest {

    /* ---------- helpers ---------- */

    private static Class<?> loadDessertClass() {
        try {
            return Class.forName("Dessert");
        } catch (ClassNotFoundException e) {
            fail("Expected a class named Dessert, but it was not found on the classpath.");
            return null; // unreachable
        }
    }

    private static Field getDeclaredField(Class<?> c, String name) {
        try {
            Field f = c.getDeclaredField(name);
            f.setAccessible(true);
            return f;
        } catch (NoSuchFieldException e) {
            fail("Expected field '" + name + "' on class Dessert, but it was not found.");
            return null; // unreachable
        }
    }

    private static Constructor<?> getIntIntConstructor(Class<?> c) {
        try {
            Constructor<?> ctor = c.getDeclaredConstructor(int.class, int.class);
            ctor.setAccessible(true);
            return ctor;
        } catch (NoSuchMethodException e) {
            fail("Expected a public constructor Dessert(int flavor, int price), but it was not found.");
            return null; // unreachable
        }
    }

    private static Object newDessert(int flavor, int price) {
        Class<?> cls = loadDessertClass();
        Constructor<?> ctor = getIntIntConstructor(cls);
        try {
            return ctor.newInstance(flavor, price);
        } catch (ReflectiveOperationException e) {
            fail("Could not construct Dessert(" + flavor + ", " + price + "): " + e);
            return null; // unreachable
        }
    }

    private static void setNumDesserts(int value) {
        Class<?> cls = loadDessertClass();
        Field nd = getDeclaredField(cls, "numDesserts");
        try {
            nd.setInt(null, value);
        } catch (IllegalAccessException e) {
            fail("Could not set static field numDesserts via reflection: " + e);
        }
    }

    private static int getNumDesserts() {
        Class<?> cls = loadDessertClass();
        Field nd = getDeclaredField(cls, "numDesserts");
        try {
            return nd.getInt(null);
        } catch (IllegalAccessException e) {
            fail("Could not read static field numDesserts via reflection: " + e);
            return -1; // unreachable
        }
    }

    private static String captureStdout(Runnable r) {
        PrintStream original = System.out;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(baos);
        try {
            System.setOut(ps);
            r.run();
        } finally {
            System.setOut(original);
        }
        return baos.toString().replace("\r\n", "\n");
    }

    /* ---------- tests ---------- */

    @Test
    @Order(0)
    @DisplayName("Dessert has exactly two instance fields (int flavor, int price)")
    public void testInstanceFields() {
        Class<?> cls = loadDessertClass();

        Field flavor = getDeclaredField(cls, "flavor");
        Field price = getDeclaredField(cls, "price");

        assertThat(flavor.getType()).isEqualTo(int.class);
        assertThat(price.getType()).isEqualTo(int.class);

        assertThat(Modifier.isStatic(flavor.getModifiers())).isFalse();
        assertThat(Modifier.isStatic(price.getModifiers())).isFalse();

        // Enforce exactly two instance (non-static) fields.
        List<Field> instanceFields = Arrays.stream(cls.getDeclaredFields())
                .filter(f -> !Modifier.isStatic(f.getModifiers()))
                .collect(Collectors.toList());
        assertThat(instanceFields).hasSize(2);
    }

    @Test
    @Order(1)
    @DisplayName("Dessert constructor sets flavor and price correctly")
    public void testConstructorSetsFields() {
        setNumDesserts(0);
        Object d = newDessert(7, 11);

        Class<?> cls = d.getClass();
        Field flavor = getDeclaredField(cls, "flavor");
        Field price = getDeclaredField(cls, "price");

        try {
            assertThat(flavor.getInt(d)).isEqualTo(7);
            assertThat(price.getInt(d)).isEqualTo(11);
        } catch (IllegalAccessException e) {
            fail("Could not read instance fields via reflection: " + e);
        }

        // Also verify the constructor signature is public
        Constructor<?> ctor = getIntIntConstructor(cls);
        assertThat(Modifier.isPublic(ctor.getModifiers())).isTrue();
    }

    @Test
    @Order(2)
    @DisplayName("There is exactly one static int field (numDesserts) that counts created desserts")
    public void testStaticNumDessertsFieldAndCounting() {
        Class<?> cls = loadDessertClass();

        Field numDesserts = getDeclaredField(cls, "numDesserts");
        assertThat(Modifier.isStatic(numDesserts.getModifiers())).isTrue();
        assertThat(numDesserts.getType()).isEqualTo(int.class);

        // Ensure it's the only static field
        List<Field> staticFields = Arrays.stream(cls.getDeclaredFields())
                .filter(f -> Modifier.isStatic(f.getModifiers()))
                .collect(Collectors.toList());
        assertThat(staticFields).hasSize(1);

        // Check that it increments as desserts are created
        setNumDesserts(0);
        newDessert(1, 2);
        assertThat(getNumDesserts()).isEqualTo(1);
        newDessert(3, 4);
        assertThat(getNumDesserts()).isEqualTo(2);
    }

    @Test
    @Order(3)
    @DisplayName("printDessert() prints \"flavor price numDesserts\" with spaces")
    public void testPrintDessertOutput() {
        Class<?> cls = loadDessertClass();

        // Verify method exists and is public void with no params
        Method printMethod;
        try {
            printMethod = cls.getDeclaredMethod("printDessert");
            assertThat(Modifier.isPublic(printMethod.getModifiers())).isTrue();
            assertThat(printMethod.getReturnType()).isEqualTo(void.class);
        } catch (NoSuchMethodException e) {
            fail("Expected a method 'public void printDessert()', but it was not found.");
            return;
        }

        // Reset counter, create first dessert, expect "1 2 1"
        setNumDesserts(0);
        Object d1 = newDessert(1, 2);
        String out1 = captureStdout(() -> {
            try {
                printMethod.invoke(d1);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        assertThat(out1).isEqualTo("1 2 1\n");

        // Create second dessert, expect "3 4 2"
        Object d2 = newDessert(3, 4);
        String out2 = captureStdout(() -> {
            try {
                printMethod.invoke(d2);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        assertThat(out2).isEqualTo("3 4 2\n");
    }

    @Test
    @Order(4)
    @DisplayName("main(String[] args) exists, is public static void, and prints exactly \"I love dessert!\"")
    public void testMainMethodSignatureAndOutput() {
        Class<?> cls = loadDessertClass();

        Method main;
        try {
            main = cls.getDeclaredMethod("main", String[].class);
        } catch (NoSuchMethodException e) {
            fail("Expected a method 'public static void main(String[] args)', but it was not found.");
            return;
        }

        int mods = main.getModifiers();
        assertThat(Modifier.isPublic(mods)).isTrue();
        assertThat(Modifier.isStatic(mods)).isTrue();
        assertThat(main.getReturnType()).isEqualTo(void.class);

        String out = captureStdout(() -> {
            try {
                main.invoke(null, (Object) new String[0]);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        assertThat(out).isEqualTo("I love dessert!\n");
    }
}