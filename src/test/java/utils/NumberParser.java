package utils;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

/**
 * NumberParser - Utility for parsing formatted numbers from UI
 *
 * Many applications display numbers with formatting:
 * - Thousand separators: 1,234.56
 * - Currency symbols: $1,234.56
 * - Percentage signs: 12.5%
 *
 * This utility strips formatting and parses to numeric types.
 */
public class NumberParser {

    /**
     * Parse a formatted string to double
     * Handles: "1,234.56" → 1234.56
     */
    public static double parseDouble(String formattedNumber) {
        if (formattedNumber == null || formattedNumber.trim().isEmpty()) {
            return 0.0;
        }

        // Remove common formatting characters
        String cleaned = formattedNumber
            .replace(",", "")           // Remove thousand separators
            .replace("$", "")           // Remove currency symbols
            .replace("%", "")           // Remove percentage
            .replace(" ", "")           // Remove spaces
            .trim();

        try {
            return Double.parseDouble(cleaned);
        } catch (NumberFormatException e) {
            TestLogger.warn("Failed to parse number: '" + formattedNumber + "' (cleaned: '" + cleaned + "')");
            throw new IllegalArgumentException("Cannot parse as number: " + formattedNumber, e);
        }
    }

    /**
     * Parse a formatted string to float
     */
    public static float parseFloat(String formattedNumber) {
        return (float) parseDouble(formattedNumber);
    }

    /**
     * Parse a formatted string to int
     */
    public static int parseInt(String formattedNumber) {
        return (int) Math.round(parseDouble(formattedNumber));
    }

    /**
     * Parse a formatted string to long
     */
    public static long parseLong(String formattedNumber) {
        return Math.round(parseDouble(formattedNumber));
    }

    /**
     * Format a number for display (with thousand separators)
     * Useful for logging: 1234.56 → "1,234.56"
     */
    public static String formatNumber(double number) {
        return NumberFormat.getNumberInstance(Locale.US).format(number);
    }

    /**
     * Format a number for display (with thousand separators)
     */
    public static String formatNumber(int number) {
        return NumberFormat.getNumberInstance(Locale.US).format(number);
    }

    /**
     * Compare two formatted numbers (handles formatting automatically)
     * Returns true if they represent the same numeric value
     */
    public static boolean equals(String formattedNumber1, String formattedNumber2) {
        return parseDouble(formattedNumber1) == parseDouble(formattedNumber2);
    }

    /**
     * Compare two formatted numbers (handles formatting automatically)
     * Returns true if they represent the same numeric value (with optional delta)
     */
    public static boolean equals(String formattedNumber1, String formattedNumber2, double delta) {
        double num1 = parseDouble(formattedNumber1);
        double num2 = parseDouble(formattedNumber2);
        return Math.abs(num1 - num2) <= delta;
    }
}
