package udes.ds.agent.behaviors;

import jade.content.lang.Codec;
import jade.content.lang.sl.SLCodec;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.SimpleBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.UnreadableException;
import udes.ds.agent.AbstractEquation;
import udes.ds.agent.EquationReceiver;
import udes.ds.agent.Parser;

import java.util.concurrent.ConcurrentLinkedQueue;

public class GeneticSenderBehaviour extends CyclicBehaviour {

    // This agent speaks the SL language
    private Codec codec = new SLCodec();
    // This agent complies with the People ontology
    private boolean finished = false;

    public GeneticSenderBehaviour(Agent a) {
        super(a);
    }

    public void action() {
        try {
            // Preparing the first message
            ConcurrentLinkedQueue<String> qu = ((ConcurrentLinkedQueue<String>) getDataStore().get("queue"));
            ConcurrentLinkedQueue<String> qu_res = ((ConcurrentLinkedQueue<String>) getDataStore().get("queue_response"));
            if (qu.isEmpty()) {
                return;
            }
            String input = qu.poll();
            AbstractEquation eq = Parser.Parse(input);

            ServiceDescription sd1 = new ServiceDescription();
            sd1.setType(eq.Type());
            DFAgentDescription df1 = new DFAgentDescription();
            df1.addServices(sd1);
            DFAgentDescription[] agents = DFService.search(myAgent, df1);

            ACLMessage msg = new ACLMessage(ACLMessage.REQUEST);
            msg.setSender(this.myAgent.getAID());
            msg.addReceiver(agents[0].getName());
            msg.setLanguage(codec.getName());
            // Fill the content of the message
            msg.setContentObject(eq);
            // Send the message
            System.out.println("[" + myAgent.getLocalName() + "] Sending the message...");
            myAgent.send(msg);
            myAgent.addBehaviour(new SimpleBehaviour() {
                boolean finished = false;

                @Override
                public void action() {
                    ACLMessage msg = myAgent.receive();
                    if (msg != null) {
                        try {
                            AbstractEquation eq1 = (AbstractEquation) msg.getContentObject();
                            eq1.printUserReadable();
                            if (EquationReceiver.TestFac(eq, eq1) < 1.0f) {
                                System.out.println("This is not the right answer! We'll requeue the input till it is the case.");
                                qu.add(input);
                            }
                            qu_res.add(eq1.getUserReadableString());

                            finished = true;
                        } catch (UnreadableException e) {
                            e.printStackTrace();
                        }

                    }
                }

                @Override
                public boolean done() {
                    return finished;
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
