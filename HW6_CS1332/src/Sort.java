
import java.util.Random;

public class Sort {

    public static <T extends Comparable<T>> void insertionsort(T[] arr) {
        for (int ind = 1; ind < arr.length; ++ind) {
            int ind2 = ind;

            while (ind2 > 0) {
                if (arr[ind2] == null) {
                    // reached the end of the array 
                    break; 
                }
                Comparable thisVal = (Comparable) arr[ind2];
                Comparable prevVal = (Comparable) arr[ind2 - 1];

                if (thisVal.compareTo(prevVal) < 0) {
                    // thisVal < prevVal
                    // swap the two values
                    T temp = (T) thisVal;
                    arr[ind2] = (T) prevVal;
                    arr[--ind2] = temp;

                } 
                else break;
            }
        }
    }

    /**
     * Implement quick sort.
     *
     * Use the provided random object to select your pivots. For example if you
     * need a pivot between a (inclusive) and b (exclusive) where b > a, use the
     * following code:
     *
     * int pivotIndex = r.nextInt(b - a) + a;
     *
     * It should be: inplace
     *
     * Have a worst case running time of: O(n^2)
     *
     * And a best case running time of: O(n log n)
     *
     * @param arr
     */
    public static <T extends Comparable<T>> void quicksort(T[] arr, Random r) {
        // This problem is best solved with recursion
        // helper function needed
        arr = quicksort2(arr, r);
        for (T sorted : arr) {
            System.out.println("sorted = " + sorted);
        }
    }
    
    public static <T extends Comparable<T>> T[] quicksort2(T[] arr, Random r) {
        
        // For small array sizes
        if (arr == null) return null;
        if (arr.length == 1) return arr;
        
        if (arr.length == 2) {
            if (arr[0].compareTo(arr[1]) > 0) {
                T temp = arr[0];
                arr[0] = arr[1];
                arr[1] = temp;
            }
            return arr;
        }
        
        
        // For larger array sizes
        int iLeft = 0;
        int iRight = arr.length - 1;
        int pivotIndex = r.nextInt(iRight - iLeft) + iLeft;
        T[] lesserValues = (T[]) new Comparable[arr.length];
        T[] greaterValues = (T[]) new Comparable[arr.length];
        T[] lesserValues2 = null;
        T[] greaterValues2 = null;
        int numLessThanPivot = 0;
        int numGreaterThanPivot = 0;
        
        // move numbers less than pivot to front of array
        for (int i = 0; i < arr.length; i++) {
            if (arr[i].compareTo(arr[pivotIndex]) < 0) {
                lesserValues[numLessThanPivot++] = arr[i];
            }
            else if (arr[i].compareTo(arr[pivotIndex]) > 0){
                greaterValues[numGreaterThanPivot++] = arr[i];
            }
        }

        
        // reduce lesserValues and greaterValues down to correct size
        if (numLessThanPivot > 0) {
            lesserValues2 = (T[]) new Comparable[numLessThanPivot];
            System.arraycopy(lesserValues, 0, lesserValues2, 0, lesserValues2.length);
        }
        
        if (numGreaterThanPivot > 0) {
            greaterValues2 = (T[]) new Comparable[numGreaterThanPivot];
            System.arraycopy(greaterValues, 0, greaterValues2, 0, greaterValues2.length);
        }
        
        System.out.println("");
        lesserValues2 = quicksort2(lesserValues2, r);
        greaterValues2 = quicksort2(greaterValues2, r);
        
        T[] sortedArray = (T[]) new Comparable[arr.length];
        
        // place values left of pivot
        int k;
        for (k = 0; k < lesserValues2.length; k++) {
            sortedArray[k] = lesserValues2[k];
        }
        
        // place pivot
        sortedArray[k] = arr[pivotIndex];
        
        // place values right of pivot
        for (int i = 0; i < numGreaterThanPivot; i++) {
            int rightIndex = i + 1 + numLessThanPivot;
            sortedArray[rightIndex] = greaterValues2[i];
        }
        
        return sortedArray;
    }
    
    /**
     * Implement merge sort.
     *
     * It should be: stable
     *
     * Have a worst case running time of: O(n log n)
     *
     * And a best case running time of: O(n log n)
     *
     * @param arr
     * @return
     */
    public static <T extends Comparable<T>> T[] mergesort(T[] arr) {
        
        // one-element array
        if (arr.length == 1) return arr; 
        
        // two-element arrays
        if (arr.length == 2) {
            if (arr[0].compareTo(arr[1]) > 0) {
                // swap values 
                T temp = arr[0];
                arr[0] = arr[1];
                arr[1] = temp;
            }
            return arr;
        }
        
        T[] left = (T[]) new Comparable[arr.length / 2];
        T[] right = (T[]) new Comparable[arr.length - left.length];
        
        for (int i = 0; i < left.length; i++) {
            left[i] = arr[i];
        }
        for (int i = 0; i < right.length; i++) {
            right[i] = arr[i + left.length];
        }
        
        T[] leftSorted = mergesort(left);
        T[] rightSorted = mergesort(right);
        
        return merge(leftSorted, rightSorted);
    }
    
    // Note: the following is only for sorted arrays
    private static <T extends Comparable<T>> T[] merge(T[] left, T[] right) {
        T[] newArray = (T[]) new Comparable[left.length + right.length];
        int indL = 0;
        int indR = 0;
        
        for (int indNew = 0; indNew < newArray.length; ++indNew) {
            
            if (indR < right.length && (indL == left.length ||
                    left[indL].compareTo(right[indR]) > 0)) {
                newArray[indNew] = right[indR++];
            }
            else {
                newArray[indNew] = left[indL++];
            }
        }
        
        return newArray;
    }

    /**
     * Implement radix sort
     *
     * Hint: You can use Integer.toString to get a string of the digits. Don't
     * forget to account for negative integers, they will have a '-' at the
     * front of the string.
     *
     * It should be: stable
     *
     * Have a worst case running time of: O(kn)
     *
     * And a best case running time of: O(kn)
     *
     * @param arr
     * @return
     */
    public static int[] radixsort(int[] arr) {

        return arr;
    }
}