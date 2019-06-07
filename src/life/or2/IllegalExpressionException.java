package life.or2;

public class IllegalExpressionException extends Throwable {

    private String expression;

    public IllegalExpressionException(String expression) {
        this.expression = expression;
    }

    public String getExpression() {
        return this.expression;
    }

}
