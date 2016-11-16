/**
 * udes.ds.rmi
 * Constant.java
 * 3 sept. 08
 */
package udes.ds.agent;


/**
 * Stores a fix value for x or y
 * 
 * @author      Luc Bergevin
 * @version     1.0         
 */
public class Constant extends AbstractEquation {
	
	private static final long	 serialVersionUID	= 1L;
	private double _value;

	public Constant(double value) {
		super();
		_value = value;
	}

	public double getValue() {
		return _value;
	}

	@Override
	public Object[] getParams() {
		return new Double[]{_value, _value, new Double(0), new Double(1)};
	}

	/**
	 * @see udes.ds.rmi.hw.Equation#getFunctionValue(double)       
	 */
	public double getFunctionValue(double x) {
		return _value;
	}

	@Override
	public String Type() {
		return EquationReceiver.Constant;
	}

	/**
	 * @see udes.ds.rmi.hw.AbstractEquation#getUserReadableString()      
	 */
	public String getUserReadableString() {
		return Double.toString(_value);
	}
	
}
