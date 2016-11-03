/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package udes.ds.agent;

import java.util.LinkedList;
import java.util.function.BiFunction;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import udes.ds.agent.*;

/**
 *
 * @author cadb1801
 */
public class Parser {
    public static AbstractEquation Parse(String input){
        LinkedList<String> chars = new LinkedList<>();
        Matcher m = Pattern.compile("C|\\(|\\+|\\*|\\)|\\^|-?[0-9]+([.][0-9]+)?").matcher(input);
        while (m.find()) {
            chars.add(m.group());
        }
        return SExpressionParse(chars);
    }

    public static AbstractEquation SExpressionParse(LinkedList<String> xs){
        String x = xs.removeFirst();
        if(x.equals("(")){
            AbstractEquation eq = FunctionParse(xs);
            String y = xs.removeFirst();
            if(y.equals(")")){
                return eq;
            }
        }
        return null;
    }

    private static AbstractEquation FunctionParse(LinkedList<String> xs){
        String x = xs.removeFirst();
        switch(x){
            case "+":
                return BinaryFunctionParse(xs, (a,b) -> new SummativeEquation(a, b));
            case "*":
                return BinaryFunctionParse(xs, (a,b) -> new MultiplicativeEquation(a, b));
            case "^":
                double a = DoubleParser(xs);
                int b = IntParser(xs);
                return new BasicEquation(a, b);
            case "C":
                return new Constant(DoubleParser(xs));
            default:
                return null;
        }
    }
    private static AbstractEquation BinaryFunctionParse(LinkedList<String> xs,
                                                        BiFunction<AbstractEquation, AbstractEquation, AbstractEquation> func){
        AbstractEquation a = SExpressionParse(xs);
        AbstractEquation b = SExpressionParse(xs);
        return func.apply(a, b);
    }

    private static double DoubleParser(LinkedList<String> xs){
        return Double.parseDouble(xs.removeFirst());
    }
    private static int IntParser(LinkedList<String> xs){
        return Integer.parseInt(xs.removeFirst());
    }
}