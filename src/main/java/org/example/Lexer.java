package org.example;

import java.util.ArrayList;

public class Lexer {
    private final String input;
    private int position = 0;
    private final ArrayList<Token> result = new ArrayList<>();

    public Lexer(String input) {
        this.input = input;
    }
    public TokenType getNextTokenType(String input, int position){
        char nextChar = input.charAt(position);
        if(Character.isAlphabetic(nextChar)){
            return TokenType.Variable;
        } else if (Character.isDigit(nextChar) || nextChar == '.') {
            return TokenType.Numeric;
        } else if (nextChar == '\n'){
            return TokenType.NewLine;
        }else if (Character.isWhitespace(nextChar)) {
            return TokenType.Whitespace;
        } else if (nextChar == '"') {
            return TokenType.String;
        } else if (nextChar == '(') {
            return TokenType.LParenthesis;
        } else if (nextChar == ')') {
            return TokenType.RParenthesis;
        } else if (nextChar == '+') {
            return TokenType.Plus;
        } else if (nextChar == '-') {
            return TokenType.Minus;
        } else if (nextChar == '*') {
            return TokenType.Multiplication;
        } else if (nextChar == '/') {
            return TokenType.Division;
        }else if (nextChar == '=') {
            return TokenType.Assign;
        } else
            return TokenType.Operator;
    }
    public ArrayList<Token> tokenize(){
        while (position < input.length()){
            TokenType nextTokenType = getNextTokenType(input, position);
            if (nextTokenType == TokenType.Numeric || nextTokenType == TokenType.Variable){
                StringBuilder tokenValue = new StringBuilder();
                while ( position < input.length() && (getNextTokenType(input, position) == nextTokenType)){
                    tokenValue.append(input.charAt(position));
                    position++;
                }
                result.add(new Token(tokenValue.toString(), nextTokenType, position));
            } else if(nextTokenType == TokenType.Whitespace){
                while (position < input.length() && getNextTokenType(input, position) == TokenType.Whitespace){
                    position++;
                }
            } else if ( nextTokenType == TokenType.NewLine) {
                position++;
                result.add(new Token("\\n", nextTokenType, position));
            } else if (nextTokenType == TokenType.LParenthesis || nextTokenType == TokenType.RParenthesis) {
                position++;
                result.add(new Token(String.valueOf(input.charAt(position)), nextTokenType, position));
            } else if (nextTokenType == TokenType.String) {
                StringBuilder tokenValue = new StringBuilder();
                position++;
                while (position < input.length() && input.charAt(position) != '"'){
                    tokenValue.append(input.charAt(position));
                    position++;
                }
                position++;
                result.add(new Token(tokenValue.toString(), nextTokenType, position));
            } else {
                final String[] ValidOperators = {"<=", ">=", "+=", "-=", "++", "--", "=", "<", ">", "+", "-", "*", "/" };
                for (String operator : ValidOperators){
                    if(input.substring(position).startsWith(operator)){
                        position += operator.length();
                        result.add(new Token(operator, nextTokenType, position));
                        break;
                    }
                }
            }
        }
        return result;
    }
}
