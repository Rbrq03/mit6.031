package expressivo;

import java.util.Objects;

public class VariableExpression implements Expression{

    private String variable;

    /**
     * Abstraction funcion:
     *  variable(Double)
     * Represent Invariance:
     * 
     * Rep expoure safety:
     *  argument is immutable & private
     */


    public VariableExpression(String data) {
        variable = data;
    }

    @Override
    public String toString() {
        return variable;
    }

    @Override
    public boolean equals(Object obj) {
        assert(obj instanceof VariableExpression);

        VariableExpression objVari = (VariableExpression)obj;
        return objVari.variable.equals(this.variable);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(variable);
    }

    @Override
    public Expression Differentiate(VariableExpression var) {
        if (var.equals(this)) {
            return new ConstantExpression(1.0);
        }
        else {
            return new ConstantExpression(0.0);
        }
        
    }

    @Override
    public Expression Simplify(VariableExpression var, double value) {
        if(var.equals(this)) {
            return new ConstantExpression(value);
        }
        else {
            return new VariableExpression(this.variable);
        }
    }
    
}
