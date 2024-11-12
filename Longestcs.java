package code;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Longestcs {

    // Method to find the length of LCS
    public static int findLCSLength(String seq1, String seq2) {
        int m = seq1.length();
        int n = seq2.length();
        int[][] dp = new int[m + 1][n + 1];

        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if (seq1.charAt(i - 1) == seq2.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1] + 1;
                } else {
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
                }
            }
        }
        return dp[m][n]; // Length of LCS
    }

    // Method to find the LCS sequence itself
    public static String findLCS(String seq1, String seq2) {
        int m = seq1.length();
        int n = seq2.length();
        int[][] dp = new int[m + 1][n + 1];

        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if (seq1.charAt(i - 1) == seq2.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1] + 1;
                } else {
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
                }
            }
        }

        // Trace back to get the LCS string
        StringBuilder lcs = new StringBuilder();
        int i = m, j = n;
        while (i > 0 && j > 0) {
            if (seq1.charAt(i - 1) == seq2.charAt(j - 1)) {
                lcs.append(seq1.charAt(i - 1));
                i--;
                j--;
            } else if (dp[i - 1][j] > dp[i][j - 1]) {
                i--;
            } else {
                j--;
            }
        }
        return lcs.reverse().toString();
    }

    public static void main(String[] args) {
        // Path to the CSV file with names and ranks
        String filePath = "C:\\Users\\sunda\\eclipse-workspace\\LCSProject\\src\\code\\babyname.csv"; // Update to your actual file path

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            br.readLine(); // Skip header line if it exists

            while ((line = br.readLine()) != null) {
                String[] columns = line.split(","); // Split by comma to get each column

                if (columns.length == 3) { // Ensure there are three columns (Rank, Male name, Female name)
                    String maleName = columns[1].trim(); // Male name is in the 2nd column
                    String femaleName = columns[2].trim(); // Female name is in the 3rd column

                    // Calculate LCS length and sequence
                    int lcsLength = findLCSLength(maleName, femaleName);
                    String lcsSequence = findLCS(maleName, femaleName);

                    // Display the results for each pair of names
                    System.out.println("Male Name: " + maleName);
                    System.out.println("Female Name: " + femaleName);
                    System.out.println("Length of Longest Common Subsequence: " + lcsLength);
                    System.out.println("Longest Common Subsequence: " + lcsSequence);
                    System.out.println("========================================");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
