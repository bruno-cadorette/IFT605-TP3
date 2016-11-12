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
public class BasicEquation extends AbstractEquation {

    private static final long serialVersionUID = 1L;
    private double _coefficient;
    private int _exponent;

    public BasicEquation(double coefficient, int exponent) {
        super();
        _coefficient = coefficient;
        _exponent = exponent;
    }

    public double getCoefficient() {
        return _coefficient;
    }

    public int getExponent() {
        return _exponent;
    }

    @Override
    public Object[] getParams() {
        return new Object[]{_coefficient, new Double(_exponent), new Double(0), new Double(1)};
    }

    /**
     * @see udes.ds.rmi.hw.Equation#getFunctionValue(double)
     */
    public double getFunctionValue(double x) {
        return ((Math.pow(x, _exponent)) * _coefficient);
    }

    @Override
    public String Type() {
        return EquationReceiver.BasicEquation;
    }

    /**
     * @see udes.ds.rmi.hw.AbstractEquation#getUserReadableString()
     */
    protected String getUserReadableString() {
        return new String(Double.toString(_coefficient) + "x^" + Integer.toString(_exponent));
    }

}
