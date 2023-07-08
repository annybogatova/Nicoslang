package org.example;

public enum TokenType {
    Whitespace, // \s\t\n\r
    Numeric, // real numbers
    String,
    GroupDivider,
    Operator,
    Variable;
}
