package utils;

import io.qameta.allure.Allure;
import org.junit.jupiter.api.Assertions;

/**
 * AssertLogger - Wrapper around JUnit assertions that automatically logs results to Allure
 *
 * Usage:
 *   AssertLogger.assertEquals(expected, actual, "Values should match");
 *   AssertLogger.assertTrue(condition, "Condition should be true");
 *
 * Benefits:
 * - Automatically logs assertion details (expected vs actual)
 * - Creates nested Allure steps for each assertion
 * - Shows pass/fail status with values in Allure report
 * - Maintains standard JUnit assertion behavior (throws AssertionError on failure)
 */
public class AssertLogger {

    /**
     * Assert that two objects are equal
     */
    public static <T> void assertEquals(T expected, T actual, String message) {
        Allure.step("✓ " + message, () -> {
            TestLogger.logDataNoParams("Assertion",
                "Expected", expected,
                "Actual", actual
            );

            try {
                Assertions.assertEquals(expected, actual, message);
                TestLogger.info("✓ PASSED");
            } catch (AssertionError e) {
                TestLogger.error("✗ FAILED");
                throw e;
            }
        });
    }

    /**
     * Assert that two objects are equal (without message)
     */
    public static <T> void assertEquals(T expected, T actual) {
        assertEquals(expected, actual, "Expected and actual values should be equal");
    }

    /**
     * Assert that two formatted numbers are equal (handles "1,234.56" formatting)
     * Use this when comparing numbers retrieved from UI that may have thousand separators
     */
    public static void assertNumberEquals(String expectedFormatted, String actualFormatted, String message) {
        double expected = NumberParser.parseDouble(expectedFormatted);
        double actual = NumberParser.parseDouble(actualFormatted);

        Allure.step("✓ " + message, () -> {
            TestLogger.logDataNoParams("Number Assertion",
                "Expected (formatted)", expectedFormatted,
                "Actual (formatted)", actualFormatted,
                "Expected (parsed)", expected,
                "Actual (parsed)", actual
            );

            try {
                Assertions.assertEquals(expected, actual, 0.01, message);
                TestLogger.info("✓ PASSED");
            } catch (AssertionError e) {
                TestLogger.error("✗ FAILED (Difference: " + Math.abs(expected - actual) + ")");
                throw e;
            }
        });
    }

    /**
     * Assert that two formatted numbers are equal with custom delta
     */
    public static void assertNumberEquals(String expectedFormatted, String actualFormatted, double delta, String message) {
        double expected = NumberParser.parseDouble(expectedFormatted);
        double actual = NumberParser.parseDouble(actualFormatted);

        Allure.step("✓ " + message, () -> {
            TestLogger.logDataNoParams("Number Assertion",
                "Expected (formatted)", expectedFormatted,
                "Actual (formatted)", actualFormatted,
                "Expected (parsed)", expected,
                "Actual (parsed)", actual,
                "Delta", delta
            );

            try {
                Assertions.assertEquals(expected, actual, delta, message);
                TestLogger.info("✓ PASSED");
            } catch (AssertionError e) {
                TestLogger.error("✗ FAILED (Difference: " + Math.abs(expected - actual) + ")");
                throw e;
            }
        });
    }

    /**
     * Assert that two doubles are equal within a delta tolerance
     */
    public static void assertEquals(double expected, double actual, double delta, String message) {
        Allure.step("✓ " + message, () -> {
            TestLogger.logDataNoParams("Assertion",
                "Expected", expected,
                "Actual", actual,
                "Delta", delta,
                "Difference", Math.abs(expected - actual)
            );

            try {
                Assertions.assertEquals(expected, actual, delta, message);
                TestLogger.info("✓ PASSED");
            } catch (AssertionError e) {
                TestLogger.error("✗ FAILED");
                throw e;
            }
        });
    }

    /**
     * Assert that two doubles are equal within a delta tolerance (without message)
     */
    public static void assertEquals(double expected, double actual, double delta) {
        assertEquals(expected, actual, delta, "Double values should be equal within delta");
    }

    /**
     * Assert that two floats are equal within a delta tolerance
     */
    public static void assertEquals(float expected, float actual, float delta, String message) {
        Allure.step("✓ " + message, () -> {
            TestLogger.logDataNoParams("Assertion",
                "Expected", expected,
                "Actual", actual,
                "Delta", delta
            );

            try {
                Assertions.assertEquals(expected, actual, delta, message);
                TestLogger.info("✓ PASSED");
            } catch (AssertionError e) {
                TestLogger.error("✗ FAILED (Difference: " + Math.abs(expected - actual) + ")");
                throw e;
            }
        });
    }

    /**
     * Assert that two floats are equal within a delta tolerance (without message)
     */
    public static void assertEquals(float expected, float actual, float delta) {
        assertEquals(expected, actual, delta, "Float values should be equal within delta");
    }

    /**
     * Assert that condition is true
     */
    public static void assertTrue(boolean condition, String message) {
        Allure.step("✓ " + message, () -> {
            TestLogger.logDataNoParams("Assertion",
                "Type", "assertTrue",
                "Message", message,
                "Condition Result", condition
            );

            try {
                Assertions.assertTrue(condition, message);
                TestLogger.info("✓ PASSED");
            } catch (AssertionError e) {
                TestLogger.error("✗ FAILED");
                throw e;
            }
        });
    }

    /**
     * Assert that condition is true (without message)
     */
    public static void assertTrue(boolean condition) {
        assertTrue(condition, "Condition should be true");
    }

