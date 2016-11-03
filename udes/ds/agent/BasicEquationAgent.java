package udes.ds.agent;

import jade.content.ContentManager;
import jade.content.lang.sl.SLCodec;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import jade.domain.FIPANames;
import jade.domain.JADEAgentManagement.JADEManagementOntology;
import jade.lang.acl.ACLMessage;
import jade.util.Logger;


/**
 * Created by root on 16-10-26.
 */
public class BasicEquationAgent extends EquationReceiver<BasicEquation> {
    protected AbstractEquation specificAction(BasicEquation equation){
        int exp = equation.getExponent();
        return new BasicEquation(equation.getCoefficient()*exp, exp-1);
    }

}