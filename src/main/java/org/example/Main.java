package org.example;

import java.io.*;
import java.util.ArrayList;

public class Main {

    public static TokenType getNextTokenType(String input, int position){
        char nextChar = input.charAt(position);
        if(Character.isAlphabetic(nextChar)){
            return TokenType.Variable;
        } else if (Character.isDigit(nextChar) || nextChar == '.') {
            return TokenType.Numeric;
        } else if (Character.isWhitespace(nextChar)) {
            return TokenType.Whitespace;
        } else if (nextChar == '"') {
            return TokenType.String;
        } else if (nextChar == '(' || nextChar == ')') {
            return TokenType.GroupDivider;
        } else return TokenType.Operator;
    }
    public static ArrayList<Token> tokenizer(String input){
        ArrayList<Token> result = new ArrayList<Token>();
        int position = 0;
        while (position < input.length()){
            TokenType nextTokenType = getNextTokenType(input, position);
            if (nextTokenType == TokenType.Numeric || nextTokenType == TokenType.Variable){
                String tokenValue = "";
                while ( position < input.length() && (getNextTokenType(input, position) == nextTokenType)){
                    tokenValue += input.charAt(position);
                    position++;
                }
                result.add(new Token(tokenValue, nextTokenType));
            } else if(nextTokenType == TokenType.Whitespace){
                while (position < input.length() && (getNextTokenType(input, position) == TokenType.Whitespace)){
                    position++;
                }
            } else if (nextTokenType == TokenType.GroupDivider) {
                result.add(new Token(String.valueOf(input.charAt(position)), nextTokenType));
                position++;
            } else if (nextTokenType == TokenType.String) {
                String tokenValue = "";
                position++;
                while (position < input.length() && input.charAt(position) != '"'){
                    tokenValue += input.charAt(position);
                    position++;
                }
                position++;
                result.add(new Token(tokenValue, nextTokenType));
            } else {
                final String[] ValidOperators = {"<=", ">=", "+=", "-=", "++", "--", "=", "<", ">", "+", "-" };
                for (String operator : ValidOperators){
                    if(input.substring(position).startsWith(operator)){
                        result.add(new Token(operator, TokenType.Operator));
                        position += operator.length();
                        break;
                    }
                }
            }
        }
        return result;
    }
    public static void main(String[] args) {
        File file = new File("C:\\Users\\aniad\\IdeaProjects\\Nicoslang\\nicoslang.nsl");
        SyntaxAnalyzer(file);


    }
    private static void SyntaxAnalyzer(File file) {

        try (FileReader fr = new FileReader(file); BufferedReader br = new BufferedReader(fr, 200)){
            String line = br.readLine();
            while (line != null && !(line.equals("\n") || line.equals("") || line.equals(" "))){
                ArrayList<Token> tokenslist = tokenizer(line);
                for (Token token : tokenslist){
                    System.out.println("[" + token.getType() + "] ---> " + token.getValue());
                }
                line = br.readLine();
            }
        } catch (IOException ex){
            System.out.println(ex.getMessage());
        }

    }


}

