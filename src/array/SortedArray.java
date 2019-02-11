package array;

public class SortedArray {
    public static int find(int[] array, int value) {
        return find(array, value, 0, array.length);
    }

    private static int find(int[] array, int value, int start, int end) {
        int insertIndex = findInsertIndex(array, value, start, end);
        if (insertIndex >= array.length || array[insertIndex] != value) {
            return -1;
        } else {
            return insertIndex;
        }
    }

    public static int findInsertIndex(int[] array, int value) {
        return findInsertIndex(array, value, 0, array.length);
    }

    private static int findInsertIndex(int[] array, int value, int start, int end) {
        if (end == start) {
            return start;
        }
        int center = (start + end) / 2;
        if (array[center] == value) {
            return center;
        } else if (array[center] > value) {
            return findInsertIndex(array, value, start, center);
        } else {
            return findInsertIndex(array, value, center + 1, end);
        }
    }
}
