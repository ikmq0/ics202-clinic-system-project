import ds.AVLTree;
import ds.HashTable;

public class TestStructures {

    public static void main(String[] args) {
        testHashTable();
        testAVLTree();
    }

    static void testHashTable() {
        System.out.println("=== HashTable Test ===");

        HashTable<String, String> table = new HashTable<>();

        table.put("P101", "Alice");
        table.put("P102", "Bob");
        table.put("P103", "Charlie");

        System.out.println("Get P101: " + table.get("P101")); // Alice
        System.out.println("Get P102: " + table.get("P102")); // Bob
        System.out.println("Get P999: " + table.get("P999")); // null

        table.remove("P102");
        System.out.println("After remove P102: " + table.get("P102")); // null

        table.put("P101", "Alice Updated");
        System.out.println("After update P101: " + table.get("P101")); // Alice Updated

        System.out.println("Size: " + table.size()); // 2
        System.out.println();
    }

    static void testAVLTree() {
        System.out.println("=== AVLTree Test ===");

        AVLTree<String, String> tree = new AVLTree<>();

        tree.insert("2025-01-15 09:00", "Appt with Dr. Smith");
        tree.insert("2025-01-15 08:00", "Appt with Dr. Lee");
        tree.insert("2025-01-16 10:30", "Appt with Dr. Khan");
        tree.insert("2025-01-14 14:00", "Appt with Dr. Ali");

        System.out.println("In-order (should be sorted by date/time):");
        tree.printInOrder();

        System.out.println("Search 2025-01-15 09:00: " + tree.search("2025-01-15 09:00"));
        System.out.println("Search 2025-01-99 00:00: " + tree.search("2025-01-99 00:00")); // null

        tree.delete("2025-01-15 09:00");
        System.out.println("After delete 2025-01-15 09:00:");
        tree.printInOrder();
    }
}
