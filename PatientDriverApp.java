/**
 * Class description: Driver program that reads a patient from the keyboard, creates three procedures and displays a report.
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
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class PatientDriverApp {

	// Author's name
	private static final String STUDENT_NAME = "Eugene Loh";

	/**
	 * Prompts for and reads every patient field, then builds a Patient using the all-attributes constructor.
	 * 
	 * @param input the Scanner reading from the keyboard
	 * @return the new Patient
	 */
	public static Patient inputPatient(Scanner input) {
		System.out.print("Enter first name: ");
		String first = input.nextLine();
		System.out.print("Enter middle name: ");
		String middle = input.nextLine();
		System.out.print("Enter last name: ");
		String last = input.nextLine();
		System.out.print("Enter street address: ");
		String street = input.nextLine();
		System.out.print("Enter city: ");
		String city = input.nextLine();
		System.out.print("Enter state: ");
		String state = input.nextLine();
		System.out.print("Enter zip: ");
		String zip = input.nextLine();
		System.out.print("Enter phone number (###-###-####): ");
		String phone = input.nextLine();
		System.out.print("Enter emergency contact name: ");
		String emName = input.nextLine();
		System.out.print("Enter emergency contact phone (###-###-####): ");
		String emPhone = input.nextLine();

		return new Patient(first, middle, last, street, city, state, zip, phone, emName, emPhone);
	}

	/**
	 * Creates a Procedure with the no arg constructor, then uses the mutators so every attribute is set.
	 */
	public static Procedure createProcedure1() {
		Procedure p = new Procedure();
		p.setProcedureName("Physical Exam");
		p.setProcedureDate("07/20/2026");
		p.setPractitionerName("Dr. Irvine");
		p.setCharges(250.00);
		return p;
	}

	/**
	 * Creates a Procedure with the name and date constructor, then uses the mutators for the remaining attributes.
	 */
	public static Procedure createProcedure2() {
		Procedure p = new Procedure("X-ray", "07/20/2026");
		p.setPractitionerName("Dr. Jamison");
		p.setCharges(550.43);
		return p;
	}

	/**
	 * Creates a Procedure with the all attributes constructor.
	 */
	public static Procedure createProcedure3() {
		return new Procedure("Blood Test", "07/20/2026", "Dr. Smith", 1400.75);
	}

	/**
	 * Displays patient information, including phone validation results.
	 */
	public static void displayPatient(Patient patient) {
		System.out.println("\nPatient Information");
		System.out.println("-------------------");
		System.out.println(patient);
		System.out.println("Phone Valid: " + patient.isValidPhoneNumber());
		System.out.println("Emergency Phone Valid: " + patient.isValidEmergencyPhoneNumber());
	}

	/**
	 * Displays one procedure using its toString() method.
	 */
	public static void displayProcedure(Procedure procedure) {
		System.out.println(procedure);
		System.out.println();
	}

	/**
	 * Displays the three procedures in an aligned table.
	 */
	public static void displayProcedureTable(Procedure p1, Procedure p2, Procedure p3) {
		System.out.println();
		System.out.printf("%-20s%-13s%-20s%-16s%s%n", "Procedure", "Date", "Practitioner", "Charge", "Category");
		System.out.println("------------------------------------------------------------------------");
		for (Procedure p : new Procedure[] { p1, p2, p3 }) {
			System.out.printf("%-20s%-13s%-20s%-16s%s%n", p.getProcedureName(), p.getProcedureDate(),
					p.getPractitionerName(), p.getFormattedCharge(), p.getChargeCategory());
		}
	}

	/**
	 * Adds up the charges of the three procedures.
	 */
	public static double calculateTotalCharges(Procedure p1, Procedure p2, Procedure p3) {
		return p1.getCharges() + p2.getCharges() + p3.getCharges();
	}

	/**
	 * Averages the charges of the three procedures.
	 */
	public static double calculateAverageCharge(Procedure p1, Procedure p2, Procedure p3) {
		return calculateTotalCharges(p1, p2, p3) / 3.0;
	}

	/**
	 * Returns whichever procedure has the largest charge.
	 */
	public static Procedure findHighestChargeProcedure(Procedure p1, Procedure p2, Procedure p3) {
		Procedure highest = p1;
		if (p2.getCharges() > highest.getCharges()) {
			highest = p2;
		}
		if (p3.getCharges() > highest.getCharges()) {
			highest = p3;
		}
		return highest;
	}

	/**
	 * Counts how many of the three procedures are expensive (>= 1000.00).
	 */
	public static int countExpensiveProcedures(Procedure p1, Procedure p2, Procedure p3) {
		int count = 0;
		if (p1.isExpensiveProcedure())
			count++;
		if (p2.isExpensiveProcedure())
			count++;
		if (p3.isExpensiveProcedure())
			count++;
		return count;
	}

	/**
	 * Displays total, average, highest-charge procedure and expensive count.
	 */
	public static void displaySummary(Procedure p1, Procedure p2, Procedure p3) {
		DecimalFormat money = new DecimalFormat("$#,##0.00");
		System.out.println();
		System.out.println("Total Charges: " + money.format(calculateTotalCharges(p1, p2, p3)));
		System.out.println("Average Charge: " + money.format(calculateAverageCharge(p1, p2, p3)));
		System.out.println("Highest Charge Procedure: " + findHighestChargeProcedure(p1, p2, p3).getProcedureName());
		System.out.println("Number of Expensive Procedures: " + countExpensiveProcedures(p1, p2, p3));
	}

	/**
	 * Program entry point: reads the patient, builds the procedures, and prints the
	 * full report.
	 */
	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);

		Patient patient = inputPatient(input);
		Procedure p1 = createProcedure1();
		Procedure p2 = createProcedure2();
		Procedure p3 = createProcedure3();

		displayPatient(patient);
		displayProcedureTable(p1, p2, p3);
		displaySummary(p1, p2, p3);

		System.out.println("\nThe program was developed by a Student: " + STUDENT_NAME + " on 10/9/26");

		input.close();
	}
}
