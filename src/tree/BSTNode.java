package tree;

public class BSTNode<T extends Comparable<T>> {
    protected T value;
    protected BSTNode<T> left;
    protected BSTNode<T> right;
    protected BSTNode<T> parent;

    protected BSTNode(T value, BSTNode<T> parent) {
        this.value = value;
        this.parent = parent;
    }

    protected BSTNode<T> createNewNode(T value) {
        return new BSTNode<>(value, this);
    }

    public static <T2 extends Comparable<T2>> BSTNode<T2> createRoot(T2 value) {
        return new BSTNode<>(value, null);
    }

    public T getValue() {
        return value;
    }

    public boolean isRoot() {
        return parent == null;
    }

    public int getHeight() {
        if (right == null && left == null) {
            return 0;
        } else {
            return 1 + Math.max(
                    right == null? 0 : right.getHeight(),
                    left == null? 0 : left.getHeight()
            );
        }
    }

    public BSTNode<T> insert(T value) {
        return search(value, true);
    }

    public BSTNode<T> find(T value) {
        return search(value, false);
    }

    private BSTNode<T> search(T value, boolean createIfNotFound) {
        int compared = this.value.compareTo(value);
        if (compared == 0) {
            return this;
        } else if (compared < 0) {
            if (right == null) {
                if (createIfNotFound) {
                    right = createNewNode(value);
                }
                return right;
            } else {
                return right.search(value, createIfNotFound);
            }
        } else {
            if (left == null) {
                if (createIfNotFound) {
                    left = createNewNode(value);
                }
                return left;
            } else {
                return left.search(value, createIfNotFound);
            }
        }
    }

    public BSTNode<T> getNext() {
        return getAdjacent(true);
    }

    public BSTNode<T> getPrevious() {
        return getAdjacent(false);
    }

    private BSTNode<T> getAdjacent(boolean next) {
        if (next && right != null || !next && left != null) {
            BSTNode<T> current = next ? right : left;
            while (next && current.left != null || !next && current.right != null) {
                current = next ? current.left : current.right;
            }
            return current;
        } else  {
            BSTNode<T> current = this;
            while (current.parent != null &&
                    (next && current.parent.left != current ||
                    !next && current.parent.right != current)) {
                current = current.parent;
            }
            return current.parent;
        }
    }
}
