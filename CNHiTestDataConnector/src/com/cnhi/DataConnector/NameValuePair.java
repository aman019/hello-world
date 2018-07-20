package com.cnhi.DataConnector;

/**
 * Base class that creates and holds execution <code>elementName</code>,
 * <code>elementValue</code> information for current execution cycle.
 * <p>
 * User can refer to these variables by creating object of type <code>NameValuePair</code>
 * <p>
 * <b>Usage:</b><br>
 * <code>NameValuePair nvp = new NameValuePair();</code>
 * @author      Vishal Kumar
 */
public class NameValuePair {

	private String elementName;
	private String elementValue;

	/**
	 * Returns the element name from the test file chosen by the user to run the test cases.
	 * <p>
	 * <b>Usage:</b><br>
	 * <code>
	 * NameValuePair nvp = new NameValuePair();<br>
	 * nvp.getElementName();</code>
	 * <p>
	 * @return elementName
	 **/
	public String getElementName() {
		return elementName;
	}

	/**
	 * Initializes and holds the name of the element from the test file chosen for test case execution.
	 * <p>
	 * <b>Usage:</b><br>
	 * <code>
	 * NameValuePair nvp = new NameValuePair();<br>
	 * nvp.setElementName(name);</code>
	 * <p>
	 * @param name Name of a particular element from the test file chosen for test case execution.
	 */
	public void setElementName(String name) {
		this.elementName = name;
	}


	/**
	 * Returns the value of element from the test file chosen by the user to run the test cases.
	 * <p>
	 * <b>Usage:</b><br>
	 * <code>
	 * NameValuePair nvp = new NameValuePair();<br>
	 * nvp.getElementValue();</code>
	 * <p>
	 * @return elementValue
	 **/
	public String getElementValue() {
		return elementValue;
	}

	/**
	 * Initializes and holds the name of the element from the test file chosen for test case execution.
	 * <p>
	 * <b>Usage:</b><br>
	 * <code>
	 * NameValuePair nvp = new NameValuePair();<br>
	 * nvp.setElementName(name);</code>
	 * <p>
	 * @param value Value of a particular element from the test file chosen for test case execution.
	 */
	public void setElementValue(String value) {
		this.elementValue = value;
	}


}
