package hash;

//import org.junit.jupiter.api.Test;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolutionException;
import org.junit.jupiter.api.extension.ParameterResolver;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(Parameterized.class)
public class HashSetCollectionTest {
    private static int type;
    HashSetCollection<Integer, Integer,? extends Collection<Integer>> getSet() {
        switch (type) {
            case 1:
                return new HashSetCollection<>(new HashSet<>());
            case 2:
                return new HashSetCollection<>(new ArrayList<>());
            default:
                return null;
        }
    }

    @Parameterized.Parameters
    public static Collection<Object> data() {
        return Arrays.asList(1, 2);
    }

    public HashSetCollectionTest(Integer type) {
        HashSetCollectionTest.type = type;
    }

    @Test
    public void get_works() {
        HashSetCollection<Integer, Integer,? extends Collection<Integer>> set =
                getSet();

        set.add(1, 1);
        set.add(2, 1);
        Collection<Integer> gotten = set.get(1);
        assertEquals(1, gotten.size());
        assertTrue(gotten.contains(1));
    }

    @Test
    public void get_returns_null_non_existing_key() {
        HashSetCollection<Integer, Integer,? extends Collection<Integer>> set =
                getSet();

        set.add(1, 1);
        Collection<Integer> gotten = set.get(2);
        assertNull(gotten);
    }

    @Test
    public void add_multiple_items() {
        HashSetCollection<Integer, Integer,? extends Collection<Integer>> set =
                getSet();
        set.add(1, 1);
        set.add(2, 2);
        set.add(1, 3);

        Collection<Integer> gotten = set.get(1);
        assertEquals(2, gotten.size());
        assertTrue(gotten.contains(1));
        assertTrue(gotten.contains(3));
    }

    @Test
    public void add_duplicated_items() {
        HashSetCollection<Integer, Integer,? extends Collection<Integer>> set =
                getSet();
        set.add(1, 1);
        set.add(1, 2);
        set.add(1, 1);

        Collection<Integer> gotten = set.get(1);
        if (gotten instanceof Set) {
            assertEquals(2, gotten.size());
        } else if (gotten instanceof List) {
            assertEquals(3, gotten.size());
            assertEquals(1, ((List<Integer>) gotten).get(0));
            assertEquals(2, ((List<Integer>) gotten).get(1));
            assertEquals(1, ((List<Integer>) gotten).get(2));

        }
        assertTrue(gotten.contains(1));
        assertTrue(gotten.contains(2));
    }

    @Test
    public void size_of_keys() {
        HashSetCollection<Integer, Integer,? extends Collection<Integer>> set =
                getSet();

        set.add(1, 2);
        set.add(2, 1);
        set.add(1, 3);

        assertEquals(2, set.size());
    }

    @Test
    public void size1() {
        HashSetCollection<Integer, Integer,? extends Collection<Integer>> set =
                getSet();

        set.add(1, 2);
        set.add(2, 1);
        set.add(1, 3);

        assertEquals(2, set.size(1));
        assertEquals(1, set.size(2));
    }

    @Test
    public void containsKey_returns_true() {
        HashSetCollection<Integer, Integer,? extends Collection<Integer>> set =
                getSet();

        set.add(1, 2);

        assertTrue(set.containsKey(1));
    }

    @Test
    public void containsKey_returns_false() {
        HashSetCollection<Integer, Integer,? extends Collection<Integer>> set =
                getSet();

        set.add(2, 1);

        assertFalse(set.containsKey(1));
    }

    @Test
    public void removeKey_returns_removed_collection() {
        HashSetCollection<Integer, Integer,? extends Collection<Integer>> set =
                getSet();

        set.add(1, 2);
        set.add(2, 1);
        set.add(1, 1);

        Collection<Integer> removed = set.removeKey(1);
        assertEquals(2, removed.size());
        assertTrue(removed.contains(1));
        assertTrue(removed.contains(1));
        assertFalse(set.containsKey(1));
    }

    @Test
    public void removeKey_returns_null_if_not_exists () {
        HashSetCollection<Integer, Integer,? extends Collection<Integer>> set =
                getSet();;

        set.add(1, 2);
        set.add(2, 1);
        set.add(1, 1);

        Collection<Integer> removed = set.removeKey(3);
        assertNull(removed);
    }

    @Test
    public void removeValue_returns_true () {
        HashSetCollection<Integer, Integer,? extends Collection<Integer>> set =
                getSet();

        set.add(1, 2);
        set.add(2, 1);
        set.add(1, 1);

        boolean found = set.removeValue(1, 1);
        assertTrue(found);
        assertEquals(1, set.size(1));
    }

    @Test
    public void removeValue_returns_false () {
        HashSetCollection<Integer, Integer,? extends Collection<Integer>> set =
                getSet();

        set.add(1, 2);
        set.add(2, 1);
        set.add(1, 1);

        assertFalse(set.removeValue(1, 3));
        assertFalse(set.removeValue(5, 1));
    }

    @Test
    public void removeValue_removes_key_if_collection_becomes_empty() {
        HashSetCollection<Integer, Integer,? extends Collection<Integer>> set =
                getSet();

        set.add(1, 2);
        set.add(2, 1);
        set.add(1, 1);

        set.removeValue(2, 1);
        assertEquals(1, set.size());
    }

    @Test
    public void containsValue_returns_true() {
        HashSetCollection<Integer, Integer,? extends Collection<Integer>> set =
                getSet();

        set.add(1, 2);
        set.add(1,3);

        assertTrue(set.containsValue(1, 2));
        assertTrue(set.containsValue(1, 3));
    }

    @Test
    public void containsValue_returns_false() {
        HashSetCollection<Integer, Integer,? extends Collection<Integer>> set =
                getSet();

        set.add(1, 2);
        set.add(2 ,3);

        assertFalse(set.containsValue(2, 2));
        assertFalse(set.containsValue(1, 3));
    }
}