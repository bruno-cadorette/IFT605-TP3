package udes.ds.agent.basicOps;

import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;

/**
 * Created by root on 16-11-12.
 */
public abstract class BaseOps extends Agent {

    public abstract String GetType();

    public abstract Behaviour GetBehaviour();

    @Override
    protected void setup() {
        // Registration with the DF
        DFAgentDescription dfd = new DFAgentDescription();
        ServiceDescription sd = new ServiceDescription();
        sd.setType(GetType());
        sd.setName(getName());
        sd.setOwnership("TILAB");
        dfd.setName(getAID());
        dfd.addServices(sd);

        try {
            DFService.register(this, dfd);
            addBehaviour(GetBehaviour());
        } catch (FIPAException e) {
            doDelete();
        }
        System.out.println(getAID());


    }

    public enum Type {
        Add,
        Product,
        Sub
    }
}
