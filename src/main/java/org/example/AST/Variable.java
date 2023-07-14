package org.example.AST;

import org.example.Token;

public class Variable extends Node {
    Token variable;

    public Variable(Token variable) {
        super();
        this.variable = variable;
    }
}
