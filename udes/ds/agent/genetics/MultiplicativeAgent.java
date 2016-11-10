package udes.ds.agent.genetics;


import udes.ds.agent.AbstractEquation;
import udes.ds.agent.MultiplicativeEquation;
import udes.ds.agent.SummativeEquation;

/**
 * Created by root on 16-10-26.
 */
public class MultiplicativeAgent extends GeneticEquationReceiver<MultiplicativeEquation> {
    protected AbstractEquation specificAction(MultiplicativeEquation equation) {
        MultiplicativeEquation x = new MultiplicativeEquation(derivate(equation.getFirst()), equation.getSecond());
        MultiplicativeEquation y = new MultiplicativeEquation(equation.getFirst(), derivate(equation.getSecond()));
        return new SummativeEquation(x, y);
    }

    @Override
    protected String Type() {
        return MultiplicativeEquation;
    }

}