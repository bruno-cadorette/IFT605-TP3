/**
 * udes.ds.rmi
 * MultiplicativeEquation.java
 * 3 sept. 08
 */
package udes.ds.agent;


/**
 * Stores an equation of the type f(x)*g(x)
 *
 * @author Luc Bergevin
 * @version 1.0
 */
public class MultiplicativeEquation extends AbstractEquation {

    private static final long serialVersionUID = 1L;
    private AbstractEquation _first;
    private AbstractEquation _second;

    public MultiplicativeEquation(AbstractEquation first, AbstractEquation second) {
        super();
        _first = first;
        _second = second;
    }

    public AbstractEquation getFirst() {
        return _first;
    }

    public AbstractEquation getSecond() {
        return _second;
    }

    @Override
    public Object[] getParams() {
        return new Object[]{_first, _second, new Double(0), new Double(1)};
    }

    /**
     * @see udes.ds.rmi.hw.Equation#getFunctionValue(double)
     */
    public double getFunctionValue(double x) {
        return (_first.getFunctionValue(x) * _second.getFunctionValue(x));
    }

    @Override
    public String Type() {
        return EquationReceiver.MultiplicativeEquation;
    }

    /**
     * @see udes.ds.rmi.hw.AbstractEquation#getUserReadableString()
     */
    protected String getUserReadableString() {
        return new String("(" + _first.getUserReadableString() + ")(" + _second.getUserReadableString() + ")");
    }
}
