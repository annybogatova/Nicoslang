# Nicoslang
Student internship project - Development of a compiled programming language.  

## Compiler structure
1. Lexical analysis (Lexer.java - splits the source code into tokens)
2. Syntactic analysis (Parser.java - create AST)
3. Code generation in assembly (Compiler.java - generate result code)

## Basic Functions
This compiler can handle:
* Assigning values
* Arithmetic operations
  * Plus
  * Minus
  * Multiplication
* Operations in parentheses
* Printing operation

## Modes
There are two modes in which this program can work:  
1. Compilation - after analyzing the source code, assembly code is generated.
2. Simulation - the result of the source code is entered into the console.

## How compiler work
### Code sample  
```java
Xval = 26  
Yval = Xval + 4  
Zval = 40  
Rval = (Xval + (Yval - Zval)) * 2  
print(Rval)
```
### Result in assembly
```assembly
INCLUDE Irvine32.inc
.data
Xval SDWORD ?
Yval SDWORD ?
Rval SDWORD ?
Zval SDWORD ?
.code
main PROC
mov Xval, 26
mov eax, Xval
mov ebx, 4
add eax, ebx
mov Yval, eax
mov Zval, 40
mov eax, Xval
mov ebx, Yval
mov ecx, Zval
sub ebx, ecx
add eax, ebx
mov ebx, 2
imul eax, ebx
mov Rval, eax
mov eax, Rval
mov eax, eax
call WriteDec
exit
main ENDP
END main
```

### Result in console
![image](https://github.com/annybogatova/Nicoslang/assets/96336462/628a9fa7-a0a3-493b-8e22-5c9030de2dea)
