/**
 * udes.ds.rmi
 * Equation.java
 * 3 sept. 08
 */
package udes.ds.agent;

import java.io.Serializable;

/**
 * Common interface for all derivable mathematical equation types
 * 
 * @author      Luc Bergevin
 * @version     1.0        
 */
public interface Equation extends Serializable {
	
	/**
	 * Computes de value of y (the function value) given the value of x
	 *
	 * @return      y double
	 * @param       x double         
	 */
	abstract public double getFunctionValue(double x);
	
	/**
	 * Prints to the system output a user-readable form of the equation
	 *
	 * @return      void          
	 */
	abstract public void printUserReadable() ;

}
