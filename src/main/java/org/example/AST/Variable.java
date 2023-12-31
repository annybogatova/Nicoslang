package org.example.AST;

import org.example.Token;

public class Variable extends Node {
    private final Token variable;

    public Variable(Token variable) {
        super();
        this.variable = variable;
    }

    public Token getVariable() {
        return variable;
    }
}
