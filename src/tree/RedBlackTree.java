package tree;

public class RedBlackTree<T extends Comparable<T>> {
    private Node<T> root;

    public RedBlackTree() {

    }

    private void updateRoot() {
        while (root.parent != null) {
            root = (Node<T>)root.parent;
        }
    }

    public Node<T> insert(T value) {
        if (root == null) {
            root = Node.createRoot(value);
            return root;
        } else {
            Node<T> inserted = root.insert(value);
            updateRoot();
            return inserted;
        }
    }

    public int getHeight() {
        return root.getHeight();
    }

    public Node<T> find(T value) {
        return (Node<T>)root.find(value);
    }

    public static class Node<T extends Comparable<T>> extends BSTNode<T> {
        private boolean isRed;

        protected Node(T value, BSTNode<T> parent, boolean isRed) {
            super(value, parent);
            this.isRed = isRed;
        }

        public static <T2 extends Comparable<T2>> Node<T2> createRoot(T2 value) {
            return new Node<>(value, null, false);
        }

        public Node<T> insert(T value) {
            Node<T> inserted = (Node<T>) super.insert(value);
            inserted.reBalance();
            return inserted;
        }

        private void reBalance() {
            Node<T> parent = (Node<T>) this.parent;
            if (isRed && parent != null && parent.isRed) {
                Node<T> grandpa = (Node<T>) parent.parent;
                if (grandpa.has2Red()) {
                    grandpa.recolor();
                    grandpa.reBalance();
                } else {
                    boolean parentRight = parent.right == this;
                    boolean grandpaRight = parent.parent.right == this.parent;
                    if (parentRight == grandpaRight) {
                        grandpa.replace(parent);
                    } else {
                        parent.parent = this;
                        if (parentRight) {
                            parent.right = left;
                            if (left != null) {
                                left.parent = parent;
                            }
                            left = parent;
                        } else {
                            parent.left = right;
                            if (right != null) {
                                right.parent = parent;
                            }
                            right = parent;
                        }
                        grandpa.replace(this);
                    }
                }
            }
        }

        private void recolor() {
            if (parent != null) this.flip();
            if (right != null) ((Node) right).flip();
            if (left != null) ((Node) left).flip();
        }

        private void replaceChild(Node<T> replace, Node<T> with) {
            if (left == replace) left = with;
            else if (right == replace) right = with;
            else assert false;
        }

        private void replace(Node<T> other) {
            Node<T> parent = (Node<T>) this.parent;
            if (parent != null) {
                parent.replaceChild(this, other);
            }
            if (value.compareTo(other.value) > 0) {
                left = other.right;
                if (other.right != null) {
                    other.right.parent = this;
                }
                other.right = this;
            } else {
                right = other.left;
                if (other.left != null) {
                    other.left.parent = this;
                }
                other.left = this;
            }

            other.parent = parent;
            this.parent = other;
            other.flip();
            flip();
        }

        private boolean has2Red() {
            return left != null && ((Node<T>) left).isRed &&
                    right != null && ((Node<T>) right).isRed;
        }

        private void flip() {
            isRed = !isRed;
        }

        @Override
        protected BSTNode<T> createNewNode(T value) {
            return new Node<T>(value, this, true);
        }
    }
}



