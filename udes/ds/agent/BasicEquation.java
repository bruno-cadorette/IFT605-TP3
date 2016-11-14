/**
 * udes.ds.rmi
 * BasicEquation.java
 * 3 sept. 08
 */
package udes.ds.agent;


/**
 * Stores an equation of the type kx^n
 *
 * @author Luc Bergevin
 * @version 1.0
 * @see
 */
public class BasicEquation extends BinaryEquation {

    private static final long serialVersionUID = 1L;
    private AbstractEquation _coefficient;
    private AbstractEquation _exponent;

    public BasicEquation(AbstractEquation coefficient, AbstractEquation exponent) {
        super();
        _coefficient = coefficient;
        _exponent = exponent;
        Left = _coefficient;
        Right = _exponent;
    }

    public BasicEquation() {
    }

    public BasicEquation(double co, double exp) {
        this(new Constant(co), new Constant(exp));
    }

    public double getCoefficient() {
        return _coefficient.getFunctionValue(1);
    }

    public double getExponent() {
        return _exponent.getFunctionValue(1);
    }

    @Override
    public Object[] getParams() {
        return new Object[]{};
    }

    /**
     * @see udes.ds.rmi.hw.Equation#getFunctionValue(double)
     */
    public double getFunctionValue(double x) {
        return ((Math.pow(x, _exponent.getFunctionValue(1))) * _coefficient.getFunctionValue(1));
    }

    @Override
    public String Type() {
        return EquationReceiver.BasicEquation;
    }

    /**
     * @see udes.ds.rmi.hw.AbstractEquation#getUserReadableString()
     */
    protected String getUserReadableString() {
        return new String(Double.toString(_coefficient.getFunctionValue(1)) + "x^" + Double.toString(_exponent.getFunctionValue(1)));
    }

    @Override
    public BinaryEquation Copy(AbstractEquation a, AbstractEquation b) {
        return new BasicEquation(a, b);
    }
}
