package data.structure;

/**
 * The type Arbre represents a binary tree structure.
 */
public class Arbre {
    /**
     * The root value of the tree, which is an integer.
     */
    Integer racine;

    /**
     * The root character value, used for storing letters in the tree.
     */
    Character racineLetter;

    /**
     * The left child subtree of the current node.
     */
    Arbre leftChild;

    /**
     * The right child subtree of the current node.
     */
    Arbre rightChild;

    /**
     * The binary code associated with the node (optional).
     */
    String binaryCode;

    /**
     * Represents an empty tree (used as a sentinel value).
     */
    public static final Arbre empty = new Arbre();

    /**
     * Instantiates a new empty Arbre.
     */
    public Arbre() {
        this.racine = 0;
        this.racineLetter = null;
        this.leftChild = empty;
        this.rightChild = empty;
        this.binaryCode = null;
    }

    /**
     * Instantiates a new Arbre with a root value and child subtrees.
     *
     * @param p_racine     the root value of the tree
     * @param p_leftChild  the left child subtree
     * @param p_rightChild the right child subtree
     */
    public Arbre(int p_racine, Arbre p_leftChild, Arbre p_rightChild) {
        this.racine = p_racine;
        this.racineLetter = null;
        this.leftChild = p_leftChild;
        this.rightChild = p_rightChild;
        this.binaryCode = null;
    }

    /**
     * Instantiates a new Arbre with a root value.
     *
     * @param p_racine the root value of the tree
     */
    public Arbre(int p_racine) {
        this.racine = p_racine;
        this.racineLetter = null;
        this.leftChild = empty;
        this.rightChild = empty;
        this.binaryCode = null;
    }

    /**
     * Instantiates a new Arbre with a root character.
     *
     * @param p_racineLetter the root character of the tree
     */
    public Arbre(char p_racineLetter) {
        this.racine = 0;
        this.racineLetter = p_racineLetter;
        this.leftChild = empty;
        this.rightChild = empty;
        this.binaryCode = null;
    }

    /**
     * Instantiates a new Arbre with a root character and root value.
     *
     * @param p_racineLetter the root character of the tree
     * @param p_racine       the root value of the tree
     */
    public Arbre(char p_racineLetter, int p_racine) {
        this.racine = p_racine;
        this.racineLetter = p_racineLetter;
        this.leftChild = empty;
        this.rightChild = empty;
        this.binaryCode = null;
    }

    /**
     * Checks if the tree is empty.
     *
     * @return true if the tree is empty, false otherwise
     */
    public boolean isEmpty() {
        return this == empty;
    }

    /**
     * Sets the root value of the tree.
     *
     * @param p_racine the new root value of the tree
     */
    public void setRacine(Integer p_racine) {
        racine = p_racine;
    }

    /**
     * Gets the root value of the tree.
     *
     * @return the root value of the tree
     */
    public int getRacine() {
        return racine;
    }

    /**
     * Gets the root character of the tree.
     *
     * @return the root character of the tree
     */
    public Character getRacineLetter() {
        return racineLetter;
    }

    /**
     * Gets the left child subtree of the current node.
     *
     * @return the left child subtree
     */
    public Arbre getLeftChild() {
        return leftChild;
    }

    /**
     * Gets the right child subtree of the current node.
     *
     * @return the right child subtree
     */
    public Arbre getRightChild() {
        return rightChild;
    }

    /**
     * Calculates the size of the tree (number of nodes).
     *
     * @return the size of the tree
     */
    public int size() {
        if (this.isEmpty()) {
            return 0;
        }
        return 1 + leftChild.size() + rightChild.size();
    }

    /**
     * Calculates the height of the tree.
     *
     * @return the height of the tree
     */
    public int height() {
        if (this.isEmpty()) {
            return -1;
        }
        return 1 + Math.max(this.leftChild.height(), this.rightChild.height());
    }

    /**
     * Traverses the tree in prefix order (root, left, right).
     *
     * @return the prefix order string representation of the tree
     */
    public String prefix() {
        if (this.isEmpty()) {
            return "";
        }
        return this.racine + " " + this.leftChild.prefix() + " " + this.rightChild.prefix();
    }

    /**
     * Traverses the tree in postfix order (left, right, root).
     *
     * @return the postfix order string representation of the tree
     */
    public String postfix() {
        if (this.isEmpty()) {
            return "";
        }
        return this.leftChild.postfix() + " " + this.rightChild.postfix() + " " + this.racine;
    }

    /**
     * Traverses the tree in infix order (left, root, right).
     *
     * @return the infix order string representation of the tree
     */
    public String infix() {
        if (this.isEmpty()) {
            return "";
        }
        return this.leftChild.infix() + " " + this.racine + " " + this.rightChild.infix();
    }

    /**
     * Checks if the tree is a Binary Search Tree (ABR).
     *
     * @return true if the tree is a valid binary search tree, false otherwise
     */
    public boolean isABR() {
        if (this.isEmpty()) {
            return true;
        }
        if (!this.leftChild.isEmpty() && this.racine <= this.leftChild.racine) {
            return false;
        }
        if (!this.rightChild.isEmpty() && this.rightChild.racine < this.racine) {
            return false;
        }
        return this.leftChild.isABR() && this.rightChild.isABR();
    }

    /**
     * Checks if the tree is balanced.
     *
     * @return true if the tree is balanced, false otherwise
     */
    public boolean isEqual() {
        if (this.isEmpty()) {
            return true;
        }
        int leftHeight = leftChild.isEmpty() ? 0 : leftChild.height();
        int rightHeight = rightChild.isEmpty() ? 0 : rightChild.height();
        if (Math.abs(leftHeight - rightHeight) > 1) {
            return false;
        }
        return leftChild.isEqual() && rightChild.isEqual();
    }
}
