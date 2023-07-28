package org.example;

import org.example.AST.*;

import java.util.HashMap;
import java.util.Map;

public class Simulator {
    private final Map<String, Integer> variables = new HashMap<>();
    private int result;
    public Map<String, Integer> getVariables() {
        return variables;
    }

    public int simulate(Node node) {
        if (node instanceof Statements) {
            for (Node line : ((Statements) node).getCode()) {
                expression(line);
            }
            return result;
        }
        throw new RuntimeException("Statement not found.");
    }
    private void expression(Node node) {
        if (node instanceof BinaryOperations) {
            if (((BinaryOperations) node).getbOperator().getType() == TokenType.Assign) {
                Variable variable = (Variable) ((BinaryOperations) node).getLeftNode();
                int value;
                if (((BinaryOperations) node).getRightNode() instanceof Numeric) {
                    value = Integer.parseInt(((Numeric) ((BinaryOperations) node).getRightNode()).getNumber().getValue());
                } else{
                    result = 0;
                    value = formula(((BinaryOperations) node).getRightNode());
                }
                variables.put(variable.getVariable().getValue(), value);

            } else {
                throw new RuntimeException("Assign operator expected in " + ((BinaryOperations) node).getbOperator().getPosition() + "position.");
            }
        } else if (node instanceof UnaryOperator) {
            if(((UnaryOperator) node).getOperator().getType() == TokenType.Print){
                result = 0;
                result = formula(((UnaryOperator) node).getOperand());
            } else {
                throw new RuntimeException("Unknown unary operator in " + ((UnaryOperator) node).getOperator().getPosition() + "position.");
            }
        } else {
            throw new RuntimeException("Binary or print operation expected.");
        }
    }
    private int formula(Node node) {
        if (node instanceof Numeric) {
            return Integer.parseInt(((Numeric) node).getNumber().getValue());
        } else if (node instanceof Variable) {
            if(variables.containsKey(((Variable) node).getVariable().getValue())){
                return variables.get(((Variable) node).getVariable().getValue());
            } else {
                throw new RuntimeException("Variable " + ((Variable) node).getVariable().getValue() + " is not exist!");
            }
        } else if (node instanceof BinaryOperations){
            switch (((BinaryOperations) node).getbOperator().getType()){
                case Plus -> {
                    return result += formula(((BinaryOperations) node).getLeftNode()) + formula(((BinaryOperations) node).getRightNode());
                }
                case Minus -> {
                    return result += formula(((BinaryOperations) node).getLeftNode()) - formula(((BinaryOperations) node).getRightNode());

                }
                case Multiplication -> {
                    return result += formula(((BinaryOperations) node).getLeftNode()) * formula(((BinaryOperations) node).getRightNode());

                }
//                case Division -> assemblyLines.add("imul " + registers[regCount - 2] + ", " + registers[regCount -1]);
            }
        }
        throw new RuntimeException("Unknown node type");
    }
}
