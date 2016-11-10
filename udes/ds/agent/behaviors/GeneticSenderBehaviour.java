package udes.ds.agent.behaviors;

import jade.content.lang.Codec;
import jade.content.lang.sl.SLCodec;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.SimpleBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.UnreadableException;
import udes.ds.agent.AbstractEquation;
import udes.ds.agent.Equation;
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
            if (qu.isEmpty()) {
                return;
            }
            String input = qu.poll();
            Equation eq = Parser.Parse(input);
            ACLMessage msg = new ACLMessage(ACLMessage.REQUEST);
            AID receiver = new AID("eq", false);

            msg.setSender(this.myAgent.getAID());
            msg.addReceiver(receiver);
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
