package matching;

public class NaiveMatcher {
    
    public static int search(String text, String pattern) {
        if (text == null || pattern == null) {
            return -1;
        }
        
        int n = text.length();
        int m = pattern.length();
        
        if (m == 0) {
            return 0;
        }

        for (int i = 0; i <= n - m; i++) {
            int j;
            for (j = 0; j < m; j++) {
                if (text.charAt(i + j) != pattern.charAt(j)) {
                    break;
                }
            }
            if (j == m) {
                return i; // Found pattern at index i
            }
        }
        
        return -1; // Pattern not found
    }
}
