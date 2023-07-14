package org.example.AST;

import java.util.ArrayList;

public class Statements extends Node {
    ArrayList<Node> code;

    public void addNode(Node node) {
        code.add(node);
    }
}
