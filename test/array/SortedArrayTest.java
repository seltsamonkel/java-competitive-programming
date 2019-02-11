package array;

import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.DynamicTest.dynamicTest;


class SortedArrayTest {

    @TestFactory
    Collection<DynamicTest> find_found() {
        int[] array = {-1, 2, 4, 5};
        return Arrays.asList(
            dynamicTest("find first",
                () -> assertEquals(SortedArray.find(array, -1), 0)),
            dynamicTest("find last",
                () -> assertEquals(SortedArray.find(array, 5), 3)),
            dynamicTest("find middle",
                () -> assertEquals(SortedArray.find(array, 2), 1))
        );
    }

    @TestFactory
    Collection<DynamicTest> find_not_found() {
        int[] array = {-1, 2, 4, 5};
        return Arrays.asList(
                dynamicTest("find too small",
                        () -> assertEquals(SortedArray.find(array, -2), -1)),
                dynamicTest("find too big",
                        () -> assertEquals(SortedArray.find(array, 6), -1)),
                dynamicTest("find middle",
                        () -> assertEquals(SortedArray.find(array, 3), -1))
        );
    }

    @TestFactory
    Collection<DynamicTest> findInsertIndex_exists() {
        int[] array = {-1, 2, 4, 5};
        return Arrays.asList(
                dynamicTest("find first",
                        () -> assertEquals(SortedArray
                                .findInsertIndex(array, -1), 0)),
                dynamicTest("find last",
                        () -> assertEquals(SortedArray
                                .findInsertIndex(array, 5), 3)),
                dynamicTest("find middle",
                        () -> assertEquals(SortedArray
                                .findInsertIndex(array, 2), 1))
        );
    }

    @TestFactory
    Collection<DynamicTest> findInsertIndex_not_exists() {
        int[] array = {-1, 2, 4, 5};
        return Arrays.asList(
                dynamicTest("smaller than others",
                        () -> assertEquals(SortedArray
                                .findInsertIndex(array, -2), 0)),
                dynamicTest("bigger than others",
                        () -> assertEquals(SortedArray
                                .findInsertIndex(array, 6), array.length)),
                dynamicTest("find middle",
                        () -> assertEquals(SortedArray
                                .findInsertIndex(array, 3), 2))
        );
    }
}