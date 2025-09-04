package data.structure;

/**
 * The type Liste.
 * Represents a linked list of Huffman tree nodes (Arbre).
 */
public class Liste {
    private Arbre element;
    private Liste queue;
    /**
     * The Empty.
     * A static final instance representing an empty list.
     */
    static final Liste empty = new Liste();

    /**
     * Instantiates a new Liste (empty list).
     */
    public Liste() {
    }

    /**
     * Instantiates a new Liste with a single element.
     *
     * @param p_element the element to be added as the head of the list
     */
    public Liste(Arbre p_element) {
        element = p_element;
        queue = empty;
    }

    /**
     * Instantiates a new Liste with an element and a queue.
     *
     * @param p_element the element to be added as the head of the list
     * @param p_queue   the rest of the list
     */
    public Liste(Arbre p_element, Liste p_queue) {
        element = p_element;
        queue = p_queue;
    }

    /**
     * Instantiates a new Liste using an existing list as the queue.
     *
     * @param p_queue the list to be used as the rest of the new list
     */
    public Liste(Liste p_queue) {
        queue = p_queue;
    }

    /**
     * Checks if the list is empty.
     *
     * @return true if the list is empty, false otherwise
     */
    public boolean isEmpty() {
        return element == null;
    }

    /**
     * Gets the current element of the list.
     *
     * @return the element at the head of the list
     */
    public Arbre getElement() {
        return element;
    }

    /**
     * Gets the queue (the rest) of the list.
     *
     * @return the remaining part of the list
     */
    public Liste getQueue() {
        return queue;
    }

    /**
     * Sets a new queue for the list.
     *
     * @param newqueue the new queue (tail) to set
     * @return the updated list with the new queue
     */
    public Liste setQueue(Liste newqueue) {
        return queue = newqueue;
    }

    /**
     * Gets the size of the list.
     *
     * @return the size (number of elements) in the list
     */
    public int getSize() {
        if (isEmpty()) {
            return 0;
        }
        return 1 + queue.getSize(); // Recursively count elements
    }

    /**
     * Adds an element at the head of the list.
     *
     * @param p_element the element to be added
     * @return the updated list with the new head element
     */
    public Liste addElement(Arbre p_element) {
        if (!isEmpty()) {
            return new Liste(p_element, this);
        }
        element = p_element;
        return this;
    }

    /**
     * Deletes the head element from the list.
     *
     * @return the list without the head element
     */
    public Liste deleteHead() {
        if (!isEmpty()) {
            return queue;
        }
        return empty;
    }

    /**
     * Adds an element at a specific position in the list.
     *
     * @param p_position the position where the element should be added
     * @param p_element  the element to be added
     * @return the updated list with the element inserted at the position
     */
    public Liste addElementInPosition(Integer p_position, Arbre p_element) {
        if (p_position == 0 || isEmpty()) {
            return this.addElement(p_element);
        }
        return new Liste(element, queue.addElementInPosition(p_position - 1, p_element));
    }

    /**
     * Deletes an element at a specific position in the list.
     *
     * @param p_position the position of the element to be deleted
     * @return the updated list with the element removed from the position
     */
    public Liste deleteElementInPosition(Integer p_position) {
        if (!isEmpty()) {
            if (p_position == 0) {
                return this.deleteHead();
            }
            return new Liste(element, queue.deleteElementInPosition(p_position - 1));
        }
        return empty;
    }

    /**
     * Concatenates two lists.
     *
     * @param p_l1 the first list
     * @param p_l2 the second list
     * @return the concatenated list
     */
    public Liste concat(Liste p_l1, Liste p_l2) {
        if (p_l1.isEmpty()) {
            return p_l2;
        }
        if (p_l1.isEmpty() && p_l2.isEmpty()) {
            return empty;
        }

        p_l1.queue = concat(p_l1.getQueue(), p_l2);
        return p_l1;
    }

    /**
     * Checks if an element exists in the list and removes it if found.
     *
     * @param p_element the element to search for
     * @return true if the element was found and removed, false otherwise
     */
    public boolean doublon(Arbre p_element) {
        if (this.isEmpty()) {
            return false;
        }
        if (this.element == p_element) {
            this.deleteHead();
            return true;
        }
        return this.queue.doublon(p_element);
    }

    /**
     * Gets the position of an element in the list.
     *
     * @param p_element the element to search for
     * @return the position of the element in the list, or 0 if not found
     */
    public int getElementInPosition(Arbre p_element) {
        int count = 0;
        if (this.isEmpty()) {
            return 0;
        }
        if (this.element == p_element) {
            return count + this.queue.getElementInPosition(p_element);
        }
        return this.queue.getElementInPosition(p_element);
    }

    /**
     * Prints the list in a human-readable format.
     */
    public void printList() {
        if (isEmpty()) {
            return;
        }

        StringBuilder sb = new StringBuilder("[");
        Liste current = this;
        boolean first = true;

        while (!current.isEmpty()) {
            Arbre currentElement = current.getElement();

            if (currentElement != Arbre.empty) {
                if (!first) {
                    sb.append(", ");
                }
                sb.append("(");
                if (currentElement.getRacineLetter() != null) {
                    sb.append(currentElement.getRacineLetter());
                } else {
                    sb.append(" ");
                }
                sb.append(currentElement.getRacine()).append(")");
                first = false;
            }

            current = current.getQueue();
        }

        sb.append("]");
        System.out.println(sb);
    }

    /**
     * Modifies the racine value of an element at a specific position in the list.
     *
     * @param position  the position of the element to be modified
     * @param newRacine the new value of the racine
     * @return the updated list with the modified element
     */
    public Liste modifyElementInPosition(int position, Integer newRacine) {
        if (position < 0 || position >= this.getSize()) {
            throw new IndexOutOfBoundsException("Position out of bounds");
        }

        Arbre current = this.getElement();
        for (int i = 0; i < position; i++) {
            if (current.getLeftChild() != null) {
                current = current.getLeftChild();
            } else {
                current = current.getRightChild(); 
            }
        }

        current.setRacine(newRacine);
        return this;
    }
}
