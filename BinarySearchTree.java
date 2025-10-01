import java.util.ArrayList;
import java.util.List;

public class BinarySearchTree {
    private TreeNode root;

    // Insert a customer into the binary search tree
    public void insert(String key, Customer customer) {
        root = insertRec(root, key, customer);
    }

    // Recursive function to insert a new node
    private TreeNode insertRec(TreeNode root, String key, Customer customer) {
        if (root == null) {
            return new TreeNode(key, customer);
        }

        if (key.compareTo(root.getKey()) < 0) {
            root.setLeft(insertRec(root.getLeft(), key, customer));
        } else if (key.compareTo(root.getKey()) > 0) {
            root.setRight(insertRec(root.getRight(), key, customer));
        } else {
            throw new IllegalArgumentException("Error: Key '" + key + "' already exists.");
        }

        return root;
    }

    // Find a customer by their key (e.g., account number or ID)
    public Customer findCustomer(String key) {
        TreeNode result = findRec(root, key);
        return (result != null) ? result.getCustomer() : null;
    }

    // Recursive search function
    private TreeNode findRec(TreeNode root, String key) {
        if (root == null || root.getKey().equals(key)) {
            return root;
        }

        if (key.compareTo(root.getKey()) < 0) {
            return findRec(root.getLeft(), key);
        }

        return findRec(root.getRight(), key);
    }

    // Get all customers in sorted order
    public List<Customer> getAllCustomers() {
        List<Customer> customers = new ArrayList<>();
        collectCustomersInOrder(root, customers);
        return customers;
    }

    // Helper method to collect customers using in-order traversal
    private void collectCustomersInOrder(TreeNode node, List<Customer> customers) {
        if (node != null) {
            collectCustomersInOrder(node.getLeft(), customers);
            customers.add(node.getCustomer());
            collectCustomersInOrder(node.getRight(), customers);
        }
    }

    // In-order traversal of the tree to print customers in sorted order
    public void inOrderTraversal() {
        inOrderRec(root);
    }

    private void inOrderRec(TreeNode root) {
        if (root != null) {
            inOrderRec(root.getLeft());
            System.out.println(root.getCustomer()); // Assuming Customer class has a proper toString() method
            inOrderRec(root.getRight());
        }
    }
}



