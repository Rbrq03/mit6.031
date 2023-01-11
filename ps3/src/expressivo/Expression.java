/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package expressivo;

import java.util.List;
import java.util.Stack;

//import org.antlr.v4.gui.Trees;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.antlr.v4.runtime.tree.TerminalNode;

import expressivo.parser.*;
import expressivo.parser.ExpressionParser.OperatorContext;
import expressivo.parser.ExpressionParser.PrimitiveContext;
import expressivo.parser.ExpressionParser.RootContext;
//import expressivo.parser.ExpressionParser.OperatorContext;
/**
 * An immutable data type representing a polynomial expression of:
 *   + and *
 *   nonnegative integers and floating-point numbers
 *   variables (case-sensitive nonempty strings of letters)
 * 
 * <p>PS3 instructions: this is a required ADT interface.
 * You MUST NOT change its name or package or the names or type signatures of existing methods.
 * You may, however, add additional methods, or strengthen the specs of existing methods.
 * Declare concrete variants of Expression in their own Java source files.
 */
public interface Expression {
    
    // Datatype definition
    // Expression = Constant(Double) +
    //              Variable(String) +
    //              Add(leftExpr + RightExpr) +
    //              Mutiply(leftExpr + RightExpr)
    
    
    /**
     * Parse an expression.
     * @param input expression to parse, as defined in the PS3 handout.
     * @return expression AST for the input
     * @throws IllegalArgumentException if the expression is invalid
     */
    public static Expression parse(String input) {
        CharStream stream = new ANTLRInputStream(input);
        ExpressionLexer lexer = new ExpressionLexer(stream);
        lexer.reportErrorsAsExceptions();
        TokenStream tokens = new CommonTokenStream(lexer);
        ExpressionParser parser = new ExpressionParser(tokens);
        parser.reportErrorsAsExceptions();
        ParseTree tree = parser.root();
        // Trees.inspect(tree, parser);

        MakeExpression exprMaker = new MakeExpression();
        new ParseTreeWalker().walk(exprMaker, tree);
        return exprMaker.getExpression();
    }
    
    /**
     * @return a parsable representation of this expression, such that
     * for all e:Expression, e.equals(Expression.parse(e.toString())).
     */
    @Override 
    public String toString();

    /**
     * @param thatObject any object
     * @return true if and only if this and thatObject are structurally-equal
     * Expressions, as defined in the PS3 handout.
     */
    @Override
    public boolean equals(Object thatObject);
    
    /**
     * @return hash code value consistent with the equals() definition of structural
     * equality, such that for all e1,e2:Expression,
     *     e1.equals(e2) implies e1.hashCode() == e2.hashCode()
     */
    @Override
    public int hashCode();
    
    /**
     * @return differentitation of the expression which is also a instance of Expression 
     */
    public Expression Differentiate(VariableExpression var);

    /**
     * @return simplify of the expression , which is also a instance of Expression
     */
    public Expression Simplify(VariableExpression var, double varValue);
    
}


class MakeExpression implements ExpressionListener {

    private Stack<Expression> stack = new Stack<>();
    private Stack<Integer> operatorStack = new Stack<>();
    int currentNodemutlNum = 0;
    int currentNUm = 0;

    // Invariant: stack contains the Expression value of each parse
    // subtree that has been fully-walked so far, but whose parent has not yet
    // been exited by the walk. The stack is ordered by recency of visit, so that
    // the top of the stack is the Expression for the most recently walked
    // subtree.
    //
    // At the start of the walk, the stack is empty, because no subtrees have
    // been fully walked.
    //
    // Whenever a node is exited by the walk, the Expression values of its
    // children are on top of the stack, in order with the last child on top. To
    // preserve the invariant, we must pop those child Expression values
    // from the stack, combine them with the appropriate Expression
    // producer, and push back an Expression value representing the entire
    // subtree under the node.
    //
    // At the end of the walk, after all subtrees have been walked and the the
    // root has been exited, only the entire tree satisfies the invariant's
    // "fully walked but parent not yet exited" property, so the top of the stack
    // is the Expression of the entire parse tree.

    /**
     * Return the expression constructed by this listener object.
     * Requires that this listener has completely walked over an Expression
     * parse tree using ParseTreeWalker
     * @return Expression for the parse tree that was walked
     */
    public Expression getExpression() {
        return stack.get(0);
    }

    @Override
    public void exitRoot(RootContext ctx) {}

    @Override
    public void exitOperator(OperatorContext ctx) {
        List<PrimitiveContext> primitiveList = ctx.primitive();
        Expression sum = stack.pop();
        
        for(int i = 1; i < primitiveList.size() - currentNodemutlNum; i++) {
            sum = new AddExpression(sum, stack.pop());
        }

        stack.push(sum);
        currentNodemutlNum =  operatorStack.pop();
    }

    @Override
    public void exitPrimitive(PrimitiveContext ctx) {
        if(ctx.NUMBER() != null) {
            Double n = Double.valueOf(ctx.NUMBER().getText());
            ConstantExpression constN = new ConstantExpression(n);
            if(currentNUm != 0) {
                MutiplyExpression multN = new MutiplyExpression(stack.pop(), constN);
                stack.push(multN);
                currentNUm = currentNUm - 1;
            }
            else {
                stack.push(constN);
            }
        }
        else if(ctx.VARIABLE() != null){
            VariableExpression var = new VariableExpression(ctx.VARIABLE().getText());
            if(currentNUm != 0) {
                MutiplyExpression multN = new MutiplyExpression(stack.pop(), var);
                stack.push(multN);
                currentNUm = currentNUm - 1;
            }
            else {
                stack.push(var);
            }
        }
    }

    @Override public void visitTerminal(TerminalNode arg0) { 
        if(arg0.getText().equals("*")) {
            currentNodemutlNum = currentNodemutlNum + 1;
            currentNUm = currentNUm + 1;
        }
    }

    @Override public void enterOperator(OperatorContext ctx) {
        operatorStack.push(currentNodemutlNum);
        currentNodemutlNum = 0;
        currentNUm = 0;
    }

    @Override public void enterPrimitive(PrimitiveContext ctx) { }
    @Override public void enterRoot(RootContext ctx) { }
    @Override public void enterEveryRule(ParserRuleContext arg0) { }
    @Override public void exitEveryRule(ParserRuleContext arg0) { }
    @Override public void visitErrorNode(ErrorNode arg0) { }

}
