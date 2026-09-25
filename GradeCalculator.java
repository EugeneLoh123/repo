//Author: Eugene Loh
/*
 * Class: CMSC203 
 * Instructor: Professor Gary Thai
 * Description: Reads input files, calculates student grades, and returns an output file with results
 * Due: 09/25/2026
 * Platform/compiler: Eclipse
 * I pledge that I have completed the programming assignment 
  independently. I have not copied the code from a student or any source. I have not given my code to any student.
 * Print your Name here: Eugene Loh
*/

import java.util.Scanner;
import java.io.File;
import java.io.PrintWriter;
import java.io.IOException;

public class GradeCalculator {

	public static void main(String[] args) {
		
		System.out.println("=======================================");
		System.out.println(" CMSC 203 Project 1 - Grade Calculator ");
		System.out.println("=======================================");
		System.out.println();
		
		//Default configuration in case configuration file is missing or invalid
		String courseName = "CMSC 203 Computer Science I";
		int numOfCategories = 3;
		
		//Storage string for categories and weights in format: ";name:weight;name:weight;"
		String configData = "";
		boolean useDefaultConfig = false;
		
		//Reading and verifying gradeconfig file
		System.out.println("Loading configuration from gradeconfig.txt...");
		File configFile = new File("gradeconfig.txt");
		boolean validConfigFound = false; //Flag to track if config loaded
		
		//Check if file exists on disk
		if (configFile.exists()) {
			try {
				Scanner configScanner = new Scanner(configFile);
				
				//Read course name
				if (configScanner.hasNextLine()) {
					String line = configScanner.nextLine().trim(); //.trim removes any extra spaces
					if (!line.isEmpty()) {
						courseName = line;
					}
				}
				
				//Read total number of categories
				if (configScanner.hasNextInt()) {
					numOfCategories = configScanner.nextInt();
				}
				
				int totalWeight = 0; //Storage to check if category weight of all category is = 100
				String tempConfigData = ";"; //Temporary storage string
				
				//Loop through categories in gradeconfig
				for (int i = 1; i <= numOfCategories; i++) {
					if (configScanner.hasNext()) {
						String name = configScanner.next(); //Category name
						int weight = configScanner.nextInt(); //Category weight
						totalWeight += weight; //Add weight to totalWeight for the check later
						
						//Append name and weight to storage string
						tempConfigData += name.toLowerCase() + ":" + weight + ";";
					}
				}
				configScanner.close();
				
				//Validate totalWeight is == 100 and numOfCategories > 0
				if (totalWeight == 100 && numOfCategories > 0) {
					validConfigFound = true;
					configData = tempConfigData;
					System.out.println("Configuration loaded successfully.");
				} else {
					System.out.println("Invalid configuration weight total (must sum up to 100). Switching to default configuration.");
				}
				
				//Catches file reading and access errors
			} catch (IOException e) {
				System.out.println("Error reading gradeconfig.txt. Switching to default configuration.");
			}
		} else {
			System.out.println("gradeconfig.txt not found. Switching to default configuration.");
		}
		
		//Apply default values if config file is missing or invalid
		if (!validConfigFound) {
			useDefaultConfig = true;
			courseName = "CMSC 203 Computer Science I";
			numOfCategories = 3;
			configData = ";projects:40;quizzes:30;exams:30;"; //Use semicolon pattern to create a "look up map"
		}
		
		System.out.println();
		System.out.println("Using input file: grades_input.txt");
		System.out.println("Using output file: grades_report.txt");
		System.out.println();
		System.out.println("Reading student scores...");
		
		//Read grade_input file
		File inputFile = new File("grades_input.txt");
		
		//Exit if input file does not exist
		if (!inputFile.exists()) {
			System.out.println("Error: Input file 'grades_input.txt' not found. Exiting program");
			return;
		}
		
		String firstName = "";
		String lastName = "";
		
		double totalWeightedSum = 0.0; //Storage for (categoryAvg * categoryWeight)
		String categoryReportText = ""; //Storage for formatted output lines of each category
		
		try {
			Scanner fileScanner = new Scanner(inputFile);
			
			//Read student's first and last name
			if (fileScanner.hasNext()) {
				firstName = fileScanner.next();
			}
			if (fileScanner.hasNext()) {
				lastName = fileScanner.next();
			}
			
			//Process each category
			while (fileScanner.hasNext()) {
				String inputCatName = fileScanner.next(); //Read category name
				if (!fileScanner.hasNextInt()) {
					break; //In case file ends abruptly 
				}
				int scoreCount = fileScanner.nextInt(); //Read number of scores in category
				double sum = 0.0; //Storage for scores in category
				
				//Sum up scores in current category
				for (int i = 0; i < scoreCount; i++) {
					if (fileScanner.hasNextDouble()) {
						sum += fileScanner.nextDouble();
					}
				}
				//Category average
				double categoryAvg = (scoreCount > 0) ? (sum / scoreCount) : 0;
				
				//Category validation, match category with loaded config
				String searchKey = ";" + inputCatName.toLowerCase() + ":";
				int keyIndex = configData.indexOf(searchKey);
				
				if (keyIndex != -1) {
					int startVal = keyIndex + searchKey.length();
					int endVal = configData.indexOf(";", startVal);
					int catWeight = Integer.parseInt(configData.substring(startVal, endVal));
					
					totalWeightedSum += (categoryAvg * catWeight);
					categoryReportText += String.format("  %s (%d%%): average = %.2f%n", inputCatName, catWeight, categoryAvg);
				} else {
					System.out.println("Error: Unrecognized category '" + inputCatName + "' skipped.");
				}
				
			}
			fileScanner.close();
		
		  //Catches file access errors
		} catch (IOException e) {
			System.out.println("Error accessing grades_input.txt");
			return;
		}
		
		//Display student info and category averages
		System.out.println();
		System.out.println("Student: " + firstName + " " + lastName);
		System.out.println("Course: " + courseName);
		System.out.println();
		System.out.println("Category Results:");
		System.out.print(categoryReportText);
        System.out.println();
        
        Scanner keyboard = new Scanner(System.in);
        String choice = "";
        boolean validChoice = false;
        
        //Repeats until user chooses y/n
        while (!validChoice) {
        	System.out.print("Apply +/- grading? (Y/N): ");
        	choice = keyboard.nextLine().trim();
        	if (choice.equalsIgnoreCase("Y") || choice.equalsIgnoreCase("N")) {
        		validChoice = true; //Input is valid, break loop
        	} else {
        		System.out.println("Invalid input. Please enter Y or N.");
        	}
        }
        
        //Calculate total weighted average
        double overallAverage = totalWeightedSum / 100.0;
        
        //Apply letter grade based on number grade
        String baseGrade = "";
        if (overallAverage >= 90.0) {
        	baseGrade = "A";
        } else if (overallAverage >= 80.0) {
        	baseGrade = "B";
        } else if (overallAverage >= 70.0) {
        	baseGrade = "C";
        } else if (overallAverage >= 60.0) {
        	baseGrade = "D";
        } else {
        	baseGrade = "F";
        }
        //If user selects +/- option
        String finalGrade = baseGrade;
        
        if (choice.equalsIgnoreCase("Y") && !baseGrade.equals("F")) {
        	double lastDigit = overallAverage % 10.0; //Use mod to find out the "ones" slot of the number to determine if +/-
        	//Have to fully write out logic for an "A" grade because of a 100% edge case which would return an "A-"
        	if (overallAverage >= 97.0) {
        		finalGrade = "A+";
        	} else if (overallAverage >= 90.0 && overallAverage < 93.0) {
        		finalGrade = "A-";
        	} else if (overallAverage < 90.0) {
        		if (lastDigit >= 7.0) {
        			finalGrade = baseGrade + "+";
        		} else if (lastDigit < 3.0) {
        			finalGrade = baseGrade + "-";
        		}
        	}
        }
        
        //Display final calculations
        System.out.println();
        System.out.printf("Overall numeric average: %.2f%n", overallAverage);
        System.out.println("Base letter grade: " + baseGrade);
        System.out.println("Final letter grade: " + finalGrade);
        
        //Write summary to output file
        try {
        	PrintWriter outFile = new PrintWriter("grades_report.txt");
        	outFile.println("=======================================");
        	outFile.println(" CMSC 203 Project 1 - Grade Calculator ");
        	outFile.println("=======================================");
        	outFile.println("Student: " + firstName + " " + lastName);
        	outFile.println("Course: " + courseName);
        	outFile.println("Default Config Used: " + (useDefaultConfig ? "Yes" : "No"));
        	outFile.println();
        	outFile.println("Category Results:");
        	outFile.print(categoryReportText);
        	outFile.println();
        	outFile.printf("Overall numeric average: %.2f%n", overallAverage);
        	outFile.println("Base letter grade: " + baseGrade);
        	outFile.println("Final letter grade: " + finalGrade);
        	outFile.println();
        	
        	outFile.close();
        	
        	System.out.println();
        	System.out.println("Summary written to grades_report.txt");
        } catch (IOException e) {
        	System.out.println("Error writing to grades_report.txt");
        }
        
        System.out.println("Program complete.");
        System.out.println("Goodbye!");
        keyboard.close();
	}

}