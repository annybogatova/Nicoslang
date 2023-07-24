package org.example.AST;

import org.example.Token;

public class UnaryOperator extends Node {
    private final Token operator;

    private final Node operand;

    public Token getOperator() {
        return operator;
    }

    public Node getOperand() {
        return operand;
    }


    public UnaryOperator(Token operator, Node operand) {
        this.operator = operator;
        this.operand = operand;
    }
}
