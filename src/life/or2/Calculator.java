package life.or2;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Calculator {

    private static final Pattern EXP_PATTERN = Pattern.compile("((\\d*\\.\\d+)|(\\d+)|([\\+\\-\\*/\\(\\)]))");
    private static final Pattern NUM_PATTERN = Pattern.compile("^\\-?[0-9]\\d{0,2}(\\.\\d*)?$");
    private static final Pattern PAR_PATTERN = Pattern.compile("\\(([^)|(]+)\\)");

    private static final List<String> OPENING_BRACKETS = Arrays.asList("{", "[", "(");
    private static final List<String> CLOSING_BRACKETS = Arrays.asList("}", "]", ")");

    private boolean isEvaluatingExpression = false;

    public double evaluateExpression(String expression) throws IllegalExpressionException, IllegalOperationException {
        expression = expression.trim().replace(" ", "");

        if (isNumber(expression)) {
            isEvaluatingExpression = false;
            return toDouble(expression);
        }

        if (!isEvaluatingExpression) {
            if (!checkParenthesis(expression, 0)) {
                throw new IllegalExpressionException(expression);
            }
        }

        isEvaluatingExpression = true;
        expression = normalizeParenthesis(expression);
        expression = "(" + expression + ")";

        Matcher matcher = PAR_PATTERN.matcher(expression);
        if (matcher.find()) {
            String simpleExp = matcher.group();
            return evaluateExpression(expression.replace(simpleExp, Double.toString(evaluateSimpleExpression(simpleExp))));
        }

        return 0;
    }

    public double evaluateSimpleExpression(String simpleExpression) throws IllegalOperationException {
        simpleExpression = removeParenthesis(simpleExpression.trim().replace(" ", ""));
        // System.out.println(simpleExpression);

        if (isNumber(simpleExpression)) {
            return toDouble(simpleExpression);
        }

        Matcher matcher = EXP_PATTERN.matcher(simpleExpression);

        String prevNumber = "", currentOperation = "";
        StringBuilder mergeBuilder = new StringBuilder();
        boolean nextIsNegative = false;

        while (matcher.find()) {
            String token = matcher.group();
            if (nextIsNegative) {
                token = "-" + token;
                nextIsNegative = false;
            }

            if (!prevNumber.equals("") && !currentOperation.equals("")) {
                mergeBuilder.append(token);

                double result = calcOperation(currentOperation, toDouble(prevNumber), toDouble(token));
                return evaluateSimpleExpression(simpleExpression.replace(mergeBuilder.toString(), Double.toString(result)));
            }

            if (isOperation(token) && !prevNumber.equals("")) {
                currentOperation = token;
            } else if (isOperation(token) && prevNumber.equals("")) {
                nextIsNegative = true;
            } else {
                prevNumber = token;
            }

            if (!nextIsNegative) {
                mergeBuilder.append(token);
            }
        }

        return 0;
    }

    public boolean checkParenthesis(String expression, int depth) {
        if (depth < 0 || depth > 3) {
            return false;
        }

        if (depth == 3
                && !expression.contains("(") && !expression.contains(")")
                && !expression.contains("[") && !expression.contains("]")
                && !expression.contains("{") && !expression.contains("}")) {
            return true;
        }

        String startPar = OPENING_BRACKETS.get(depth);
        String endPar = CLOSING_BRACKETS.get(depth);

        int startParIndex = expression.indexOf(startPar);
        int endParIndex = expression.indexOf(endPar);

        if (startParIndex != -1 && endParIndex != -1) {
            return checkParenthesis(expression.substring(startParIndex + 1, endParIndex), depth + 1);
        }

        if (startParIndex != -1 && endParIndex == -1) {
            return false;
        } else return startParIndex != -1 || endParIndex == -1;
    }

    private double calcOperation(String operator, double a, double b) throws IllegalOperationException {
        switch (operator) {
            case "+": return a + b;
            case "-": return a - b;
            case "*": return a * b;
            case "/": {
                if (b == 0) {
                    throw new IllegalOperationException("Division by zero!");
                }
                return a / b;
            }
            default: throw new IllegalOperationException("Don't know what to calculate!");
        }
    }

    private boolean isOperation(String operation) {
        return operation.equals("+") || operation.equals("-") || operation.equals("*") || operation.equals("/");
    }

    private boolean isNumber(String number) {
        return NUM_PATTERN.matcher(removeParenthesis(number)).matches();
    }

    private double toDouble(String number) throws NumberFormatException {
        return Double.parseDouble(removeParenthesis(number.replace("-", ""))) * (number.startsWith("-") ? -1 : 1);
    }

    private String removeParenthesis(String expression) {
        return expression.replace("(", "").replace(")", "");
    }

    private String normalizeParenthesis(String expression) {
        return expression
            .replace("[", "(")
            .replace("{", "(")
            .replace("]", ")")
            .replace("}", ")");
    }

}
