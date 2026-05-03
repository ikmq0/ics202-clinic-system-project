package kfupm.clinic.ds;

public class AVLTree<K extends Comparable<K>, V> {

    private class Node {
        K key;
        V value;
        Node left, right;
        int height;

        Node(K key, V value) {
            this.key = key;
            this.value = value;
            this.height = 1;
        }
    }

    private Node root;

    private int height(Node node) {
        return node == null ? 0 : node.height;
    }

    private void updateHeight(Node node) {
        node.height = 1 + Math.max(height(node.left), height(node.right));
    }

    private int balanceFactor(Node node) {
        return node == null ? 0 : height(node.left) - height(node.right);
    }

    private Node rotateRight(Node y) {
        Node x = y.left;
        Node T2 = x.right;

        x.right = y;
        y.left = T2;

        updateHeight(y);
        updateHeight(x);
        return x;
    }

    private Node rotateLeft(Node x) {
        Node y = x.right;
        Node T2 = y.left;

        y.left = x;
        x.right = T2;

        updateHeight(x);
        updateHeight(y);
        return y;
    }

    private Node rebalance(Node node) {
        updateHeight(node);
        int bf = balanceFactor(node);

        if (bf > 1) {
            // Left-Right case
            if (balanceFactor(node.left) < 0)
                node.left = rotateLeft(node.left);
            return rotateRight(node); // Left-Left case
        }

        if (bf < -1) {
            // Right-Left case
            if (balanceFactor(node.right) > 0)
                node.right = rotateRight(node.right);
            return rotateLeft(node); // Right-Right case
        }

        return node; // already balanced
    }

    public void insert(K key, V value) {
        root = insert(root, key, value);
    }

    private Node insert(Node node, K key, V value) {
        if (node == null)
            return new Node(key, value);

        int cmp = key.compareTo(node.key);

        if (cmp < 0)
            node.left = insert(node.left, key, value);
        else if (cmp > 0)
            node.right = insert(node.right, key, value);
        else
            node.value = value; // duplicate key: update value

        return rebalance(node);
    }

    public V search(K key) {
        Node node = root;

        while (node != null) {
            int cmp = key.compareTo(node.key);
            if (cmp < 0)
                node = node.left;
            else if (cmp > 0)
                node = node.right;
            else
                return node.value;
        }

        return null; // not found
    }

    public void delete(K key) {
        root = delete(root, key);
    }

    private Node delete(Node node, K key) {
        if (node == null)
            return null;

        int cmp = key.compareTo(node.key);

        if (cmp < 0)
            node.left = delete(node.left, key);
        else if (cmp > 0)
            node.right = delete(node.right, key);
        else {
            // Node found — handle 3 cases
            if (node.left == null)
                return node.right;
            if (node.right == null)
                return node.left;

            // Two children: replace with smallest node in right subtree
            Node successor = minNode(node.right);
            node.key = successor.key;
            node.value = successor.value;
            node.right = delete(node.right, successor.key);
        }

        return rebalance(node);
    }

    private Node minNode(Node node) {
        while (node.left != null)
            node = node.left;
        return node;
    }

    public void printInOrder() {
        printInOrder(root);
        System.out.println();
    }

    private void printInOrder(Node node) {
        if (node == null)
            return;
        printInOrder(node.left);
        System.out.println(node.key + " -> " + node.value);
        printInOrder(node.right);
    }
}

