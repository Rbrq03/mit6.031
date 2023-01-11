package expressivo;

public class AddExpression implements Expression{

    private Expression leftExpression;
    private Expression rightExpression;

    /**
     * Abstraction function:
     *  AF(leftExpression, rightExpression) = Expr: leftExpression + rightExpression
     * Represent Invariance:
     * 
     * Rep Exposure safety:
     *  all argument are private & no producer will return a new instance
     */

    public AddExpression(Expression lefExpression, Expression rightExpression) {
        this.leftExpression = lefExpression;
        this.rightExpression = rightExpression;
    }
    
    @Override
    public String toString() {
        return "(" + leftExpression.toString() + "+" + rightExpression.toString() + ")";
    }

    @Override
    public boolean equals(Object obj) {
        assert(obj instanceof AddExpression);

        AddExpression objExpr = (AddExpression)obj;
        return objExpr.leftExpression.equals(this.leftExpression) &&
                objExpr.rightExpression.equals(this.rightExpression);
    }

    @Override
    public int hashCode() {
        return leftExpression.hashCode() + rightExpression.hashCode();
    }
    
    @Override
    public Expression Differentiate(VariableExpression var) {
        return new AddExpression(leftExpression.Differentiate(var), 
                                rightExpression.Differentiate(var));
    }

    @Override
    public Expression Simplify(VariableExpression var, double value) {
        Expression lefExpressionSimplified = leftExpression.Simplify(var, value);
        Expression rightExpressionSimplified = rightExpression.Simplify(var, value);
        
        if(lefExpressionSimplified instanceof ConstantExpression && rightExpressionSimplified instanceof ConstantExpression) {
            return new ConstantExpression(
                ((ConstantExpression)lefExpressionSimplified).getValue() + ((ConstantExpression)rightExpressionSimplified).getValue()
            );
        }
        else {
            return new AddExpression(lefExpressionSimplified, rightExpressionSimplified);
        }
    }
    
}
