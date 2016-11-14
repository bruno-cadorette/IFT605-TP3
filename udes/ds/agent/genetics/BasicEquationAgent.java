package udes.ds.agent.genetics;


import udes.ds.agent.BasicEquation;

/**
 * Created by root on 16-10-26.
 */
public class BasicEquationAgent extends GeneticEquationReceiver<BasicEquation> {
    @Override
    protected String Type() {
        return BasicEquation;
    }
}