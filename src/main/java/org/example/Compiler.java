package org.example;

import org.example.AST.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Compiler {
    private final StringBuilder assemblyCode = new StringBuilder();
    private final ArrayList<String> assemblyLines = new ArrayList<>();
    private final String[] registers = new String[]{"eax", "ebx", "ecx", "edx"};
    private int regCount;

    public Map<String, Integer> getVariables() {
        return variables;
    }

    private final Map<String, Integer> variables = new HashMap<>();

    // Rval = -Xval + (Yval – Zval)
    //
    //         =
    //      /     \
    //   Rval       +
    //          /       \
    //        -            -
    //      /            /    \
    //    Xval         Yval   Zval
    //
    //

    public String compile(Node node) {
        if (node instanceof Statements) {
            for (Node line : ((Statements) node).getCode()) {
                expression(line);
            }
            addCode(assemblyLines);
            return assemblyCode.toString();
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
                    assemblyLines.add("mov " + variable.getVariable().getValue() + ", " + value);
                } else{
                    regCount = 0;
                    value = formula(((BinaryOperations) node).getRightNode());
                    assemblyLines.add("mov "+ variable.getVariable().getValue() + ", " + registers[regCount-1]);
                }
                variables.put(variable.getVariable().getValue(), value);

            } else {
                throw new RuntimeException("Assign operator expected in " + ((BinaryOperations) node).getbOperator().getPosition() + "position.");
            }
        } else if (node instanceof UnaryOperator) {
            if(((UnaryOperator) node).getOperator().getType() == TokenType.Print){
                regCount = 0;
                formula(((UnaryOperator) node).getOperand());
                assemblyLines.add("mov eax, " + registers[regCount - 1] + "\ncall WriteDec");
            } else {
                throw new RuntimeException("Unknown unary operator in " + ((UnaryOperator) node).getOperator().getPosition() + "position.");
            }
        } else {
            throw new RuntimeException("Binary or print operation expected.");
        }
    }
    private int formula(Node node) {
        int res = 0;
        if (node instanceof Numeric) {
            assemblyLines.add("mov " + registers[regCount] + ", " + ((Numeric) node).getNumber().getValue());
            res += Integer.parseInt(((Numeric) node).getNumber().getValue());
            regCount++;
        } else if (node instanceof Variable) {
            if(variables.containsKey(((Variable) node).getVariable().getValue())){
                assemblyLines.add("mov " + registers[regCount] + ", " + ((Variable) node).getVariable().getValue());
                res += variables.get(((Variable) node).getVariable().getValue());
                regCount++;
            } else {
                throw new RuntimeException("Variable " + ((Variable) node).getVariable().getValue() + " is not exist!");
            }
        } else if (node instanceof BinaryOperations){
            switch (((BinaryOperations) node).getbOperator().getType()){
                case Plus -> {
                    res += formula(((BinaryOperations) node).getLeftNode()) + formula(((BinaryOperations) node).getRightNode());
                    assemblyLines.add("add " + registers[regCount - 2] + ", " + registers[regCount - 1]);
                }
                case Minus -> {
                    res += formula(((BinaryOperations) node).getLeftNode()) - formula(((BinaryOperations) node).getRightNode());
                    assemblyLines.add("sub " + registers[regCount - 2] + ", " + registers[regCount - 1]);
                }
                case Multiplication -> {
                    res += formula(((BinaryOperations) node).getLeftNode()) * formula(((BinaryOperations) node).getRightNode());
                    assemblyLines.add("imul " + registers[regCount - 2] + ", " + registers[regCount - 1]);
                }
//                case Division -> assemblyLines.add("imul " + registers[regCount - 2] + ", " + registers[regCount -1]);
            }
            regCount--;
        }
        return res;
    }

    public void addVariables(Map<String, Integer> variables){
        assemblyCode.append("INCLUDE Irvine32.inc\n");
        if(!variables.isEmpty()){
            assemblyCode.append(".data\n");
            for (String key : variables.keySet()){
                assemblyCode.append(key).append(" SDWORD ?").append("\n");
            }
        }
        assemblyCode.append(".code\n" +
                "main PROC\n");
    }
    public void addCode(ArrayList<String> lines){
        addVariables(variables);
        for(String line : lines){
            assemblyCode.append(line).append("\n");
        }
        assemblyCode.append("exit\n" +
                "main ENDP\n" +
                "END main\n");
    }
}
