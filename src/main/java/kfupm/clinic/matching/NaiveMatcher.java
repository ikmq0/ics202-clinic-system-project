package kfupm.clinic.matching;

public class NaiveMatcher implements StringMatcher {

    @Override
    public boolean contains(String text, String pattern) {
        if (text == null || pattern == null) {
            return false;
        }

        int n = text.length();
        int m = pattern.length();

        if (m == 0) {
            return true;
        }

        for (int i = 0; i <= n - m; i++) {
            int j;
            for (j = 0; j < m; j++) {
                if (text.charAt(i + j) != pattern.charAt(j)) {
                    break;
                }
            }
            if (j == m) {
                return true; // Found pattern
            }
        }

        return false; // Pattern not found
    }
}
