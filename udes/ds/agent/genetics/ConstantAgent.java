package udes.ds.agent.genetics;


import udes.ds.agent.*;

import java.util.ArrayList;
import java.util.stream.Collectors;

/**
 * Created by root on 16-10-26.
 */
public class ConstantAgent extends EquationReceiver<Constant> {
    private int key_op = 0;
    private int key_left = 0;
    private int key_right = 0;

    @Override
    protected AbstractEquation ComputeEquation(Constant eq) {
        return derivate(eq);
    }

    public AbstractEquation derivate(Constant eq) {
        ArrayList<BinaryEquation> operators = new ArrayList<>();
        operators.add(new SummativeEquation());
        operators.add(new MultiplicativeEquation());
        operators.add(new BasicEquation());
        operators.add(new SubstractEquation());
        AbstractEquation current = null;
        double distance = Double.MAX_VALUE;
        ArrayList<AbstractEquation> baseParams = new ArrayList<AbstractEquation>() {{
            add(eq);
            add(new Constant(0));
            add(new Constant(1));
        }};

        ArrayList<AbstractEquation> params = new ArrayList<AbstractEquation>();
        for (AbstractEquation x : baseParams) {
            for (AbstractEquation y : baseParams) {
                params.addAll(operators.stream().map(op -> op.Copy(x, y)).collect(Collectors.toList()));
            }
        }

        BinaryEquation answer_maybe = operators.get(key_op).Copy(params.get(key_left), params.get(key_right));
        if (TestFac(eq, answer_maybe) > 0.99) {
            System.out.println("We already know the answer!");
            answer_maybe.printUserReadable();
            return answer_maybe;
        }
        boolean founded = false;
        for (int op = key_op; op < operators.size() && !founded; op++) {
            for (int i = key_left; i < params.size() && !founded; i++) {
                for (int j = key_right; j < params.size() && !founded; j++) {
                    BinaryEquation answer = operators.get(op).Copy(params.get(i), params.get(j));
                    double value = TestFac(eq, answer);
                    if (value > 0.8)
                        System.out.println(String.format("%s %s score : %s", getName(), answer.getUserReadableString(), value));
                    if (1.0d - value < distance) {
                        distance = 1.0d - value;
                        current = answer;
                    }
                    if (value >= 0.99d) {
                        founded = true;
                        key_op = op;
                        key_left = i;
                        key_right = j;
                    }
                }
            }
        }
        return current;
    }

    @Override
    protected AbstractEquation specificAction(Constant equation) {
        return null;
    }

    @Override
    protected String Type() {
        return Constant;
    }

}