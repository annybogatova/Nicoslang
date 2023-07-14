package org.example.AST;

import org.example.Token;

public class BinaryOperations extends Node {
    Token bOperator;
    Node leftNode;
    Node rightNode;

    public BinaryOperations(Token operator, Node leftNode, Node rightNode) {
        this.bOperator = operator;
        this.leftNode = leftNode;
        this.rightNode = rightNode;
    }
}
