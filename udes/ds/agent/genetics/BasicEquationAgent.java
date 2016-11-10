package udes.ds.agent.genetics;


import udes.ds.agent.BasicEquation;

import java.util.ArrayList;

/**
 * Created by root on 16-10-26.
 */
public class BasicEquationAgent extends GeneticEquationReceiver<BasicEquation> {

    @Override
    protected void setup() {
        candidates = new ArrayList<>();
        candidates.add(equation -> {
            int exp = equation.getExponent();
            return new BasicEquation(equation.getCoefficient() - exp, exp * 2);
        });
        candidates.add(equation -> {
            int exp = equation.getExponent();
            return new BasicEquation(equation.getCoefficient() * exp, exp - 1);
        });
        candidates.add(equation -> {
            int exp = equation.getExponent();
            return new BasicEquation(equation.getCoefficient() + exp, exp - 4);
        });
        super.setup();
    }

    @Override
    protected String Type() {
        return BasicEquation;
    }
}