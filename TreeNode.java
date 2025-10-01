public class TreeNode {
    // Key to compare (could be customer ID, account number, or name)
    private String key;
    private Customer customer;
    private TreeNode left, right;

    // Constructor for initializing a tree node with a customer object
    public TreeNode(String key, Customer customer) {
        this.key = key;
        this.customer = customer;
        this.left = null;
        this.right = null;
    }

    // Getters and setters for tree traversal and data access
    public String getKey() {
        return key;
    }

    public Customer getCustomer() {
        return customer;
    }

    public TreeNode getLeft() {
        return left;
    }

    public void setLeft(TreeNode left) {
        this.left = left; // Optionally add a null check: if (left == null) throw new IllegalArgumentException("Left node cannot be null.");
    }

    public TreeNode getRight() {
        return right;
    }

    public void setRight(TreeNode right) {
        this.right = right; // Optionally add a null check: if (right == null) throw new IllegalArgumentException("Right node cannot be null.");
    }

    // Method to compare this node's key with another key
    public int compareTo(String otherKey) {
        return this.key.compareTo(otherKey);
    }

    // Override toString() method for better debugging
    @Override
    public String toString() {
        return "TreeNode{" +
                "key='" + key + '\'' +
                ", customer=" + customer + // Assuming Customer class has a proper toString() method
                ", left=" + (left != null ? left.getKey() : "null") +
                ", right=" + (right != null ? right.getKey() : "null") +
                '}';
    }
}

