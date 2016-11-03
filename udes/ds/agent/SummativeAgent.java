package udes.ds.agent;


/**
 * Created by root on 16-10-26.
 */
public class SummativeAgent extends EquationReceiver<SummativeEquation> {
    protected AbstractEquation specificAction(SummativeEquation equation){

        return new SummativeEquation(derivate(equation.getFirst()), derivate(equation.getSecond()));
    }

    @Override
    protected String Type() {
        return SummativeEquation;
    }

}