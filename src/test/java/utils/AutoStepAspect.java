package utils;

import io.qameta.allure.Allure;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;

import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * AspectJ aspect that automatically creates Allure steps for methods annotated with @AutoStep.
 *
 * This aspect intercepts method calls and:
 * - Wraps them in Allure.step() for report visibility
 * - Generates human-readable step names from method names
 * - Includes parameter names and values in the step name
 * - Logs execution success/failure
 *
 * Example step names generated:
 * - enterEmail("test@example.com") → "Enter Email (email=test@example.com)"
 * - clickSignInButton() → "Click Sign In Button"
 * - fillRow(0, "Wood", "100") → "Fill Row (rowIndex=0, type=Wood, quantity=100)"
 */
@Aspect
public class AutoStepAspect {

    /**
     * Around advice that wraps @AutoStep annotated methods in Allure steps.
     * Only intercepts method EXECUTION (not calls) to avoid nested duplicate steps.
     *
     * @param joinPoint the intercepted method call
     * @param autoStep the @AutoStep annotation instance
     * @return the result of the method execution
     * @throws Throwable if the method execution fails
     */
    @Around("execution(@AutoStep * *(..)) && @annotation(autoStep)")
    public Object autoStepAdvice(ProceedingJoinPoint joinPoint, AutoStep autoStep) throws Throwable {
        String stepName = getStepName(joinPoint, autoStep);

        return Allure.step(stepName, () -> {
            try {
                Object result = joinPoint.proceed();
                TestLogger.debug("✓ " + stepName);
                return result;
            } catch (Throwable e) {
                TestLogger.error("✗ " + stepName + " failed: " + e.getMessage());
                throw e;
            }
        });
    }

    /**
     * Generates a human-readable step name from the method signature.
     *
     * @param joinPoint the intercepted method call
     * @param autoStep the @AutoStep annotation instance
     * @return the generated step name
     */
    private String getStepName(ProceedingJoinPoint joinPoint, AutoStep autoStep) {
        // Use custom name if provided
        if (!autoStep.value().isEmpty()) {
            return autoStep.value();
        }

        // Generate from method name and parameters
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        String methodName = signature.getName();
        Object[] args = joinPoint.getArgs();
        String[] paramNames = signature.getParameterNames();

        // Convert camelCase to readable text
        String readableName = camelCaseToWords(methodName);

        // Add parameters if any (and if parameter names are available)
        if (args != null && args.length > 0 && paramNames != null && paramNames.length == args.length) {
            String params = IntStream.range(0, args.length)
                .mapToObj(i -> formatParameter(paramNames[i], args[i]))
                .collect(Collectors.joining(", "));

            return readableName + " (" + params + ")";
        }

        return readableName;
    }

    /**
     * Converts camelCase method names to readable words.
     * Examples:
     * - enterEmail → Enter Email
     * - clickSignInButton → Click Sign In Button
     * - isProjectListVisible → Is Project List Visible
     *
     * @param camelCase the camelCase string
     * @return the readable string with spaces
     */
    private String camelCaseToWords(String camelCase) {
        // Insert space before uppercase letters
        String withSpaces = camelCase.replaceAll("([A-Z])", " $1").trim();

        // Capitalize first letter
        return withSpaces.substring(0, 1).toUpperCase() + withSpaces.substring(1);
    }

    /**
     * Formats a parameter for display in the step name.
     * Handles special cases like passwords, long strings, and null values.
     *
     * @param paramName the parameter name
     * @param paramValue the parameter value
     * @return formatted parameter string
     */
    private String formatParameter(String paramName, Object paramValue) {
        if (paramValue == null) {
            return paramName + "=null";
        }

        // Mask password fields
        if (paramName.toLowerCase().contains("password")) {
            return paramName + "=***";
        }

        String valueStr = paramValue.toString();

        // Truncate long strings
        if (valueStr.length() > 50) {
            valueStr = valueStr.substring(0, 47) + "...";
        }

        // Handle arrays
        if (paramValue.getClass().isArray()) {
            valueStr = Arrays.toString((Object[]) paramValue);
            if (valueStr.length() > 50) {
                valueStr = valueStr.substring(0, 47) + "...]";
            }
        }

        return paramName + "=" + valueStr;
    }
}
