package udes.ds.agent.genetics;

import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import udes.ds.agent.*;

import java.util.ArrayList;
import java.util.stream.Collectors;

public abstract class GeneticEquationReceiver<T extends BinaryEquation> extends EquationReceiver<T> {


    @Override
    protected AbstractEquation specificAction(T equation) {
        return null;
    }

    @Override
    protected AbstractEquation ComputeEquation(T eq) {
        return derivate(eq);
    }

    public AbstractEquation Derivate(AbstractEquation eq) {
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
                System.out.println("Test fact");
                System.out.println(TestFac(eq, eq1));
                return eq1;


            } catch (FIPAException e) {
                e.printStackTrace();
            }


            //Faudrait pouvoir différencer si on recoit une equation a résoudre ou bien une réponse d'un agent

        } catch (Exception e) {
        }
        return null;
    }


    public AbstractEquation derivate(T eq) {
        ArrayList<BinaryEquation> operators = new ArrayList<>();
        operators.add(new SummativeEquation());
        operators.add(new MultiplicativeEquation());
        operators.add(new BasicEquation());
        operators.add(new SubstractEquation());
        AbstractEquation current = null;
        double distance = Double.MAX_VALUE;
        AbstractEquation deriv1 = Derivate(eq.Left);
        AbstractEquation deriv2 = Derivate(eq.Right);
        ArrayList<AbstractEquation> baseParams = new ArrayList<AbstractEquation>() {{
            add(deriv1);
            add(deriv2);
            add(eq.Left);
            add(eq.Right);
            add(new Constant(0));
            add(new Constant(1));
        }};

        ArrayList<AbstractEquation> params = new ArrayList<AbstractEquation>();
        for (AbstractEquation x : baseParams) {
            for (AbstractEquation y : baseParams) {
                params.addAll(operators.stream().map(op -> op.Copy(x, y)).collect(Collectors.toList()));
            }
        }
        for (int op = 0; op < operators.size(); op++) {
            for (int i = 0; i < params.size(); i++) {
                for (int j = 0; j < params.size(); j++) {
                    BinaryEquation answer = operators.get(op).Copy(params.get(i), params.get(j));
                    double value = TestFac(eq, answer);
                    if (value > 0.5)
                        System.out.println(String.format("%s %s score : %s", getName(), answer.getUserReadableString(), value));
                    if (1.0d - value < distance) {
                        distance = 1.0d - value;
                        current = answer;
                    }
                    if (value >= 0.99d) {
                        break;
                    }
                }
            }
        }
        System.out.println("FIN RECEIVE");
        current.printUserReadable();
        return current;
    }
}
