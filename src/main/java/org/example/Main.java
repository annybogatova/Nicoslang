package org.example;

import org.example.AST.Statements;
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        File file = new File("C:\\Users\\aniad\\IdeaProjects\\Nicoslang\\nicoslang.nsl");
        String inputCode = readFile(file);
        Scanner scanner = new Scanner(System.in);
        System.out.println("Compile or simulate " + file.getName() + " file?\n(Com\\Sim?)");
        String answer = scanner.nextLine();
        if(answer.equals("com")){
            String resultCode = compileCode(inputCode);
            writeFile(resultCode);
            System.out.println("Wanna simulate a program?\nyes\\no");
            if(scanner.nextLine().equals("yes")){
                simulateCode(inputCode);
            } else {
                System.out.println("Good bye!");
            }
        } else if (answer.equals("sim")) {
            simulateCode(inputCode);
        }
    }
    private static String readFile(File file) {
        try (FileReader fr = new FileReader(file); BufferedReader br = new BufferedReader(fr, 200)){
            StringBuilder code = new StringBuilder();
            String line = br.readLine();
            while (line != null){
                code.append(line).append("\n");
                line = br.readLine();
            }
            return code.toString();
        } catch (IOException ex){
            System.out.println(ex.getMessage());
        }
        throw new RuntimeException("Unknown error!");
    }
    private static void writeFile(String resultCode){
        try (FileWriter writer = new FileWriter("result.asm")) {
            writer.write(resultCode);
            writer.flush();
        } catch (IOException ex){
            System.out.println(ex.getMessage());
        }
    }
    private static String compileCode(String code){
        Lexer lexer = new Lexer(code);
        ArrayList<Token> tokenlist = lexer.tokenize();
        Parser parser = new Parser(tokenlist);
        Statements statements = parser.parse();
        Compiler compiler = new Compiler();
        String resultCode = compiler.compile(statements);
        return resultCode;
    }
    private static void simulateCode(String code){
        Lexer lexer = new Lexer(code);
        ArrayList<Token> tokenlist = lexer.tokenize();
        Parser parser = new Parser(tokenlist);
        Statements statements = parser.parse();
        Simulator simulator = new Simulator();
        System.out.println(simulator.simulate(statements));
    }
    private static void printLexemes(ArrayList<Token> tokenList){
        for (Token token : tokenList){
            System.out.println("[" + token.getType() + "] ---> " + token.getValue() + " ---> " + token.getPosition());
        }
    }
}