package hr.fer.evaluator.service;

import hr.fer.evaluator.domain.AttributeDomain;

import java.util.List;

public class Util {
    public interface Comparison{
        <T extends Comparable<T>> boolean compare(T first, T second);
    }
    private static final Comparison NO_QUERY = new Comparison() {
        @Override
        public <T extends Comparable<T>> boolean compare(T first, T second) {
            return true;
        }
    };
    private static final Comparison QUERY_EQUALS = new Comparison() {
        @Override
        public <T extends Comparable<T>> boolean compare(T first, T second) {
            return first.compareTo(second) == 0;
        }
    };
    private static final Comparison QUERY_NOT_EQUALS = new Comparison() {
        @Override
        public <T extends Comparable<T>> boolean compare(T first, T second) {
            return first.compareTo(second) != 0;
        }
    };
    private static final Comparison QUERY_GREATER_THAN = new Comparison() {
        @Override
        public <T extends Comparable<T>> boolean compare(T first, T second) {
            return first.compareTo(second) > 0;
        }
    };
    private static final Comparison QUERY_GREATER_THAN_OR_EQUALS = new Comparison() {
        @Override
        public <T extends Comparable<T>> boolean compare(T first, T second) {
            return first.compareTo(second) >= 0;
        }
    };
    private static final Comparison QUERY_LESS_THAN = new Comparison() {
        @Override
        public <T extends Comparable<T>> boolean compare(T first, T second) {
            return first.compareTo(second) < 0;
        }
    };
    private static final Comparison QUERY_LESS_THAN_OR_EQUALS = new Comparison() {
        @Override
        public <T extends Comparable<T>> boolean compare(T first, T second) {
            return first.compareTo(second) <= 0;
        }
    };
    /**
     * All available comparisons in order:<br>
     *      1 - EQUALS<br>
     *      2 - NOT_EQUALS<br>
     *      3 - GREATER_THAN<br>
     *      4 - GREATER_THAN_OR_EQUALS<br>
     *      5 - LESS_THAN<br>
     *      6 - LESS_THAN_OR_EQUALS<br>
     */
    public static final Comparison[] COMPARISONS = {NO_QUERY ,QUERY_EQUALS, QUERY_NOT_EQUALS, QUERY_GREATER_THAN, QUERY_GREATER_THAN_OR_EQUALS, QUERY_LESS_THAN, QUERY_LESS_THAN_OR_EQUALS};

    public interface Type{
         boolean matches(String input);
         boolean check(String input);
    }

    private static final Type TYPE_INTEGER = new Type() {
        @Override
        public boolean matches(String input) {
            return input.toLowerCase().equals("integer");
        }

        @Override
        public boolean check(String input) {
            return isInteger(input);
        }
    };

    private static final Type TYPE_STRING = new Type() {
        @Override
        public boolean matches(String input) {
            return input.toLowerCase().equals("string");
        }

        @Override
        public boolean check(String input) {
            return isString(input);
        }
    };

    public static final Type[] TYPES ={TYPE_INTEGER, TYPE_STRING};

    public static final int HIGHEST_AVAILABLE_COMPARISONS_FOR_NON_RANGE = 1;

    public static boolean isDouble(String value){
        try {
            Double.parseDouble(value);
        } catch (NumberFormatException | NullPointerException e) {
            return false;
        }
        return true;
    }

    public static boolean isInteger(String value){
        for(int i = 0; i <value.length(); i++){
            if(!Character.isDigit(value.charAt(i)))
                return false;
        }
        return true;
    }

    public static boolean isString(String value){
        for(int i = 0; i <value.length(); i++){
            if(!Character.isLetter(value.charAt(i)))
                return false;
        }
        return true;
    }

}
