package utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * TestDataManager - Utility class for managing test data
 */
public class TestDataManager {
    private static final ObjectMapper mapper = new ObjectMapper();
    private static final String DATA_PATH = "src/test/java/data/";

    /**
     * Load test data from JSON file
     */
    public static List<Map<String, String>> loadTestData(String fileName) throws IOException {
        File dataFile = new File(DATA_PATH + fileName);
        if (!dataFile.exists()) {
            throw new IOException("Test data file not found: " + dataFile.getAbsolutePath());
        }

        return mapper.readValue(dataFile, new TypeReference<List<Map<String, String>>>() {});
    }

    /**
     * Load smoke test user data
     */
    public static List<Map<String, String>> loadSmokeUsers() throws IOException {
        return loadTestData("smoke-users.json");
    }

    /**
     * Load regression test user data
     */
    public static List<Map<String, String>> loadRegressionUsers() throws IOException {
        return loadTestData("regression-users.json");
    }

    /**
     * Get the first valid user from smoke test data
     */
    public static Map<String, String> getSmokeUser() throws IOException {
        List<Map<String, String>> users = loadSmokeUsers();
        if (users.isEmpty()) {
            throw new RuntimeException("No smoke test users found");
        }
        return users.get(0);
    }

    /**
     * Get valid user from regression test data (first one is always valid)
     */
    public static Map<String, String> getValidRegressionUser() throws IOException {
        List<Map<String, String>> users = loadRegressionUsers();
        if (users.isEmpty()) {
            throw new RuntimeException("No regression test users found");
        }
        return users.get(0);
    }

    /**
     * Filter users by expected result type
     */
    public static List<Map<String, String>> filterUsersByResult(List<Map<String, String>> users, String expectedResult) {
        return users.stream()
            .filter(user -> expectedResult.equals(user.get("expectedSuccess")))
            .toList();
    }

    /**
     * Get all error test cases
     */
    public static List<Map<String, String>> getErrorTestCases() throws IOException {
        List<Map<String, String>> users = loadRegressionUsers();
        return filterUsersByResult(users, "error");
    }

    /**
     * Get all success test cases
     */
    public static List<Map<String, String>> getSuccessTestCases() throws IOException {
        List<Map<String, String>> users = loadRegressionUsers();
        return filterUsersByResult(users, "List of projects");
    }

    /**
     * Load forgot password smoke test data
     */
    public static List<Map<String, String>> loadForgotPasswordSmokeData() throws IOException {
        return loadTestData("forgot-password-smoke.json");
    }

    /**
     * Load forgot password regression test data
     */
    public static List<Map<String, String>> loadForgotPasswordRegressionData() throws IOException {
        return loadTestData("forgot-password-regression.json");
    }

    /**
     * Get the first forgot password smoke test data
     */
    public static Map<String, String> getForgotPasswordSmokeData() throws IOException {
        List<Map<String, String>> data = loadForgotPasswordSmokeData();
        if (data.isEmpty()) {
            throw new RuntimeException("No forgot password smoke test data found");
        }
        return data.get(0);
    }

    /**
     * Filter forgot password test cases by test type
     */
    public static List<Map<String, String>> filterForgotPasswordByType(List<Map<String, String>> testCases, String testType) {
        return testCases.stream()
            .filter(testCase -> testType.equals(testCase.get("testType")))
            .toList();
    }

    /**
     * Get positive forgot password test cases
     */
    public static List<Map<String, String>> getPositiveForgotPasswordTestCases() throws IOException {
        List<Map<String, String>> testCases = loadForgotPasswordRegressionData();
        return filterForgotPasswordByType(testCases, "positive");
    }

    /**
     * Get negative forgot password test cases
     */
    public static List<Map<String, String>> getNegativeForgotPasswordTestCases() throws IOException {
        List<Map<String, String>> testCases = loadForgotPasswordRegressionData();
        return filterForgotPasswordByType(testCases, "negative");
    }

    /**
     * Load sign-up smoke test data
     */
    public static Map<String, String> loadSignUpSmokeData() throws IOException {
        List<Map<String, String>> data = loadTestData("signup-smoke.json");
        if (data.isEmpty()) {
            throw new RuntimeException("No sign-up smoke test data found");
        }
        return data.get(0);
    }

    /**
     * Load sign-up regression test data
     */
    public static List<Map<String, String>> loadSignUpRegressionData() throws IOException {
        return loadTestData("signup-regression.json");
    }

    /**
     * Filter sign-up test cases by test type
     */
    public static List<Map<String, String>> filterSignUpByType(List<Map<String, String>> testCases, String testType) {
        return testCases.stream()
            .filter(testCase -> testType.equals(testCase.get("testType")))
            .toList();
    }

    /**
     * Get positive sign-up test cases
     */
    public static List<Map<String, String>> getPositiveSignUpTestCases() throws IOException {
        List<Map<String, String>> testCases = loadSignUpRegressionData();
        return filterSignUpByType(testCases, "positive");
    }

    /**
     * Get negative sign-up test cases
     */
    public static List<Map<String, String>> getNegativeSignUpTestCases() throws IOException {
        List<Map<String, String>> testCases = loadSignUpRegressionData();
        return filterSignUpByType(testCases, "negative");
    }

    /**
     * Get user by role from users.json
     */
    public static Map<String, String> getUserByRole(String role) throws IOException {
        List<Map<String, String>> users = loadTestData("users.json");
        return users.stream()
            .filter(user -> role.equals(user.get("role")))
            .findFirst()
            .orElseThrow(() -> new RuntimeException("No user found with role: " + role));
    }

    /**
     * Get project owner user
     */
    public static Map<String, String> getProjectOwnerUser() throws IOException {
        return getUserByRole("projectOwner");
    }

    /**
     * Get team member user
     */
    public static Map<String, String> getTeamMemberUser() throws IOException {
        return getUserByRole("teamMember");
    }
}