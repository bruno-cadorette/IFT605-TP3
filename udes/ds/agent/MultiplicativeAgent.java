package udes.ds.agent;


/**
 * Created by root on 16-10-26.
 */
public class MultiplicativeAgent extends EquationReceiver<MultiplicativeEquation> {
    protected AbstractEquation specificAction(MultiplicativeEquation equation){
        MultiplicativeEquation x = new MultiplicativeEquation(derivate(equation.getFirst()), equation.getSecond());
        MultiplicativeEquation y = new MultiplicativeEquation(equation.getFirst(), derivate(equation.getSecond()));
        return new SummativeEquation(x, y);
    }

    @Override
    protected String Type() {
        return MultiplicativeEquation;
    }

}