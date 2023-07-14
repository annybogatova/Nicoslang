package org.example;

public class Token {
    private final String tokenValue;
    private final TokenType tokenType;

    public int getPosition() {
        return position;
    }

    private final int position;

    public Token(String tokenValue, TokenType tokenType, int position) {
        this.tokenValue = tokenValue;
        this.tokenType = tokenType;
        this.position = position;
    }

    public String getValue() {
        return tokenValue;
    }

    public TokenType getType() {
        return tokenType;
    }
}
