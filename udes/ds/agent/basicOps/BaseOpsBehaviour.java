package udes.ds.agent.basicOps;

import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.UnreadableException;
import udes.ds.agent.AbstractEquation;

import java.io.IOException;

/**
 * Created by root on 16-11-12.
 */
public abstract class BaseOpsBehaviour extends CyclicBehaviour {

    public abstract BaseOpsMessage DoOperation(AbstractEquation eq);

    @Override
    public void action() {
        ACLMessage msg = myAgent.receive();
        try {
            BaseOpsMessage opsMsg = (BaseOpsMessage) msg.getContentObject();
            ACLMessage reply = msg.createReply();
            reply.setContentObject(DoOperation(opsMsg.op1));
            myAgent.send(reply);

        } catch (UnreadableException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
