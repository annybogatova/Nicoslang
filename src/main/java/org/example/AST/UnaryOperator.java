package org.example.AST;

import org.example.Token;

public class UnaryOperator extends Node {
    Token uOperator;
    Node operand;

    public UnaryOperator(Token uOperator, Node operand) {
        this.uOperator = uOperator;
        this.operand = operand;
    }
}
