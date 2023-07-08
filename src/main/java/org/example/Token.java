package org.example;

public class Token {
    private String tokenValue;
    private TokenType tokenType;

    public String getValue() {
        return tokenValue;
    }

    public TokenType getType() {
        return tokenType;
    }
    public Token(String tokenValue, TokenType tokenType){
        this.tokenValue = tokenValue;
        this.tokenType = tokenType;
    }
}
