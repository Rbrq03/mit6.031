package expressivo;

import java.util.Objects;

public class ConstantExpression implements Expression {
    
    private Double constNumber;

    public ConstantExpression(Double constNum) {
        this.constNumber = constNum;
    }

    @Override
    public String toString() {
        return String.valueOf(constNumber);
    }

    @Override
    public boolean equals(Object obj) {
        assert(obj instanceof ConstantExpression);

        ConstantExpression objConstant = (ConstantExpression)obj;
        return objConstant.constNumber == this.constNumber;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(constNumber);
    }

    @Override
    public Expression Differentiate(VariableExpression var) {
        return new ConstantExpression(0.0);
    }

    @Override
    public Expression Simplify(VariableExpression var, double value) {
        return new ConstantExpression(this.constNumber);
    }

    public double getValue() {
        return this.constNumber;
    }
}
