package udes.ds.agent.genetics;


import udes.ds.agent.AbstractEquation;
import udes.ds.agent.Constant;
import udes.ds.agent.EquationReceiver;

/**
 * Created by root on 16-10-26.
 */
public class ConstantAgent extends EquationReceiver<Constant> {
    protected AbstractEquation specificAction(Constant equation) {
        return new Constant(0);
    }

    @Override
    protected String Type() {
        return Constant;
    }

}