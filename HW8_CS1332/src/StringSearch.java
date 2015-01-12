
import java.util.*;

public class StringSearch {

    /**
     * Find all instances of pattern in text using Boyer-Moore algorithm. Use
     * buildBoyerMooreCharTable to build your reference table.
     *
     * @param pattern The String to find
     * @param text The String to look through
     * @return A List of starting indices where pattern was found in text
     */
    public static List<Integer> boyerMoore(String pattern, String text) {
        int[] BMtable = buildBoyerMooreCharTable(pattern);
        ArrayList<Integer> indicesFound = new ArrayList<>();
        int patternInd0 = pattern.length() - 1;
        
        int iText = patternInd0;
        int iPattern = iText;
        
        while (iText < text.length()) {
            
            char textChar = text.charAt(iText);
            if (pattern.charAt(iPattern) == textChar) {
                if (iPattern == 0) {
                    // we've reached the beginning of the "pattern" string
                    indicesFound.add(iText);
                    iPattern = patternInd0;
                    iText += pattern.length();
                }
                else {
                    // keep decrementing indices to try to find a whole string
                    // match
                    iText--;
                    iPattern--;
                }
            }
            else {
                // reset pattern index, move text index
                iPattern = patternInd0;
                iText += BMtable[(int) textChar];
            }
        }
        
        // note: this could be an empty list
        return (List<Integer>) indicesFound; 
    }

    /**
     * Creates a table of distances from each character to the end. Has an entry
     * for every character from 0 to Character.MAX_VALUE. For use with
     * Boyer-Moore.
     *
     * If the character is in the string: map[c] = length - last index of c - 1
     * Otherwise: map[c] = length
     *
     * @param pattern The string being searched for
     * @return An int array for Boyer-Moore
     */
    public static int[] buildBoyerMooreCharTable(String pattern) {
        int[] map = new int[Character.MAX_VALUE + 1];
        int strLen = pattern.length();
        
        for (int i = 0; i < Character.MAX_VALUE; ++i) {
            Character c = (char) i;
            if (pattern.indexOf(c) > -1) {
                // The character does appear in the string
                map[i] = Math.max(1, strLen - pattern.lastIndexOf(c) - 1);
            }
            else {
                map[i] = strLen;
            }
        }

        return map;
    }

    
    /**
     * Find all instances of pattern in text using Knuth-Morris-Pratt algorithm.
     * Use buildKmpSuffixTable to build your reference table.
     *
     * @param pattern The String to find
     * @param test The String to look through
     * @return A List of starting indices where pattern was found in text
     */
    public static List<Integer> kmp(String pattern, String text) {
        int[] KMPtable = buildKmpSuffixTable(pattern);
        ArrayList<Integer> indices = new ArrayList<>();
        
        int iPattern = 0;
        int iText = 0;

        while (iText < text.length()) {
            
            if (pattern.charAt(iPattern) == text.charAt(iText)) {
                // character match
                if (iPattern == pattern.length() - 1) {
                    indices.add(iText - pattern.length() + 1);
                }
                ++iPattern;
                ++iText;
            } 
            else if (iPattern > 0) {
                // some characters of "pattern" matched, but not all
                iPattern = KMPtable[iPattern];
            }
            else {
                ++iText;
            }
        }
        
        // note: this could be an empty list
        return (List<Integer>) indices;
    }

    /**
     * Creates a table of matching suffix and prefix sizes. For use with
     * Knuth-Morris-Pratt.
     *
     * If i = 0: map[i] = -1 If i > 0: map[i] = size of largest common prefix
     * and suffix for substring of size i
     *
     * @param pattern The string bing searched for
     * @return An int array for Knuth-Morris-Pratt
     */
    public static int[] buildKmpSuffixTable(String pattern) {
        int[] map = new int[pattern.length()];
        
        map[0] = -1;
        map[1] = 0;
        
        int i = 0;
        int j = 2;
        while (j < map.length) {
            if (pattern.charAt(i) == pattern.charAt(j-1)) {
                map[j++] = ++i;
            }
            else {
                i = 0; //reset value of i to beginning of pattern
                if (pattern.charAt(j-1) == pattern.charAt(0)) {
                    map[j++] = ++i;
                }
                else {
                    map[j++] = 0;
                }
            }
        }
        
        return map;
    }
    
    
    // This is the base to be used for Rabin-Karp. No touchy.
    private static final int BASE = 997;

