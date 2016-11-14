/**
 * udes.ds.rmi
 * SummativeEquation.java
 * 3 sept. 08
 */
package udes.ds.agent;


/**
 * Stores an equation of the type f(x) + g(x)
 *
 * @author Luc Bergevin
 * @version 1.0
 */
public class SubstractEquation extends BinaryEquation {

    private static final long serialVersionUID = 1L;

    public SubstractEquation(AbstractEquation first, AbstractEquation second) {
        super();
        Left = first;
        Right = second;
    }

    public SubstractEquation() {
    }

    public AbstractEquation getFirst() {
        return Left;
    }

    public AbstractEquation getSecond() {
        return Right;
    }

    @Override
    public Object[] getParams() {
        return new Object[]{};
    }

    /**
     * @see udes.ds.rmi.hw.Equation#getFunctionValue(double)
     */
    public double getFunctionValue(double x) {
        return (Left.getFunctionValue(x) - Right.getFunctionValue(x));
    }

    @Override
    public String Type() {
        return EquationReceiver.SubstractEquation;
    }

    /**
     * @see udes.ds.rmi.hw.AbstractEquation#getUserReadableString()
     */
    public String getUserReadableString() {
        return new String(Left.getUserReadableString() + " - " + Right.getUserReadableString());
    }

    @Override
    public BinaryEquation Copy(AbstractEquation a, AbstractEquation b) {
        return new SubstractEquation(a, b);
    }
}
