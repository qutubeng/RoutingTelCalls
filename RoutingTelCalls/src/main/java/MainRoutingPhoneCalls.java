package main.java;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;

public class MainRoutingPhoneCalls {
	
	/**
	 * process operator information from text file 
	 * returns ArrayList of operator information
	 * @param fileName
	 * @return
	 * @throws FileNotFoundException
	 */
	public ArrayList<String> getOperatorInfo(String fileName) throws FileNotFoundException {
		//
		ArrayList<String> operatorList = new ArrayList<String>();
		PrepareOperatorData prepareOperatorData = new PrepareOperatorData();
		operatorList = prepareOperatorData.getDataFromTextFile(fileName);
		
		//sorting operator data 
		Collections.sort(operatorList);
		//sorting operator data in descending order
		//so that we can get the longest prefix matched first 
		//from the several prefix matched for the same number
		Collections.reverse(operatorList);
		operatorList = prepareOperatorData.getOperatorInfoList(operatorList);
		
		return operatorList;
	}
	
	/**
	 * get phone numbers from text file and returns ArrayList of phone numbers
	 * @param fileName
	 * @return
	 */
	private ArrayList<String> getPhoneNumbers(String fileName) {

		ArrayList<String> phoneNumberList = new ArrayList<String>();
		PrepareOperatorData prepareOperatorData = new PrepareOperatorData();
		
		try {
			phoneNumberList = prepareOperatorData.getDataFromTextFile(fileName);
		}catch(FileNotFoundException fnfe) {
			log("FileNotFoundException: " +fnfe.getMessage());
		}
		
		return phoneNumberList;
	}
	
	/**
	 * get cheapest operator by phone number
	 * @param phoneNumber
	 * @param operatorListA
	 * @param operatorListB
	 * @return
	 */
	public String getCheapOperatorByPhoneNumber(String phoneNumber, 
			ArrayList<String> operatorListA, ArrayList<String> operatorListB) {

		String cheapestOperator = "";
		double operatorPriceA = -1.0;
		double operatorPriceB = -1.0;

		//operatorListA
		for(int i = 0; i < operatorListA.size()-1; i=i+2) {
			
			if(phoneNumber.startsWith(operatorListA.get(i))) {
				try {
					//if we have several prefixes match for the same number, the longest one should be used
					operatorPriceA = Double.parseDouble(operatorListA.get(i+1).trim());
					//System.out.println("phoneNumber: " + phoneNumber +" : " + operatorListA.get(i) +" : " + operatorPriceA);
				} catch(NumberFormatException nfe) {
					log("NumberFormatException: " + nfe.getMessage());
				}
				break;
			}
		}
		
		//operatorListB
		for(int i = 0; i < operatorListB.size()-1; i=i+2) {
			
			if(phoneNumber.startsWith(operatorListB.get(i))) {
				try {
					//if we have several prefixes match for the same number, the longest one should be used
					operatorPriceB = Double.parseDouble(operatorListB.get(i+1).trim());
					
				} catch(NumberFormatException nfe) {
					log("NumberFormatException: " + nfe.getMessage());
				}
				break;
			}
		}

		if(operatorPriceA != -1.0 || operatorPriceB != -1.0) {
			if(operatorPriceA != -1.0 && operatorPriceB != -1.0) {
				if(operatorPriceA < operatorPriceB) {
					cheapestOperator = "operator A($" + operatorPriceA + ") is cheaper than operator B($" + operatorPriceB+")";
				}
				else if(operatorPriceA == operatorPriceB) {
					cheapestOperator = "operator A($" + operatorPriceA + ") and operator B($" + operatorPriceB + ") both are same";
				}
				else {
					cheapestOperator = "operator B($" + operatorPriceB + ") is cheaper than operator A($" + operatorPriceA+")";
				}
			}
			else if(operatorPriceA == -1.0) {
				cheapestOperator = "only operator B($" + operatorPriceB + ") is found, operator A is missing";
			}
			else {
				cheapestOperator = "only operator A($" + operatorPriceA + ") is found, operator B is missing";
			}
		}
		else {
			cheapestOperator = "no operator is found";
		}
		return phoneNumber + " : " + cheapestOperator;
	}
	
	/**
	 * Check cheapest phone call from two different operators
	 * @param filePathPhoneNumbers
	 * @param filePathOperatorA
	 * @param filePathOperatorB
	 * @param filePathOutput
	 */
	private void getCheapPhoneCall(String filePathPhoneNumbers, 
			String filePathOperatorA, String filePathOperatorB, String filePathOutput) {
		
		ArrayList<String> phoneNumberList = new ArrayList<String>();
		ArrayList<String> operatorListA = new ArrayList<String>();
		ArrayList<String> operatorListB = new ArrayList<String>();
		ArrayList<String> resultList = new ArrayList<String>();
		
		String phoneNumber = "";
		String result = "";
		
		try{
			operatorListA = getOperatorInfo(filePathOperatorA);
			operatorListB = getOperatorInfo(filePathOperatorB);
			phoneNumberList = getPhoneNumbers(filePathPhoneNumbers);
		}
		catch(FileNotFoundException fnfe) {
			log("FileNotFoundException: " +fnfe.getMessage());
		}
		
		//check for each phone number
		for(int i = 0; i < phoneNumberList.size(); i++) {
			phoneNumber = phoneNumberList.get(i);
			result = getCheapOperatorByPhoneNumber(phoneNumber, operatorListA, operatorListB);
			//storing result in ArrayList
			resultList.add(result);
		}
		
		//writing results in a text file
		PrepareOperatorData prepareOperatorData = new PrepareOperatorData();
		prepareOperatorData.writeOnTextFile(filePathOutput, resultList);
	}
	
	/**
	 * get source directory
	 * @return
	 */
	private String getWorkingDirectory() {
		String path = new File("").getAbsolutePath();
		path = path.replace("\\", "/");
		return path;
	}
	
	public static void main(String[] args) {
		String filePathOperatorA;
		String filePathOperatorB;
		String filePathPhoneNumbers;
		String filePathOutput;
		
		MainRoutingPhoneCalls routingPhnCll = new MainRoutingPhoneCalls();
		String directoryPath = routingPhnCll.getWorkingDirectory();
		
		filePathPhoneNumbers = directoryPath + "/src/main/resources/InputPhoneNumbers.txt";
		filePathOperatorA = directoryPath + "/src/main/resources/OperatorA.txt";
		filePathOperatorB = directoryPath + "/src/main/resources/OperatorB.txt";
		filePathOutput = directoryPath + "/src/output/result.txt";
		
		routingPhnCll.getCheapPhoneCall(filePathPhoneNumbers, filePathOperatorA, 
				filePathOperatorB, filePathOutput);
	}
	
	private static void log(Object aObject){
		System.out.println(String.valueOf(aObject));
	}
}
