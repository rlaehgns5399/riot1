package univ.lecture.riotapi.model;

/**
 * Created by tchi on 2016. 12. 11..
 */
public enum Operator {
    ADD("+") {
        @Override
        public double evaluate(double firstOperand, double secondOperand) {
            return firstOperand + secondOperand;
        }
    },
    SUB("-") {
        @Override
        public double evaluate(double firstOperand, double secondOperand) {
            return firstOperand - secondOperand;
        }
    },
    MUL("x") {
        @Override
        public double evaluate(double firstOperand, double secondOperand) {
            return firstOperand * secondOperand;
        }
    },
    DIV("/") {
        @Override
        public double evaluate(double firstOperand, double secondOperand) {
            return firstOperand / secondOperand;
        }
    };

    private final String symbol;

    Operator(String symbol) {
        this.symbol = symbol;
    }

    public abstract double evaluate(double firstOperand, double secondOperand);

    public static Operator findOperator(String symbol) {
        for (Operator op : values()) {
            if (op.symbol.equals(symbol)) {
                return op;
            }
        }

        throw new ClassCastException("Invalid operator");
    }
}