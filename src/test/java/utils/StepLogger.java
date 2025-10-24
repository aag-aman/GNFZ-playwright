package utils;

import io.qameta.allure.Allure;

import java.util.function.Supplier;

/**
 * StepLogger - Simplified wrapper for Allure steps with enhanced logging
 *
 * Usage:
 *   StepLogger.step("Login to application", () -> {
 *       loginPage.enterCredentials(username, password);
 *       loginPage.clickSubmit();
 *   });
 *
 * Benefits:
 * - Cleaner syntax than Allure.step()
 * - Auto-logs step start/end
 * - Better exception handling
 * - Support for nested steps
 */
public class StepLogger {

    /**
     * Execute a step with logging (no return value)
     */
    public static void step(String stepName, Runnable stepCode) {
        Allure.step(stepName, () -> {
            TestLogger.info("▶ Starting step: " + stepName);
            TestLogger.flush(); // Flush previous logs before starting new step

            try {
                stepCode.run();
                TestLogger.info("✓ Completed step: " + stepName);
            } catch (Exception e) {
                TestLogger.error("✗ Failed step: " + stepName, e);
                throw e;
            } finally {
                TestLogger.flush(); // Flush logs at end of step
            }
        });
    }

    /**
     * Execute a step with logging (with return value)
     */
    public static <T> T step(String stepName, Supplier<T> stepCode) {
        return Allure.step(stepName, () -> {
            TestLogger.info("▶ Starting step: " + stepName);
            TestLogger.flush();

            try {
                T result = stepCode.get();
                TestLogger.info("✓ Completed step: " + stepName);

                if (result != null) {
                    TestLogger.debug("Step result: " + result);
                }

                return result;
            } catch (Exception e) {
                TestLogger.error("✗ Failed step: " + stepName, e);
                throw e;
            } finally {
                TestLogger.flush();
            }
        });
    }

    /**
     * Nested step - creates a sub-step within a parent step
     * Useful for breaking down complex operations into smaller steps
     */
    public static void subStep(String subStepName, Runnable stepCode) {
        Allure.step("  └─ " + subStepName, () -> {
            TestLogger.debug("  ▶ Sub-step: " + subStepName);

            try {
                stepCode.run();
                TestLogger.debug("  ✓ Sub-step completed: " + subStepName);
            } catch (Exception e) {
                TestLogger.error("  ✗ Sub-step failed: " + subStepName, e);
                throw e;
            }
        });
    }

    /**
     * Nested step with return value
     */
    public static <T> T subStep(String subStepName, Supplier<T> stepCode) {
        return Allure.step("  └─ " + subStepName, () -> {
            TestLogger.debug("  ▶ Sub-step: " + subStepName);

            try {
                T result = stepCode.get();
                TestLogger.debug("  ✓ Sub-step completed: " + subStepName);

                if (result != null) {
                    TestLogger.debug("  Sub-step result: " + result);
                }

                return result;
            } catch (Exception e) {
                TestLogger.error("  ✗ Sub-step failed: " + subStepName, e);
                throw e;
            }
        });
    }

    /**
     * Action step - for simple actions (enter text, click button, etc.)
     * Uses compact logging format
     */
    public static void action(String actionDescription) {
        Allure.step(actionDescription, () -> {
            TestLogger.debug("→ " + actionDescription);
        });
    }

    /**
     * Verification step - for assertions and validations
     * Highlighted in logs for easy identification
     */
    public static void verify(String verificationDescription, Runnable verificationCode) {
        Allure.step("Verify: " + verificationDescription, () -> {
            TestLogger.info("🔍 Verifying: " + verificationDescription);

            try {
                verificationCode.run();
                TestLogger.info("✓ Verification passed: " + verificationDescription);
            } catch (AssertionError e) {
                TestLogger.error("✗ Verification failed: " + verificationDescription);
                throw e;
            }
        });
    }

    /**
     * Group multiple related actions under one step
     */
    public static void group(String groupName, Runnable... actions) {
        step(groupName, () -> {
            TestLogger.section(groupName);
            for (Runnable action : actions) {
                action.run();
            }
        });
    }

    /**
     * Conditional step - only executes if condition is true
     */
    public static void stepIf(boolean condition, String stepName, Runnable stepCode) {
        if (condition) {
            step(stepName, stepCode);
        } else {
            TestLogger.debug("⊘ Skipped step (condition false): " + stepName);
        }
    }

    /**
     * Retry step - retries on failure up to maxAttempts times
     */
    public static void stepWithRetry(String stepName, int maxAttempts, Runnable stepCode) {
        Allure.step(stepName, () -> {
            int attempt = 1;
            Exception lastException = null;

            while (attempt <= maxAttempts) {
                try {
                    TestLogger.info("▶ Attempt " + attempt + "/" + maxAttempts + ": " + stepName);
                    stepCode.run();
                    TestLogger.info("✓ Step succeeded on attempt " + attempt);
                    return;
                } catch (Exception e) {
                    lastException = e;
                    TestLogger.warn("⚠ Attempt " + attempt + " failed: " + e.getMessage());
                    attempt++;

                    if (attempt <= maxAttempts) {
                        try {
                            Thread.sleep(1000); // Wait 1 second between retries
                        } catch (InterruptedException ie) {
                            Thread.currentThread().interrupt();
                        }
                    }
                }
            }

            TestLogger.error("✗ Step failed after " + maxAttempts + " attempts");
            throw new RuntimeException("Step failed after " + maxAttempts + " attempts", lastException);
        });
    }

    /**
     * Performance step - logs execution time
     */
    public static void stepWithTiming(String stepName, Runnable stepCode) {
        Allure.step(stepName, () -> {
            long startTime = System.currentTimeMillis();
            TestLogger.info("▶ Starting step: " + stepName);

            try {
                stepCode.run();
                long duration = System.currentTimeMillis() - startTime;
                TestLogger.info("✓ Completed step: " + stepName + " (took " + duration + "ms)");
                Allure.parameter("Execution Time", duration + "ms");
            } catch (Exception e) {
                long duration = System.currentTimeMillis() - startTime;
                TestLogger.error("✗ Failed step: " + stepName + " (after " + duration + "ms)", e);
                throw e;
            } finally {
                TestLogger.flush();
            }
        });
    }
}
