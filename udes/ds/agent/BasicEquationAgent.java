package udes.ds.agent;


import jade.lang.acl.ACLMessage;

/**
 * Created by root on 16-10-26.
 */
public class BasicEquationAgent extends EquationReceiver<BasicEquation> {
    protected AbstractEquation specificAction(BasicEquation equation){
        int exp = equation.getExponent();
        return new BasicEquation(equation.getCoefficient()*exp, exp-1);
    }

    @Override
    protected String Type() {
        return BasicEquation;
    }
}