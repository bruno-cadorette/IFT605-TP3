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
import jade.lang.acl.MessageTemplate;
import jade.util.Logger;


/**
 * Created by root on 16-10-26.
 */
public abstract class EquationReceiver<T extends AbstractEquation> extends Agent {
    public static final String Constant = "Constant";
    public static final String BasicEquation = "BasicEquation";
    public static final String MultiplicativeEquation = "MultiplicativeEquation";
    public static final String SummativeEquation = "SummativeEquation";

    public int cpt = 0;

    private Logger myLogger = Logger.getMyLogger(getClass().getName());
    CyclicBehaviour behaviour = new CyclicBehaviour() {
        @Override
        public void action() {
            try {
                //Recevoir
                ACLMessage msg = myAgent.receive();
                if (msg != null) {

                    ACLMessage reply = new ACLMessage(ACLMessage.INFORM);
                    reply.addReceiver(msg.getSender());
                    reply.setSender(this.myAgent.getAID());
                    if (msg.getPerformative() == ACLMessage.REQUEST) {

                        //Envoyer
                        try {
                            T eq1 = (T) msg.getContentObject();
                            AbstractEquation answer = specificAction(eq1);


                            reply.setPerformative(ACLMessage.INFORM);
                            reply.setContentObject(answer);
                            String out = (String.format("Hello %s this is %s, you sent me \" %s \"", msg.getSender().getLocalName(), this.getAgent().getLocalName(), eq1.getUserReadableString()));
                            System.out.println(out);
                        } catch (Exception ex) {
                            //Le cast plus haut n'a pas fonctionné. Bien entendu c'est impossible de le savoir avec instanceof car ça ne fonctionne pas avec les generics
                            //c'est ça qu'on veut car on l'envoit a tout les agents
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
    private ContentManager manager = (ContentManager) getContentManager();

    protected abstract AbstractEquation specificAction(T equation);

    protected abstract String Type();

    @Override
    protected void setup() {
        manager.registerLanguage(new SLCodec(), FIPANames.ContentLanguage.FIPA_SL);
        manager.registerOntology(JADEManagementOntology.getInstance());
        // Registration with the DF
        DFAgentDescription dfd = new DFAgentDescription();
        ServiceDescription sd = new ServiceDescription();
        sd.setType(Type());
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


    public AbstractEquation derivate(AbstractEquation eq) {
        try {
            ServiceDescription sd1 = new ServiceDescription();
            sd1.setType(eq.Type());
            DFAgentDescription df1 = new DFAgentDescription();
            df1.addServices(sd1);

            try {
                DFAgentDescription[] agents = DFService.search(this, df1);
                ACLMessage msg = new ACLMessage(ACLMessage.REQUEST);
                msg.setContentObject(eq);
                msg.addReceiver(agents[0].getName());
                send(msg);
                ACLMessage msg1 = blockingReceive(MessageTemplate.MatchPerformative(ACLMessage.INFORM));
                AbstractEquation eq1 = (AbstractEquation) msg1.getContentObject();
                return eq1;


            } catch (FIPAException e) {
                e.printStackTrace();
            }


            //Faudrait pouvoir différencer si on recoit une equation a résoudre ou bien une réponse d'un agent

        } catch (Exception e) {
        }
        return null;
    }
}
