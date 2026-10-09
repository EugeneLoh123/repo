/**
 * Class description: Models a medical procedure: its name, date, practitioner and charge.
 *
 * Pledge: I pledge that I have completed the programming assignment
 * 		independently. I have not copied the code from a student or any
 *      source. I have not given my code to any student.
 *      Print your Name here: Eugene Loh
 * Course: CMSC203
 * Due Date: 10/09/26
 * Platform/Compiler: Eclipse
 */

import java.text.DecimalFormat;

public class Procedure {

	// Attributes 
	private String procedureName;
	private String procedureDate;
	private String practitionerName;
	private double charges;

	// Constructors 

	/**
	 * No arg constructor. starts with empty strings and a charge of 0.0.
	 */
	public Procedure() {
		this("", "", "", 0.0);
	}

	/**
	 * Initializes the procedure name and date only.
	 */
	public Procedure(String procedureName, String procedureDate) {
		this(procedureName, procedureDate, "", 0.0);
	}

	/**
	 * Initializes every attribute.
	 */
	public Procedure(String procedureName, String procedureDate, String practitionerName, double charges) {
		this.procedureName = procedureName;
		this.procedureDate = procedureDate;
		this.practitionerName = practitionerName;
		this.charges = charges;
	}

	// Accessors 
	public String getProcedureName() {
		return procedureName;
	}

	public String getProcedureDate() {
		return procedureDate;
	}

	public String getPractitionerName() {
		return practitionerName;
	}

	public double getCharges() {
		return charges;
	}

	// Mutators
	public void setProcedureName(String procedureName) {
		this.procedureName = procedureName;
	}

	public void setProcedureDate(String procedureDate) {
		this.procedureDate = procedureDate;
	}

	public void setPractitionerName(String practitionerName) {
		this.practitionerName = practitionerName;
	}

	public void setCharges(double charges) {
		this.charges = charges;
	}

	/**
	 * Displays all procedure information.
	 * 
	 * @return a multi-line description of the procedure
	 */
	@Override
	public String toString() {
		return "Procedure: " + procedureName + "\n" + "Date: " + procedureDate + "\n" + "Practitioner: " + practitionerName + "\n" + "Charges: " + getFormattedCharge();
	}

	// Additional methods

	/**
	 * A procedure is expensive when its charges are 1000.00 or more.
	 * 
	 * @return true if charges >= 1000.00
	 */
	public boolean isExpensiveProcedure() {
		return charges >= 1000.00;
	}

	/**
	 * Lowers the charges by the given percent. Only values from 0 to 100 are
	 * accepted; anything else leaves the charges unchanged.
	 * 
	 * @param percent discount percentage (0-100)
	 */
	public void applyDiscount(double percent) {
		if (percent >= 0 && percent <= 100) {
			charges = charges - (charges * percent / 100.0);
		}
	}

	/**
	 * Categorizes the charge: under 500 is "Low", 500 up to (but not including) 1000 is "Medium", and 1000 or more is "High".
	 * 
	 * @return "Low", "Medium" or "High"
	 */
	public String getChargeCategory() {
		if (charges < 500.00) {
			return "Low";
		} else if (charges < 1000.00) {
			return "Medium";
		} else {
			return "High";
		}
	}

	/**
	 * Checks whether this procedure was performed by the given practitioner (case-insensitive).
	 */
	public boolean isPerformedBy(String practitionerName) {
		return this.practitionerName.equalsIgnoreCase(practitionerName);
	}

	/**
	 * Formats the charge with a dollar sign, commas and two decimals
	 * 
	 * @return the formatted charge
	 */
	public String getFormattedCharge() {
		return new DecimalFormat("$#,##0.00").format(charges);
	}
}
