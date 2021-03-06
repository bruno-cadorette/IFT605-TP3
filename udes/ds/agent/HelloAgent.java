package udes.ds.agent;

import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import jade.lang.acl.ACLMessage;
import jade.util.Logger;

/**
 * Created by root on 16-10-26.
 */
public class HelloAgent extends Agent {

    private Logger myLogger = Logger.getMyLogger(getClass().getName());
    CyclicBehaviour behaviour = new CyclicBehaviour() {
        @Override
        public void action() {
            ACLMessage msg = myAgent.receive();
            if (msg != null) {
                ACLMessage reply = msg.createReply();

                if (msg.getPerformative() == ACLMessage.REQUEST) {
                    String content = msg.getContent();
                    if (content != null) {
                        myLogger.log(Logger.INFO, "Agent " + getLocalName() + " - Received Message Request from " + msg.getSender().getLocalName());
                        reply.setPerformative(ACLMessage.INFORM);
                        reply.setContent(String.format("Hello %s this is %s, you sent me \" %s \"", msg.getSender().getLocalName(), this.getAgent().getLocalName(),content));
                    } else {
                        myLogger.log(Logger.INFO, "Agent " + getLocalName() + " - Unexpected request [" + content + "] received from " + msg.getSender().getLocalName());
                        reply.setPerformative(ACLMessage.REFUSE);
                        reply.setContent("( UnexpectedContent (" + content + "))");
                    }

                } else {
                    myLogger.log(Logger.INFO, "Agent " + getLocalName() + " - Unexpected message [" + ACLMessage.getPerformative(msg.getPerformative()) + "] received from " + msg.getSender().getLocalName());
                    reply.setPerformative(ACLMessage.NOT_UNDERSTOOD);
                    reply.setContent("( (Unexpected-act " + ACLMessage.getPerformative(msg.getPerformative()) + ") )");
                }
                send(reply);
            } else {
                block();
            }
        }
    };

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
            addBehaviour(behaviour);
        } catch (FIPAException e) {
            myLogger.log(Logger.SEVERE, "Agent " + getLocalName() + " - Cannot register with DF", e);
            doDelete();
        }
    }
}
