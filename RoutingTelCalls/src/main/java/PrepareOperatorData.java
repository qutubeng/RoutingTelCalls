package main.java;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class PrepareOperatorData {
	/**
	 * Read text file
	 * @param fileName
	 * @return
	 * @throws FileNotFoundException
	 */
	public ArrayList<String> getDataFromTextFile(String fileName) throws FileNotFoundException {
		Scanner scanner = new Scanner(new FileReader(fileName));
		ArrayList<String> dataList = new ArrayList<String>();
		
		try {
	      //first use a Scanner to get each line
	      while ( scanner.hasNextLine() ){
	    	  dataList.add(scanner.nextLine().trim());
	      }
	    }
	    finally {
	      scanner.close();
	    }
	    return dataList;
	}
	
	/**
	 * prepare data as phone-number-prefix and phone-bill
	 * @param operatorInfoLineByLine
	 * @return
	 */
	public ArrayList<String> getOperatorInfoList(ArrayList<String> operatorInfoLineByLine){
		
		ArrayList<String> operatorList = new ArrayList<String>();
		String aLine = "";
		for(int i =0; i < operatorInfoLineByLine.size(); i++ ) {
			aLine = operatorInfoLineByLine.get(i);
			Scanner scanner = new Scanner(aLine);
			String delims = "[+\\-*/\\^ \\^	\\=*/]+";
			scanner.useDelimiter(delims);

			try {
				if(scanner.hasNext()) {
					String phonePrefix = scanner.next().trim();
					String bill = scanner.next().trim();
					operatorList.add(phonePrefix);
					operatorList.add(bill);
				}
				else {
					log("Empty line!");
				}
			} 
			catch (NoSuchElementException e) {
				e.getStackTrace();
			}
		}
		return operatorList;
	}
	
	/**
	 * Write data in Text file
	 * @param fileName
	 * @param resultList
	 */
	public void writeOnTextFile(String fileName, ArrayList<String> resultList) {
		
		try {			
			//If old file is exist, then delete this file.
			File file = new File(fileName);
			if(file.exists()) {
				file.delete();
			}
			
			FileWriter writer = new FileWriter(fileName, true);
			BufferedWriter out = new BufferedWriter(writer);
						
			for(int i = 0; i < resultList.size(); i++) {				
				out.write(resultList.get(i)+"\n");
				log(resultList.get(i));
			}
			out.close();
		}
		catch(IOException ioe) {
			log(ioe.getMessage());
		}
		
		if(resultList.size()>0) {
		log("\nResult has been stored in a text file in the location of \n" +
				"" + "\"" +fileName + "\"");
		}
		else {
			log("There is no result to store in output file");
		}
	}
	
	/**
	 * @param aObject
	 */
	private static void log(Object aObject){
		System.out.println(String.valueOf(aObject));
	}
}
