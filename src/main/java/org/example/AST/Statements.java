package org.example.AST;

import java.util.ArrayList;

public class Statements extends Node {
    private final ArrayList<Node> code = new ArrayList<>();

    public ArrayList<Node> getCode() {
        return code;
    }

    public void addNode(Node node) {
        code.add(node);
    }
}
