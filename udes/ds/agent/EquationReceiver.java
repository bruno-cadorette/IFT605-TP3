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
public class EquationReceiver extends Agent {

    private Logger myLogger = Logger.getMyLogger(getClass().getName());
    private ContentManager manager = (ContentManager) getContentManager();

    CyclicBehaviour behaviour = new CyclicBehaviour() {
        @Override
        public void action() {
            try {
                ACLMessage msg = myAgent.receive();
                if (msg != null) {
                    ACLMessage reply = msg.createReply();

                    if (msg.getPerformative() == ACLMessage.REQUEST) {
                        String content = msg.getContent();
                        AbstractEquation eq1 = (AbstractEquation) msg.getContentObject();
                        reply.setPerformative(ACLMessage.INFORM);
                        String out = (String.format("Hello %s this is %s, you sent me \" %s \"", msg.getSender().getLocalName(), this.getAgent().getLocalName(), eq1.getUserReadableString()));
                        reply.setContent(out);
                        System.out.print(out);

                        if (content != null) {
                            myLogger.log(Logger.INFO, "Agent " + getLocalName() + " - Received Message Request from " + msg.getSender().getLocalName());
                            reply.setPerformative(ACLMessage.INFORM);
                            reply.setContent(String.format("Hello %s this is %s, you sent me \" %s \"", msg.getSender().getLocalName(), this.getAgent().getLocalName(), content));
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
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    @Override
    protected void setup() {
        manager.registerLanguage(new SLCodec(), FIPANames.ContentLanguage.FIPA_SL);
        manager.registerOntology(JADEManagementOntology.getInstance());
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
        System.out.println(getAID());

    }
}
