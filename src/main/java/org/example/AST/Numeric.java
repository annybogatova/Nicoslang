package org.example.AST;

import org.example.Token;

public class Numeric extends Node {
    public Numeric(Token number) {
        this.number = number;
    }

    Token number;
}
