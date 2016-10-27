package udes.ds.agent.behaviors;

import jade.content.ContentManager;
import jade.content.abs.*;
import jade.content.lang.Codec;
import jade.content.lang.sl.SLCodec;
import jade.content.lang.sl.SLVocabulary;
import jade.content.onto.BasicOntology;
import jade.content.onto.Ontology;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.SimpleBehaviour;
import jade.lang.acl.ACLMessage;
import jade.util.leap.ArrayList;
import jade.util.leap.List;
import udes.ds.agent.BasicEquation;
import udes.ds.agent.Equation;
import udes.ds.agent.SummativeEquation;

/**
 * Created by root on 16-10-26.
 */
public class SenderBehaviour extends SimpleBehaviour {

    // This agent speaks the SL language
    private Codec codec = new SLCodec();
    // This agent complies with the People ontology
    private boolean finished = false;

    public SenderBehaviour(Agent a) {
        super(a);
    }

    public boolean done() {
        return finished;
    }

    public void action() {
        try {
            // Preparing the first message
            ACLMessage msg = new ACLMessage(ACLMessage.REQUEST);
            AID receiver = new AID("eq", false);

            msg.setSender(this.myAgent.getAID());
            msg.addReceiver(receiver);
            msg.setLanguage(codec.getName());
            Equation eq = new SummativeEquation(new BasicEquation(3,1),new BasicEquation(4,2));
            // Fill the content of the message
            msg.setContentObject(eq);
            // Send the message
            System.out.println("[" + myAgent.getLocalName() + "] Sending the message...");
            myAgent.send(msg);
        } catch (Exception e) {
            e.printStackTrace();
        }

        finished = true;
    }
}
