package org.example;

import org.example.AST.*;

import java.util.ArrayList;

public class Parser {
    private final ArrayList<Token> tokenList;
    private int position = 0;

    public Parser(ArrayList<Token> tokenList) {
        this.tokenList = tokenList;
    }

    private Token match(TokenType tokenType) {
        if(position < this.tokenList.size()){
            Token current = this.tokenList.get(position);
            if(current.getType() == tokenType){
                position++;
                return current;
            }
        }
        return null;
    }

    //ожидание символа: если "(", ожидаем ")", в конце строки "\n"
    private Token require(TokenType tokenType) {
        Token token = match(tokenType);
        if(token != null){
            return token;
        }
        throw new RuntimeException(tokenType.name() + " expected in " + position + " position");
    }

    public Statements parse(){
        Statements statement = new Statements();
        while (position < tokenList.size()){
            Node codeLine = parseLine();
            statement.addNode(codeLine);
        }
        return statement;
    }

    // первый элемент строки или переменная или функция
    private Node parseLine() {
        if(tokenList.get(position).getType() == TokenType.Variable){
            Node variable = parseVariableOrNumber();
            if(tokenList.get(position).getType() == TokenType.Assign){
                Token assignToken = match(TokenType.Assign);
                Node formula = parseFormula();
                return new BinaryOperations(assignToken, variable, formula);
            } else {
                throw new RuntimeException("Assign operator is missing!");
            }
        } else if (tokenList.get(position).getType() == TokenType.Print) {
            Node printNode = parsePrint();
            return printNode;
        }
        //не переменная - print/if/while/...
        throw new RuntimeException("Invalid character in " + position);
    }

    private Node parsePrint() {
        Token token = match(TokenType.Print);
        if(token!=null){
            return new UnaryOperator(token, parseFormula());
        }
        throw new RuntimeException("Error in " + position);
    }

    // для +- binOperation(operator, left, parenthesis)
    private Node parseFormula() {
        Node left = parseParenthesis();
        Token operator = new Token(tokenList.get(position).getValue(), tokenList.get(position).getType(), position);
        while (operator.getType() != TokenType.NewLine && (operator.getType() == TokenType.Plus
                || operator.getType() == TokenType.Minus || operator.getType() == TokenType.Division
                || operator.getType() == TokenType.Multiplication) ){
            position++;
            Node right = parseParenthesis();
            left = new BinaryOperations(operator, left, right);
            operator = new Token(tokenList.get(position).getValue(), tokenList.get(position).getType(), position);
           }
        if(operator.getType() == TokenType.NewLine){
            position++;
        }
        return left;
    }
    private Node parseParenthesis(){
        if(match(TokenType.LParenthesis) != null){
            Node node = parseFormula();
            require(TokenType.RParenthesis);
            return node;
        } else
            return parseVariableOrNumber();
    }
    private Node parseVariableOrNumber() {
        Token number = match(TokenType.Numeric);
        if (number != null){
            return new Numeric(number);
        } else{
            Token value = match(TokenType.Variable);
            if (value!= null){
                return new Variable(value);
            }
        }
        throw new RuntimeException("Variable or number expected in " + position);
    }

}
