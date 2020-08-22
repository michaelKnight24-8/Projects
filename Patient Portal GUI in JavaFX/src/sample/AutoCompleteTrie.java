package sample;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

// implementation of the Trie database that shows suggestions of possible employees
// based upon what the user is typing into the "to" section of the message, so that
// the user enters it in correctly, and there is so misspellings
public class AutoCompleteTrie {

    private class Node {
        private char value;
        private HashMap<Character, Node> children = new HashMap<>();
        private boolean isEndOfWord;

        public Node(char value) { this.value = value; }

        public boolean hasChild(char ch) { return children.containsKey(ch); }

        public void addChild(char ch) { children.put(ch, new Node(ch)); }

        public Node getChild(char ch) { return children.get(ch); }

        public Node[] getChildren() { return children.values().toArray(new Node[0]); }
    }
    //root node to start off the trie
    private Node root = new Node(' ');

    //for when we insert the names from the database
    public void insert(String name) {
        var current = root;

        for (var ch : name.toCharArray()) {
            if (!current.hasChild(ch))
                current.addChild(ch);
            current = current.getChild(ch);
        }
        current.isEndOfWord = true;
    }

    public List<String> findNames(String prefix) {
        List<String> names = new ArrayList<>();
        var lastNode = findLastNodeOf(prefix);
        findNames(lastNode, prefix, names);

        return names;
    }

    // find names that include the prefix the user has typed in. For example,
    // if the use has type in 'Ma', then the names 'Madison' or 'Matthew' or 'Mark'
    // would show up as a suggestion, if an employee has that name
    private void findNames(Node root, String prefix, List<String> names) {
        if (root == null)
            return;

        if (root.isEndOfWord)
            names.add(prefix);
        for (var child : root.getChildren())
            findNames(child, prefix + child.value, names);
    }

    //find where the current name ends
    public Node findLastNodeOf(String prefix) {
        if (prefix == null) return null;

        var current = root;
        for (var ch : prefix.toCharArray()) {
            var child = current.getChild(ch);
            if (child == null)
                return null;
            current = child;
        }
        return current;
    }
}
