/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package expressivo;

import static org.junit.Assert.*;

import org.junit.Test;

//import java.util.concurrent.TimeUnit;

/**
 * Tests for the Expression abstract data type.
 */
public class ExpressionTest {

    // Testing strategy
    //   TODO
    
    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }
    
    @Test
    public void testConstantExpr() {
        ConstantExpression testConstant = new ConstantExpression(1.2);
        VariableExpression testVar= new VariableExpression("x");
        System.out.println(testConstant);
        System.out.println(testVar);
    }

    @Test
    public void testAddExpr() {
        ConstantExpression testConstant = new ConstantExpression(1.2);
        VariableExpression testVar= new VariableExpression("x");
        AddExpression testAdd = new AddExpression(testConstant, testVar);
        System.out.println(testAdd);
    }

    @Test
    public void testMultExpr() {
        ConstantExpression testConstant = new ConstantExpression(1.2);
        VariableExpression testVar= new VariableExpression("x");
        MutiplyExpression testMult1 = new MutiplyExpression(testConstant, testVar);
        MutiplyExpression testMult2 = new MutiplyExpression(testVar, testMult1);
        System.out.println(testMult1);
        System.out.println(testMult2);
    }
    
    @Test 
    public void testParse() {
        Expression testExpr =  Expression.parse("12+x*y+(5*x)");
        // try{
        //     Thread.currentThread().sleep(3000);
        // }
        // catch(InterruptedException e) { }
        
        assertEquals("5.0*x+x*y+12.0", testExpr.toString());
    }
    
}
