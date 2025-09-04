package data.structure;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * The type Huffman code.
 */
public class HuffmanCode {
    private String phrase;
    private final Map<Character, Integer> occuranceMap;
    private final Map<Character, String> binaryMap;

    /**
     * Instantiates a new Huffman code.
     */
    public HuffmanCode(){
        this.phrase = "";
        occuranceMap = new LinkedHashMap<>();
        binaryMap = new HashMap<>();
    }

    /**
     * Instantiates a new Huffman code.
     *
     * @param p_phrase the phrase to encode
     */
    public HuffmanCode(String p_phrase) {
        this.phrase = p_phrase;
        occuranceMap = new LinkedHashMap<>();
        createOccuranceMap();
        binaryMap = new HashMap<>();
    }

    /**
     * Set the phrase to be encoded.
     *
     * @param p_phrase the phrase to encode
     */
    public void setPhrase(String p_phrase){
        phrase = p_phrase;
    }

    /**
     * Get the phrase to be encoded.
     *
     * @return the original phrase
     */
    public String getPhrase(){
        return phrase;
    }

    /**
     * Removes duplicate characters from the phrase.
     *
     * @return a string with unique characters from the phrase
     */
    private String phraseSansRep() {
        return phraseSansRepRec(phrase, new StringBuilder(), 0);
    }

    /**
     * Recursive method to build a string with unique characters from the original phrase.
     *
     * @param phrase the original phrase
     * @param unique the string builder for unique characters
     * @param index the current index in the phrase
     * @return the string with unique characters
     */
    private String phraseSansRepRec(String phrase, StringBuilder unique, int index) {
        if (index >= phrase.length()) return unique.toString();

        char currentLetter = phrase.charAt(index);
        if (unique.indexOf(String.valueOf(currentLetter)) == -1) {
            unique.append(currentLetter);
        }
        return phraseSansRepRec(phrase, unique, index + 1);
    }

    /**
     * Creates a map of character occurrences in the phrase.
     */
    public void createOccuranceMap() {
        createOccuranceMapRec(phrase, 0);
    }

    /**
     * Recursive method to populate the occurrence map.
     *
     * @param phrase the phrase
     * @param index the current index in the phrase
     */
    private void createOccuranceMapRec(String phrase, int index) {
        if (index >= phrase.length()) return;

        char currentLetter = phrase.charAt(index);
        occuranceMap.put(currentLetter, occuranceMap.getOrDefault(currentLetter, 0) + 1);
        createOccuranceMapRec(phrase, index + 1);
    }

    /**
     * Gets the list of initial trees (one tree per unique character).
     *
     * @return the initial list of trees
     */
    public Liste getListInitial() {
        String uniquePhrase = phraseSansRep();
        return getListInitialRec(uniquePhrase, 0, new Liste());
    }

    /**
     * Recursive method to build the initial list of trees.
     *
     * @param uniquePhrase the string with unique characters
     * @param index the current index
     * @param arbreList the list of trees
     * @return the updated list of trees
     */
    private Liste getListInitialRec(String uniquePhrase, int index, Liste arbreList) {
        if (index >= uniquePhrase.length()) {
            arbreList.printList();
            return arbreList;
        }

        char letter = uniquePhrase.charAt(index);
        int occurrence = occuranceMap.get(letter);
        Arbre newArbre = new Arbre(letter, occurrence);
        return getListInitialRec(uniquePhrase, index + 1, arbreList.concat(arbreList, new Liste(newArbre)));
    }

    /**
     * Sorts the list of trees by occurrence in ascending order.
     *
     * @param originalList the original list of trees
     * @return the sorted list
     */
    public Liste sortList(Liste originalList) {
        if (originalList.isEmpty()) {
            return new Liste();
        }

        Arbre currentElement = originalList.getElement();
        Liste rest = sortList(originalList.getQueue());

        return insertInOrder(rest, currentElement);
    }

