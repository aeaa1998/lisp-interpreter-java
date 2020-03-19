package com.algoritmos;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class InterpreterTest {

    @Test
    void parsePrint() {
        Interpreter interpreter = new Interpreter();
        try{
            interpreter.parse("(print \"here\")");
            assertTrue(true);
        }catch (Exception e){
            assertTrue(false);
        }

    }
    @Test
    void parseCreateAndPrintVariable() {
        Interpreter interpreter = new Interpreter();
        try{
            interpreter.parse("(setq stringVariable \"here\")");
            interpreter.parse("(print stringVariable)");
            assertTrue(true);
        }catch (Exception e){
            assertTrue(false);
        }

    }


    @Test
    void parseCreateAndPrintFunction() {
        Interpreter interpreter = new Interpreter();
        try{
            interpreter.parse("(defun myFun () \"here\")");
            interpreter.parse("(print (myFun))");
            assertTrue(true);
        }catch (Exception e){
            assertTrue(false);
        }

    }
    @Test
    void parseCreateAndPrintFunctionRecursice() {
        Interpreter interpreter = new Interpreter();
        try{
            interpreter.parse("(defun myFun3 (x) " +
                    "(" +
                        "COND " +
                            "((= x 5) (+ x 10))" +
                            "((> x 2) (myFun3 (- x 1)))" +
                            "(T x)" +
                    ")" +
                ")");
            interpreter.parse("(print (myFun3 10))");
            assertTrue(true);
        }catch (Exception e){
            e.printStackTrace();
            assertTrue(false);
        }

    }

    @Test
    void parseCreateAndPrintAtom() {
        Interpreter interpreter = new Interpreter();
        try{
            interpreter.parse("(defun myFun2 (x) " +
                    "(" +
                    "COND " +
                    "((> x 2) (myFun2 (- x 1)))" +
                    "(T x)" +
                    ")" +
                    ")");
            interpreter.parse("(print (ATOM (myFun2 10)))");
            assertTrue(true);
        }catch (Exception e){
            e.printStackTrace();
            assertTrue(false);
        }

    }

    @Test
    void testExtraParentesis() {
        Interpreter interpreter = new Interpreter();
        try{
            interpreter.parse("(print \"a\"))");
            assertTrue(false);
        }catch (Exception e){
            e.printStackTrace();
            assertTrue(true);
        }

    }

    @Test
    void testInvalidCharacter() {
        Interpreter interpreter = new Interpreter();
        try{
            interpreter.parse("(printa \"a\")");
            assertTrue(false);
        }catch (Exception e){
            e.printStackTrace();
            assertTrue(true);
        }
    }

    @Test
    void testConcatenateString() {
        Interpreter interpreter = new Interpreter();
        try{
            interpreter.parse("(print ( Concatenate 'string \"Esto\" \" es\" \" Lisp.\"))");
            assertTrue(true);
        }catch (Exception e){
            e.printStackTrace();
            assertTrue(false);
        }
    }

    @Test
    void testConcatenateList() {
        Interpreter interpreter = new Interpreter();
        try{
            interpreter.parse("(print ( Concatenate 'list '( A B) '(C) \"D\"))");
            assertTrue(true);
        }catch (Exception e){
            e.printStackTrace();
            assertTrue(false);
        }
    }

    @Test
    void testCond() {
        Interpreter interpreter = new Interpreter();
        try{
            interpreter.parse("(print " +
                    "(" +
                    "COND " +
                    "((> 1 2) 1)" +
                    "(T 2)" +
                    ")" +
                    ")");
            assertTrue(true);
        }catch (Exception e){
            e.printStackTrace();
            assertTrue(false);
        }
    }


    @Test
    void testElt() {
        Interpreter interpreter = new Interpreter();
        try{
            interpreter.parse("(print (elt '(a b c) 0))");
            assertTrue(true);
        }catch (Exception e){
            e.printStackTrace();
            assertTrue(false);
        }
    }


    @Test
    void testQuotedlist() {
        Interpreter interpreter = new Interpreter();
        try{
            interpreter.parse("(print '(elt '(a b c) 0))");
            assertTrue(true);
        }catch (Exception e){
            e.printStackTrace();
            assertTrue(false);
        }
    }


    @Test
    void testNormalList() {
        Interpreter interpreter = new Interpreter();
        try{
            interpreter.parse("(print (list (list 1 2 3) 345 6 7))");
            assertTrue(true);
        }catch (Exception e){
            e.printStackTrace();
            assertTrue(false);
        }
    }


    @Test
    void testQuotedValue() {
        Interpreter interpreter = new Interpreter();
        try{
            interpreter.parse("(print 'LOLOP)");
            assertTrue(true);
        }catch (Exception e){
            e.printStackTrace();
            assertTrue(false);
        }
    }


    @Test
    void testBoolean() {
        Interpreter interpreter = new Interpreter();
        try{
            interpreter.parse("(print T)");
            assertTrue(true);
        }catch (Exception e){
            e.printStackTrace();
            assertTrue(false);
        }
    }


    @Test
    void testBooleanOperator() {
        Interpreter interpreter = new Interpreter();
        try{
            interpreter.parse("(print (= 1 2))");
            assertTrue(true);
        }catch (Exception e){
            e.printStackTrace();
            assertTrue(false);
        }
    }

}