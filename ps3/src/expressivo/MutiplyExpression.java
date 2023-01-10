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
        return leftExpression.toString() + "*" + rightExpression.toString();
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
}
