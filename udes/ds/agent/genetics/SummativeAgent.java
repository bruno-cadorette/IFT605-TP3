package udes.ds.agent.genetics;


import udes.ds.agent.AbstractEquation;
import udes.ds.agent.SummativeEquation;

/**
 * Created by root on 16-10-26.
 */
public class SummativeAgent extends GeneticEquationReceiver<SummativeEquation> {
    protected AbstractEquation specificAction(SummativeEquation equation) {

        return new SummativeEquation(derivate(equation.getFirst()), derivate(equation.getSecond()));
    }

    @Override
    protected String Type() {
        return SummativeEquation;
    }

}