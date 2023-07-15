package org.example;

import org.example.AST.Node;
import org.example.AST.Statements;

import java.beans.Statement;
import java.io.*;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        File file = new File("C:\\Users\\aniad\\IdeaProjects\\Nicoslang\\nicoslang.nsl");
        readFile(file);
    }
    private static void readFile(File file) {

        try (FileReader fr = new FileReader(file); BufferedReader br = new BufferedReader(fr, 200)){
            StringBuilder code = new StringBuilder();
            String line = br.readLine();
            while (line != null){
                code.append(line).append("\n");
                line = br.readLine();
            }
            Lexer lexer = new Lexer(code.toString());
            ArrayList<Token> tokenlist = lexer.tokenize();
            for (Token token : tokenlist){
                System.out.println("[" + token.getType() + "] ---> " + token.getValue() + " ---> " + token.getPosition());
            }
            Parser parser = new Parser(tokenlist);
            Statements statements = parser.parse();
            System.out.println(statements.getCode());
        } catch (IOException ex){
            System.out.println(ex.getMessage());
        }

    }


}

