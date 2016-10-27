package udes.ds.agent;

import jade.content.ContentManager;
import jade.core.Agent;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import jade.util.Logger;
import udes.ds.agent.behaviors.SenderBehaviour;

/**
 * Created by root on 16-10-26.
 */
public class EquationSender extends Agent {

    private Logger myLogger = Logger.getMyLogger(getClass().getName());
    private ContentManager manager = (ContentManager) getContentManager();

    @Override
    protected void setup() {
        // Registration with the DF
        DFAgentDescription dfd = new DFAgentDescription();
        ServiceDescription sd = new ServiceDescription();
        sd.setType("HelloAgent");
        sd.setName(getName());
        sd.setOwnership("TILAB");
        dfd.setName(getAID());
        dfd.addServices(sd);
        try {
            DFService.register(this, dfd);
            addBehaviour(new SenderBehaviour(this));
        } catch (FIPAException e) {
            myLogger.log(Logger.SEVERE, "Agent " + getLocalName() + " - Cannot register with DF", e);
            doDelete();
        }
        System.out.println(getAID());

    }
}
