package org.example;

import org.example.AST.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Parser {
    ArrayList<Token> tokenList;
    int position = 0;
    Map<String, String> variable = new HashMap<>();

    public Parser(ArrayList<Token> tokenList) {
        this.tokenList = tokenList;
    }

    // Rval = -Xval + (Yval – Zval)
    // Rval - value
    // = - bin operation
    // -Xval + (Yval – Zval) - formula
    // -Xval - formula
    // (Yval – Zval) - formula
    //
    //       =
    //    /     \
    // Rval       +
    //        /       \
    //      -            -
    //   /   \         /    \
    //  0    Xval     Yval   Zval

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

    public Node parse(){
        Statements statement = new Statements();
        while (position < tokenList.size()){
            Node codeLine = parseLine();
            require(TokenType.NewLine);
            statement.addNode(codeLine);
        }
        return statement;
    }

    // первый элемент строки или переменная или функция
    private Node parseLine() {
        if(match(TokenType.Variable) != null){
            position--;
            Node variable = parseVariableOrNumber();
            if(tokenList.get(position).getType() == TokenType.Assign){
                Token assignToken = match(TokenType.Assign);
                Node formula = parseFormula();
                BinaryOperations binNode = new BinaryOperations(assignToken, variable, formula);
                return  binNode;
            }
        }
        //не переменная - print/if/while/...
        position--;
        return null;
    }

    private Node parseFormula() {
        Node left = parseParenthesis();
        Token operator;
        while ((operator = match(tokenList.get(position).getType())) != null && (operator.getType() == TokenType.Plus || operator.getType() == TokenType.Minus
                || operator.getType() == TokenType.Division || operator.getType() == TokenType.Multiplication) ){

            Node right = parseParenthesis();
            left = new BinaryOperations(operator, left, right);
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
