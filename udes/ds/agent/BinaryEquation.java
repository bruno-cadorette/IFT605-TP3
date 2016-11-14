package udes.ds.agent;

/**
 * Created by root on 16-11-14.
 */
public abstract class BinaryEquation extends AbstractEquation {
    public AbstractEquation Left;
    public AbstractEquation Right;

    public BinaryEquation() {

    }

    public abstract BinaryEquation Copy(AbstractEquation a, AbstractEquation b);
}