package org.example.AST;

import org.example.Token;

public class BinaryOperations extends Node {
    public Token getbOperator() {
        return bOperator;
    }

    public Node getLeftNode() {
        return leftNode;
    }

    public Node getRightNode() {
        return rightNode;
    }

    private final Token bOperator;
    private final Node leftNode;
    private final Node rightNode;

    public BinaryOperations(Token operator, Node leftNode, Node rightNode) {
        this.bOperator = operator;
        this.leftNode = leftNode;
        this.rightNode = rightNode;
    }
}