    /**
     * Assert that condition is false
     */
    public static void assertFalse(boolean condition, String message) {
        Allure.step("✓ " + message, () -> {
            TestLogger.logDataNoParams("Assertion",
                "Type", "assertFalse",
                "Message", message,
                "Condition Result", condition
            );

            try {
                Assertions.assertFalse(condition, message);
                TestLogger.info("✓ PASSED");
            } catch (AssertionError e) {
                TestLogger.error("✗ FAILED");
                throw e;
            }
        });
    }

    /**
     * Assert that condition is false (without message)
     */
    public static void assertFalse(boolean condition) {
        assertFalse(condition, "Condition should be false");
    }

    /**
     * Assert that object is not null
     */
    public static void assertNotNull(Object object, String message) {
        Allure.step("✓ " + message, () -> {
            TestLogger.logDataNoParams("Assertion",
                "Type", "assertNotNull",
                "Message", message,
                "Object is null?", (object == null)
            );

            try {
                Assertions.assertNotNull(object, message);
                TestLogger.info("✓ PASSED");
            } catch (AssertionError e) {
                TestLogger.error("✗ FAILED");
                throw e;
            }
        });
    }

    /**
     * Assert that object is not null (without message)
     */
    public static void assertNotNull(Object object) {
        assertNotNull(object, "Object should not be null");
    }

    /**
     * Assert that object is null
     */
    public static void assertNull(Object object, String message) {
        Allure.step("✓ " + message, () -> {
            TestLogger.logDataNoParams("Assertion",
                "Type", "assertNull",
                "Message", message,
                "Object is null?", (object == null),
                "Actual Value", object
            );

            try {
                Assertions.assertNull(object, message);
                TestLogger.info("✓ PASSED");
            } catch (AssertionError e) {
                TestLogger.error("✗ FAILED");
                throw e;
            }
        });
    }

    /**
     * Assert that object is null (without message)
     */
    public static void assertNull(Object object) {
        assertNull(object, "Object should be null");
    }

    /**
     * Assert that two objects are NOT equal
     */
    public static <T> void assertNotEquals(T unexpected, T actual, String message) {
        Allure.step("✓ " + message, () -> {
            TestLogger.logDataNoParams("Assertion",
                "Type", "assertNotEquals",
                "Message", message,
                "Unexpected", unexpected,
                "Actual", actual
            );

            try {
                Assertions.assertNotEquals(unexpected, actual, message);
                TestLogger.info("✓ PASSED");
            } catch (AssertionError e) {
                TestLogger.error("✗ FAILED");
                throw e;
            }
        });
    }

    /**
     * Assert that two objects are NOT equal (without message)
     */
    public static <T> void assertNotEquals(T unexpected, T actual) {
        assertNotEquals(unexpected, actual, "Values should not be equal");
    }

    /**
     * Assert that string contains substring
     */
    public static void assertContains(String fullString, String substring, String message) {
        Allure.step("✓ " + message, () -> {
            boolean contains = fullString != null && fullString.contains(substring);

            TestLogger.logDataNoParams("Assertion",
                "Type", "assertContains",
                "Message", message,
                "Full String", fullString,
                "Substring", substring,
                "Contains?", contains
            );

            try {
                Assertions.assertTrue(contains, message);
                TestLogger.info("✓ PASSED");
            } catch (AssertionError e) {
                TestLogger.error("✗ FAILED");
                throw e;
            }
        });
    }

    /**
     * Assert that string contains substring (without message)
     */
    public static void assertContains(String fullString, String substring) {
        assertContains(fullString, substring, "String should contain substring");
    }

    /**
     * Assert that collection/array is not empty
     */
    public static void assertNotEmpty(Object[] array, String message) {
        Allure.step("✓ " + message, () -> {
            boolean isEmpty = array == null || array.length == 0;

            TestLogger.logDataNoParams("Assertion",
                "Type", "assertNotEmpty",
                "Message", message,
                "Array Length", array != null ? array.length : "null",
                "Is Empty?", isEmpty
            );

            try {
                Assertions.assertTrue(!isEmpty, message);
                TestLogger.info("✓ PASSED");
            } catch (AssertionError e) {
                TestLogger.error("✗ FAILED");
                throw e;
            }
        });
    }

    /**
     * Assert that two values are equal with custom comparison message
     * Useful for comparing complex objects where you want to show specific details
     */
    public static <T> void assertEqualsWithDetails(T expected, T actual, String message, String details) {
        Allure.step("✓ " + message, () -> {
            TestLogger.logDataNoParams("Assertion",
                "Type", "assertEquals",
                "Message", message,
                "Expected", expected,
                "Actual", actual,
                "Additional Details", details
            );

            try {
                Assertions.assertEquals(expected, actual, message);
                TestLogger.info("✓ PASSED");
                TestLogger.debug("Details: " + details);
            } catch (AssertionError e) {
                TestLogger.error("✗ FAILED");
                TestLogger.error("Details: " + details);
                throw e;
            }
        });
    }

    /**
     * Soft assertion - log failure but don't throw exception
     * Useful when you want to continue test execution after assertion failure
     */
    public static <T> boolean softAssertEquals(T expected, T actual, String message) {
        boolean passed = expected != null ? expected.equals(actual) : actual == null;

        TestLogger.logDataNoParams("Soft Assertion",
            "Type", "softAssertEquals",
            "Message", message,
            "Expected", expected,
            "Actual", actual,
            "Status", passed ? "PASSED" : "FAILED"
        );

        if (passed) {
            TestLogger.info("✓ Soft Assertion PASSED: " + message);
        } else {
            TestLogger.warn("✗ Soft Assertion FAILED: " + message + " (continuing test execution)");
        }

        return passed;
    }
}