    /**
     * Find all instances of pattern in text using Rabin-Karp algorithm. Use
     * hashString and updateHash to handle hashing.
     *
     * @param pattern The String to find
     * @param text The String to look through
     * @return A List of starting indices where pattern was found in text
     */
    public static List<Integer> rabinKarp(String pattern, String text) {
        ArrayList<Integer> indicesFound = new ArrayList<>();
        int patLen = pattern.length();
        int hashPat = hashString(pattern); // hash value of pattern
        int hashText; // hash value of text slice
        
        int iTextBegin = 0;
        int iTextEnd = patLen;
        String textSlice;
                
        while (iTextEnd < text.length()) {
            textSlice = text.substring(iTextBegin, iTextEnd);
            hashText = hashString(textSlice);
            if (hashText == hashPat) {
                // brute force compare
                if (textSlice.equals(pattern)) {
                    indicesFound.add(iTextBegin);
                }
            }
            // move to the next character in the String
            ++iTextBegin;
            ++iTextEnd;
        }
        
        return (List<Integer>) indicesFound;
    }

    /**
     * Hashes a string in a specified way. For use with Rabin-Karp.
     *
     * This hash function will use BASE and the indices of the characters. Each
     * character at i is multiplied by BASE raised to the power of length - 1 -
     * i These values are summed to determine the entire hash.
     *
     * For example: Hashing "bunn" as a substring of "bunny" with base 433 hash
     * = b * 433 ^ 3 + u * 433 ^ 2 + n * 433 ^ 1 + n * 433 ^ 0 = 98 * 433 ^ 3 +
     * 117 * 433 ^ 2 + 110 * 433 ^ 1 + 110 * 433 ^ 0 = 7977892179
     *
     * @param pattern The String to be hashed
     * @return A hash value for the string
     */
    public static int hashString(String pattern) {
        double hash = 0;
        int strLen = pattern.length();
        
        for (int i = 0; i < strLen; i++) {
            int charVal = (int) pattern.charAt(i);
            hash += (double)charVal * Math.pow(BASE, strLen - i - 1);
        }
        
        
        if (hash > Integer.MAX_VALUE) {
            hash %= Integer.MAX_VALUE;
        }
        return (int)hash;
    }

    /**
     * Updates the oldHash in a specified way. Follows the same hash formula as
     * hashString. For use with Rabin-Karp.
     *
     * To update the hash, remove the oldChar times BASE raised to the length -
     * 1, multiply by BASE, and add the newChar. For example: Shifting from
     * "bunn" to "unny" in "bunny" with base 433 hash("unny") = (hash("bunn") -
     * b * 433 ^ 3) * 433 + y * 433 ^ 0 = (7977892179 - 98 * 433 ^ 3) * 433 +
     * 121 * 433 ^ 0 = 9519051770
     *
     * @param oldHash The old hash to be updated
     * @param newChar The new character added at the end of the substring
     * @param oldChar The old character being removed from the front of the
     * substring
     * @param length The length of the hashed substring
     */
    public static int updateHash(int oldHash, char newChar, char oldChar,
            int length) {
        double oldCharVal = ((double)oldChar) * Math.pow(BASE, length);
        double newHash = (double)oldHash - oldCharVal + (double)newChar;
        
        if (newHash > Integer.MAX_VALUE) {
            newHash %= Integer.MAX_VALUE;
        }
        
        return (int)newHash;
    }
}