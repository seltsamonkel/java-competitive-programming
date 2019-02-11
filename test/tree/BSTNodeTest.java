package tree;

import org.junit.Test;
import org.junit.jupiter.api.TestFactory;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import static org.junit.jupiter.api.DynamicTest.dynamicTest;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(Parameterized.class)
public class BSTNodeTest {
    static final int[][] DATA_SOURCE = new int[][]{
            {1, 2, 3, 4, 5, 6, 7, 8, 9, 10},
            {-6, -7, 1, 4, 2, -10, 10, 5}
    };
    static final int[] TREE_HEIGHT = {9, 4};

    @Parameterized.Parameters
    public static Collection<Integer> data() {
        return Arrays.asList(0, 1);
    }

    public int[] data;
    public int[] dataSorted;
    public int dataHeight;
    public BSTNodeTest(Integer dataId) {
        this.data = DATA_SOURCE[dataId];
        this.dataHeight = TREE_HEIGHT[dataId];
        this.dataSorted = this.data.clone();
        Arrays.sort(this.dataSorted);
    }

    private BSTNode<Integer> getBST() {
        BSTNode<Integer> root = null;
        for (int value : data) {
            if (root == null) {
                root = BSTNode.createRoot(data[0]);
            } else {
                root.insert(value);
            }
        }

        return root;
    }

    @Test
    public void insert_returns_inserted_node() {
        BSTNode<Integer> root = BSTNode.createRoot(0);
        BSTNode<Integer>[] inserted = new BSTNode[data.length];
        for (int i = 0; i < data.length; i++) {
            inserted[i] = root.insert(data[i]);
        }

        for (int i = 0; i < data.length; i++) {
            assertEquals(data[i], inserted[i].getValue());
        }
    }

    @Test
    public void insert_returns_existing_node_if_exists() {
        BSTNode<Integer> root = BSTNode.createRoot(0);
        BSTNode<Integer>[] inserted = new BSTNode[data.length];
        for (int i = 0; i < data.length; i++) {
            inserted[i] = root.insert(data[i]);
        }

        for (int i = 0; i < data.length; i++) {
            assertTrue(inserted[i] == root.insert(data[i]));
        }
    }

    @Test
    public void createRoot_returns_root_node() {
        BSTNode<Character> root = BSTNode.createRoot('1');

        assertTrue(root.isRoot());
    }

    @Test
    public void find_returns_node() {
        BSTNode<Integer> root = getBST();

        for (int i = 0; i < data.length; i++) {
            BSTNode<Integer> found = root.find(data[i]);
            assertEquals(found.getValue(), data[i]);
        }
    }

    @Test
    public void find_returns_null_if_not_exists() {
        BSTNode<Integer> root = getBST();
        Random random = new Random();

        int previous = Integer.MIN_VALUE;
        for (int i = 0; i < data.length; i++) {
            if (previous < data[i] - 1) {
                int value  = previous + 1;

                if (data[i] - 1 - previous > 0) {
                    value += random.nextInt(data[i] - 1 - previous);
                }

                assertNull(root.find(value));
            }
        }
    }

    @Test
    public void getNext_returns_next_node() {
        BSTNode<Integer> root = getBST();

        for (int i = 0; i < dataSorted.length - 1; i++) {
            BSTNode<Integer> node = root.find(dataSorted[i]);
            assertEquals(node.getNext().getValue(), dataSorted[i + 1]);
        }
    }

    @Test
    public void getNext_returns_null_if_none() {
        BSTNode<Integer> root = getBST();

        assertNull(root.find(dataSorted[dataSorted.length - 1]).getNext());
    }


    @Test
    public void getPrevious_returns_previous_node() {
        BSTNode<Integer> root = getBST();

        for (int i = dataSorted.length - 1; i > 0; i--) {
            BSTNode<Integer> node = root.find(dataSorted[i]);
            assertEquals(node.getPrevious().getValue(), dataSorted[i - 1]);
        }
    }

    @Test
    public void getPrevious_returns_null_if_none() {
        BSTNode<Integer> root = getBST();

        assertNull(root.find(dataSorted[0]).getPrevious());
    }

    @Test
    public void getHeight() {
        BSTNode<Integer> root = getBST();

        assertEquals(dataHeight, root.getHeight());
    }
}