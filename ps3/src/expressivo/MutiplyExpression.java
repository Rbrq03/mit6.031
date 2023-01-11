package expressivo;

public class MutiplyExpression implements Expression {
    
    private Expression leftExpression;
    private Expression rightExpression;

    public MutiplyExpression(Expression lefExpression, Expression rightExpression) {
        this.leftExpression = lefExpression;
        this.rightExpression = rightExpression;
    }

    @Override
    public String toString() {
        return "(" + leftExpression.toString() + "*" + rightExpression.toString() + ")";
    }

    @Override
    public boolean equals(Object obj) {
        assert(obj instanceof MutiplyExpression);

        MutiplyExpression objMutiply = (MutiplyExpression)obj;
        return objMutiply.leftExpression.equals(this.leftExpression) &&
                objMutiply.rightExpression.equals(rightExpression);
    }

    @Override
    public int hashCode() {
        return leftExpression.hashCode() * rightExpression.hashCode();
    }

    @Override
    public Expression Differentiate(VariableExpression var) {
        return new AddExpression(new MutiplyExpression(leftExpression.Differentiate(var), rightExpression), 
                                 new MutiplyExpression(leftExpression, rightExpression.Differentiate(var)));
    }

    @Override
    public Expression Simplify(VariableExpression var, double value) {
        Expression lefExpressionSimplified = leftExpression.Simplify(var, value);
        Expression rightExpressionSimplified = rightExpression.Simplify(var, value);
        
        if(lefExpressionSimplified instanceof ConstantExpression && rightExpressionSimplified instanceof ConstantExpression) {
            return new ConstantExpression(
                ((ConstantExpression)lefExpressionSimplified).getValue()  * ((ConstantExpression)rightExpressionSimplified).getValue()
            );
        }
        else {
            return new MutiplyExpression(lefExpressionSimplified, rightExpressionSimplified);
        }
    }
}
