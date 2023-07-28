package org.example.AST;

import org.example.Token;

public class Numeric extends Node {
    private final Token number;

    public Token getNumber() {
        return number;
    }

    public Numeric(Token number) {
        this.number = number;
    }
}
