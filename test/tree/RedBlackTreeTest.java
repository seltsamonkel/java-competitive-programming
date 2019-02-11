package tree;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(Parameterized.class)
public class RedBlackTreeTest {
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
    public RedBlackTreeTest(Integer dataId) {
        this.data = DATA_SOURCE[dataId];
        this.dataHeight = TREE_HEIGHT[dataId];
        this.dataSorted = this.data.clone();
        Arrays.sort(this.dataSorted);
    }

    BSTNodeTest bstTest;
    private RedBlackTree<Integer> createTree() {
        RedBlackTree<Integer> tree = new RedBlackTree<>();
        for (int value : data) {
            tree.insert(value);
        }

        return tree;
    }

    @Test
    public void createRoot() {

    }

    @Test
    public void insert_returns_inserted() {
        RedBlackTree<Integer> tree = new RedBlackTree<>();
        for (int value : data) {
            RedBlackTree.Node<Integer> inserted = tree.insert(value);

            assertEquals(inserted.getValue(), value);
        }
    }

    @Test
    public void insert_keeps_tree_balanced() {
        RedBlackTree<Integer> tree = createTree();

        assertTrue(tree.getHeight() <= 2 * Math.log(data.length + 1) / Math.log(2));
    }
}