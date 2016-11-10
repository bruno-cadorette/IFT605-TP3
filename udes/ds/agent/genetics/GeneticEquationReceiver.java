package udes.ds.agent.genetics;

import udes.ds.agent.AbstractEquation;
import udes.ds.agent.EquationReceiver;

import java.util.List;
import java.util.Random;
import java.util.function.Function;

public abstract class GeneticEquationReceiver<T extends AbstractEquation> extends EquationReceiver<T> {
    public List<Function<T, AbstractEquation>> candidates;
    public int index = 0;

    @Override
    protected AbstractEquation specificAction(T equation) {
        return candidates.get(index).apply(equation);
    }

    @Override
    protected AbstractEquation ComputeEquation(T eq) {
        AbstractEquation answer = super.ComputeEquation(eq);
        float score = TestFac(eq, answer);
        System.out.println(score);
        if (score < 1.0) {
            index = new Random().nextInt(candidates.size());
            index %= candidates.size();
        }
        return answer;
    }
}