    /**
     * Inserts a tree in order into a sorted list of trees.
     *
     * @param sortedList the sorted list
     * @param currentElement the current tree to insert
     * @return the updated sorted list
     */
    private Liste insertInOrder(Liste sortedList, Arbre currentElement) {
        if (sortedList.isEmpty() || currentElement.getRacine() < sortedList.getElement().getRacine()) {
            return new Liste(currentElement, sortedList);
        } else {
            return new Liste(sortedList.getElement(), insertInOrder(sortedList.getQueue(), currentElement));
        }
    }

    /**
     * Creates the Huffman tree from the list of trees.
     *
     * @param arbreList the list of trees
     * @return the Huffman tree
     */
    public Liste createHuffmanArbre(Liste arbreList) {
        if (arbreList.getSize() <= 1) {
            createBinaryMap(arbreList.getElement(), "");
            return arbreList;
        }

        Arbre first = arbreList.getElement();
        arbreList = arbreList.deleteHead();

        Arbre second = arbreList.getElement();
        arbreList = arbreList.deleteHead();

        Arbre combinedArbre = new Arbre(first.getRacine() + second.getRacine(), first, second);
        first.binaryCode = (first.binaryCode != null ? first.binaryCode : "") + "0";
        second.binaryCode = (second.binaryCode != null ? second.binaryCode : "") + "1";

        arbreList = arbreList.concat(arbreList, new Liste(combinedArbre));
        arbreList = sortList(arbreList);

        return createHuffmanArbre(arbreList);
    }

    /**
     * Creates the binary map for each character in the Huffman tree.
     *
     * @param node the current node
     * @param currentCode the current binary code
     */
    private void createBinaryMap(Arbre node, String currentCode) {
        if (node.isEmpty()) {
            return;
        }
        if (node.getRacineLetter() != null) {
            binaryMap.put(node.getRacineLetter(), currentCode);
        }
        createBinaryMap(node.getLeftChild(), currentCode + "0");
        createBinaryMap(node.getRightChild(), currentCode + "1");
    }

    /**
     * Encodes the phrase using the Huffman binary map.
     *
     * @return the encoded binary string
     */
    public String encode() {
        return encodeRec(phrase, 0, new StringBuilder()).toString();
    }

    /**
     * Recursive method to encode the phrase into binary.
     *
     * @param phrase the original phrase
     * @param index the current index
     * @param codedPhrase the binary-encoded string
     * @return the encoded string
     */
    private StringBuilder encodeRec(String phrase, int index, StringBuilder codedPhrase) {
        if (index >= phrase.length()) return codedPhrase;

        char currentChar = phrase.charAt(index);
        String code = binaryMap.get(currentChar);
        if (code != null) {
            codedPhrase.append(code);
        }
        return encodeRec(phrase, index + 1, codedPhrase);
    }

    /**
     * Decodes a binary string into the original phrase using the binary map.
     *
     * @param codedPhrase the binary-encoded phrase
     * @return the decoded phrase
     */
    public String decodeCodedPhrase(String codedPhrase) {
        StringBuilder decodedPhrase = new StringBuilder();
        StringBuilder currentCode = new StringBuilder();

        for (char bit : codedPhrase.toCharArray()) {
            currentCode.append(bit);

            for (Map.Entry<Character, String> entry : binaryMap.entrySet()) {
                if (entry.getValue().contentEquals(currentCode)) {
                    decodedPhrase.append(entry.getKey());
                    currentCode.setLength(0);
                    break;
                }
            }
        }

        return decodedPhrase.toString();
    }

    /**
     * Encodes the contents of an input file to an output file.
     *
     * @param inputFilePath the input file path
     * @param outputFilePath the output file path
     */
    public void encodeFromFileToFile(String inputFilePath, String outputFilePath) {
        try {
            this.phrase = new String(Files.readAllBytes(Paths.get(inputFilePath)));
            String codedPhrase = encode();

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFilePath))) {
                writer.write(codedPhrase);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Decodes the contents of an input file to an output file.
     *
     * @param inputFilePath the input file path
     * @param outputFilePath the output file path
     */
    public void decodeFromFile(String inputFilePath, String outputFilePath) {
        String codedPhrase;
        try {
            codedPhrase = new String(Files.readAllBytes(Paths.get(inputFilePath)));
            String decodedPhrase = decodeCodedPhrase(codedPhrase);

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFilePath))) {
                writer.write(decodedPhrase);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
