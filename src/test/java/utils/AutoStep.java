package utils;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Automatically creates an Allure step from method execution.
 *
 * When applied to a method, this annotation will:
 * - Wrap the method execution in an Allure step
 * - Generate step name from method name and parameters
 * - Log execution status (success/failure)
 * - Show parameter values in the report
 *
 * Usage:
 * <pre>
 * {@code
 * @AutoStep
 * public void enterEmail(String email) {
 *     InputHelper.humanizedInput(page, emailField, email);
 * }
 *
 * @AutoStep("Custom step name")
 * public void clickSignInButton() {
 *     signInButton.click();
 * }
 * }
 * </pre>
 *
 * This eliminates the need to manually wrap method calls in Allure.step().
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface AutoStep {
    /**
     * Custom step name (optional).
     * If empty, the step name will be auto-generated from the method name.
     *
     * @return custom step name or empty string for auto-generation
     */
    String value() default "";
}
