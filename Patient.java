/**
 * Class description: Models a patient's personal, address, phone and emergency-contact information.
 *
 * Pledge: I pledge that I have completed the programming assignment
 * 		independently. I have not copied the code from a student or any 
 * 		source. I have not given my code to any student. 
 * 		Print your Name here: Eugene Loh
 * Course: CMSC203 
 * Due Date: 10/09/26 
 * Platform/Compiler: Eclipse
 */

public class Patient {

	// Attributes
	private String firstName;
	private String middleName;
	private String lastName;
	private String street;
	private String city;
	private String state;
	private String zip;
	private String phoneNumber;
	private String emergencyName;
	private String emergencyPhone;

	// Constructors

	/**
	 * No arg constructor. Every String field starts as an empty string so the build methods never print "null".
	 */
	public Patient() {
		this("", "", "");
	}

	/**
	 * Initializes only the first, middle and last name.
	 */
	public Patient(String firstName, String middleName, String lastName) {
		this(firstName, middleName, lastName, "", "", "", "", "", "", "");
	}

	/**
	 * Initializes every attribute of the patient.
	 */
	public Patient(String firstName, String middleName, String lastName, String street, String city, String state, String zip, String phoneNumber, String emergencyName, String emergencyPhone) {
		this.firstName = firstName;
		this.middleName = middleName;
		this.lastName = lastName;
		this.street = street;
		this.city = city;
		this.state = state;
		this.zip = zip;
		this.phoneNumber = phoneNumber;
		this.emergencyName = emergencyName;
		this.emergencyPhone = emergencyPhone;
	}

	// Accessors
	public String getFirstName() {
		return firstName;
	}

	public String getMiddleName() {
		return middleName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getStreet() {
		return street;
	}

	public String getCity() {
		return city;
	}

	public String getState() {
		return state;
	}

	public String getZip() {
		return zip;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public String getEmergencyName() {
		return emergencyName;
	}

	public String getEmergencyPhone() {
		return emergencyPhone;
	}

	// Mutators 
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public void setState(String state) {
		this.state = state;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public void setEmergencyName(String emergencyName) {
		this.emergencyName = emergencyName;
	}

	public void setEmergencyPhone(String emergencyPhone) {
		this.emergencyPhone = emergencyPhone;
	}

	// Build methods 

	/**
	 * Builds the full name in the form "firstName middleName lastName".
	 * 
	 * @return the full name
	 */
	public String buildFullName() {
		return firstName + " " + middleName + " " + lastName;
	}

	/**
	 * Builds the address in the form "street city state zip".
	 * 
	 * @return the full address
	 */
	public String buildAddress() {
		return street + " " + city + " " + state + " " + zip;
	}

	/**
	 * Builds the emergency contact in the form "emergencyName emergencyPhone".
	 * 
	 * @return the emergency contact
	 */
	public String buildEmergencyContact() {
		return emergencyName + " " + emergencyPhone;
	}

	/**
	 * Displays all patient information using the build methods.
	 * 
	 * @return a description of the patient
	 */
	@Override
	public String toString() {
		return "Name: " + buildFullName() + "\n" + "Address: " + buildAddress() + "\n" + "Phone Number: " + phoneNumber + "\n" + "Emergency Contact: " + buildEmergencyContact();
	}

	// Additional methods 

	/**
	 * Checks that a phone number matches ###-###-#### (digits only).
	 */
	private static boolean matchesPhoneFormat(String phone) {
		return phone != null && phone.matches("\\d{3}-\\d{3}-\\d{4}");
	}

	/**
	 * Tells whether the patient's phone number is in ###-###-#### format.
	 * 
	 * @return true if valid, false otherwise
	 */
	public boolean isValidPhoneNumber() {
		return matchesPhoneFormat(phoneNumber);
	}

	/**
	 * Tells whether the emergency contact's phone number is in ###-###-#### format.
	 * 
	 * @return true if valid, false otherwise
	 */
	public boolean isValidEmergencyPhoneNumber() {
		return matchesPhoneFormat(emergencyPhone);
	}

	/**
	 * Builds the name in the form "lastName, firstName middleName".
	 * 
	 * @return the name in "last first middle" format
	 */
	public String getLastFirstMiddle() {
		return lastName + ", " + firstName + " " + middleName;
	}

	/**
	 * Checks whether the patient lives in the given city and state
	 * (case-insensitive).
	 * 
	 * @param city  the city to compare
	 * @param state the state to compare
	 * @return true if both match
	 */
	public boolean hasSameCityState(String city, String state) {
		return this.city.equalsIgnoreCase(city) && this.state.equalsIgnoreCase(state);
	}

	/**
	 * Replaces the street, city, state and ZIP code in one call.
	 */
	public void updateAddress(String street, String city, String state, String zip) {
		this.street = street;
		this.city = city;
		this.state = state;
		this.zip = zip;
	}

	/**
	 * Builds a formatted summary of the patient and emergency contact.
	 * 
	 * @return the contact summary
	 */
	public String getContactSummary() {
		return "Patient: " + getLastFirstMiddle() + " | Phone: " + phoneNumber + " | Emergency Contact: " + emergencyName + " (" + emergencyPhone + ")";
	}
}
