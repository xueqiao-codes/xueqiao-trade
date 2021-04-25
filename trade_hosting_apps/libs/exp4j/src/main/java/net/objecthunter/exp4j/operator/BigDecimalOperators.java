package net.objecthunter.exp4j.operator;

import java.math.BigDecimal;

public class BigDecimalOperators {
    private static final int INDEX_ADDITION = 0;
    private static final int INDEX_SUBTRACTION = 1;
    private static final int INDEX_MUTLIPLICATION = 2;
    private static final int INDEX_DIVISION = 3;
    private static final int INDEX_POWER = 4;
    private static final int INDEX_MODULO = 5;
    private static final int INDEX_UNARYMINUS = 6;
    private static final int INDEX_UNARYPLUS = 7;

    private static final Operator[] bigdecimalOperators = new Operator[8];
    
    static {
        bigdecimalOperators[INDEX_ADDITION]= new Operator("+", 2, true, Operator.PRECEDENCE_ADDITION) {
            @Override
            public double apply(final double... args) {
                return new BigDecimal(String.valueOf(args[0]))
                        .add(new BigDecimal(String.valueOf(args[1])))
                        .doubleValue();
            }
        };
        bigdecimalOperators[INDEX_SUBTRACTION]= new Operator("-", 2, true, Operator.PRECEDENCE_ADDITION) {
            @Override
            public double apply(final double... args) {
                return new BigDecimal(String.valueOf(args[0]))
                        .subtract(new BigDecimal(String.valueOf(args[1])))
                        .doubleValue();
            }
        };
        bigdecimalOperators[INDEX_UNARYMINUS]= new Operator("-", 1, false, Operator.PRECEDENCE_UNARY_MINUS) {
            @Override
            public double apply(final double... args) {
                return new BigDecimal(0)
                        .subtract(new BigDecimal(String.valueOf(args[0])))
                        .doubleValue();
            }
        };
        bigdecimalOperators[INDEX_UNARYPLUS]= new Operator("+", 1, false, Operator.PRECEDENCE_UNARY_PLUS) {
            @Override
            public double apply(final double... args) {
                return args[0];
            }
        };
        bigdecimalOperators[INDEX_MUTLIPLICATION]= new Operator("*", 2, true, Operator.PRECEDENCE_MULTIPLICATION) {
            @Override
            public double apply(final double... args) {
                return new BigDecimal(String.valueOf(args[0]))
                        .multiply(new BigDecimal(String.valueOf(args[1])))
                        .doubleValue();
            }
        };
        bigdecimalOperators[INDEX_DIVISION]= new Operator("/", 2, true, Operator.PRECEDENCE_DIVISION) {
            @Override
            public double apply(final double... args) {
                if (Double.compare(args[1], 0d) == 0) {
                    throw new ArithmeticException("Division by zero!");
                }
                return new BigDecimal(String.valueOf(args[0]))
                        .divide(new BigDecimal(String.valueOf(args[1])), 5, BigDecimal.ROUND_HALF_UP)
                        .doubleValue();
            }
        };
        bigdecimalOperators[INDEX_POWER]= new Operator("^", 2, false, Operator.PRECEDENCE_POWER) {
            @Override
            public double apply(final double... args) {
                return Math.pow(args[0], args[1]);
            }
        };
        bigdecimalOperators[INDEX_MODULO]= new Operator("%", 2, true, Operator.PRECEDENCE_MODULO) {
            @Override
            public double apply(final double... args) {
                if (args[1] == 0d) {
                    throw new ArithmeticException("Division by zero!");
                }
                return args[0] % args[1];
            }
        };
    }

    public static Operator getBuiltinOperator(final char symbol, final int numArguments) {
        switch(symbol) {
            case '+':
                if (numArguments != 1) {
                    return bigdecimalOperators[INDEX_ADDITION];
                }else{
                    return bigdecimalOperators[INDEX_UNARYPLUS];
                }
            case '-':
                if (numArguments != 1) {
                    return bigdecimalOperators[INDEX_SUBTRACTION];
                }else{
                    return bigdecimalOperators[INDEX_UNARYMINUS];
                }
            case '*':
                return bigdecimalOperators[INDEX_MUTLIPLICATION];
            case '/':
                return bigdecimalOperators[INDEX_DIVISION];
            case '^':
                return bigdecimalOperators[INDEX_POWER];
            case '%':
                return bigdecimalOperators[INDEX_MODULO];
            default:
                return null;
        }
    }
}
