/**
 * udes.ds.agent.hw
 * AbstractEquation.java
 * 7 oct. 09
 */
package udes.ds.agent;


/**
 * TODO add type comments
 * 
 * @author      Luc Bergevin
 * @version     1.0
 * @see          
 */
public abstract class AbstractEquation implements Equation {

	/**
	 * Returns a user-readable form of the equation
	 *
	 * @return      String          
	 */
	abstract protected String getUserReadableString();

	/**   
	 * @see udes.ds.rmi.hw.Equation#printUserReadable()      
	 */
	public void printUserReadable() {
		System.out.println("y = " + this.getUserReadableString());
	}

}
