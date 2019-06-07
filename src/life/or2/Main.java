package life.or2;

public class Main {

    public static void main(String[] args) {
        try {
            Calculator calculator = new Calculator();
            System.out.println("RESULT #1: " + calculator.evaluateExpression(
                "(2 + 3) * 5"
            ));
            System.out.println("RESULT #2: " + calculator.evaluateExpression(
                "30 / { 4 + [ 2 * 9 / ( 8 - 6 - 1 ) ] }"
            ));
            System.out.println("RESULT #3: " + calculator.evaluateExpression(
                "150 / { [ ( 7 + 5 ] - 3 ) + 9 } + 2"
            ));

        } catch (IllegalOperationException e) {
            System.out.println("Illegal operation! Reason: " + e.getReason());
            e.printStackTrace();
        } catch (IllegalExpressionException e) {
            System.out.println("Illegal expression! Expression: " + e.getExpression());
            e.printStackTrace();
        }
    }

}
