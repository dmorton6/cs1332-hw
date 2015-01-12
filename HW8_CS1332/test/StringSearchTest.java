import java.util.*;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Ignore;

public class StringSearchTest {

    /**
     * Test of boyerMoore method, of class StringSearch.
     */
    @Test
    @Ignore
    public void testBoyerMoore() {
        String pattern = "aardvark";
        String text = "hello aardvarks aardvarks";
        
        //int[] bmTable = StringSearch.buildBoyerMooreCharTable(pattern);
        List<Integer> matches = StringSearch.boyerMoore(pattern, text);
        
        for (Integer integer : matches) {
            System.out.println("match at index " + integer);
        }
    }
    
    @Test
    @Ignore
    public void testKMP() {
        String pattern = "abcdabcabe";
        String text = "abcdabcabcdabcfabcdabcabe";
        
        List<Integer> indices = StringSearch.kmp(pattern, text);
        
        System.out.println("indices.size() = " + indices.size());
        int x = indices.get(0);
        System.out.println("x = " + x);
    }

    @Test
    public void testRK() {        
        //int hash = StringSearch.hashString("abc");
        String pattern = "ar";
        String text = "aardvark";
        
        List<Integer> matches = StringSearch.rabinKarp(pattern, text);
        
        for (Integer integer : matches) {
            System.out.println("integer = " + integer);
        }
        
    }
}