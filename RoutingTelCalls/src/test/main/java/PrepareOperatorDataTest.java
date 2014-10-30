package main.java;

import static org.junit.Assert.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import org.junit.Before;
import org.junit.Test;

public class PrepareOperatorDataTest {
	
	private PrepareOperatorData prepareOperatorData;
	
	@Before
	public void setUp() throws Exception {
		prepareOperatorData = new PrepareOperatorData();
	}

	@Test
	public final void testGetOperatorInfoList() {
		
		ArrayList<String> operatorList = new ArrayList<String>();
		ArrayList<String> expectedList = new ArrayList<String>();
		
		operatorList.add("4235 2.05");
		operatorList.add("44 1.0");		
		operatorList = prepareOperatorData.getOperatorInfoList(operatorList);
		
		expectedList.add("4235");
		expectedList.add("2.05");
		expectedList.add("44");
		expectedList.add("1.0");
		assertEquals(expectedList, operatorList);
	}
	
	@Test
	public final void testGetDataFromTextFile() throws IOException {
		
		String path = new File("").getAbsolutePath();
		path = path.replace("\\", "/");
		
		String filePathPhoneNumbers = path + "/src/test/resources/InputPhoneNumbers.txt";
		String filePathOperatorA = path + "/src/test/resources/OperatorA.txt";
		String filePathOperatorB = path + "/src/test/resources/OperatorB.txt";
		
		ArrayList<String> expectedPhoneList = new ArrayList<String>();
		ArrayList<String> expectedOperatorA = new ArrayList<String>();
		ArrayList<String> expectedOperatorB = new ArrayList<String>();
		
		ArrayList<String> actualPhoneList = new ArrayList<String>();
		ArrayList<String> actualOperatorA = new ArrayList<String>();
		ArrayList<String> actualOperatorB = new ArrayList<String>();
		
		expectedPhoneList = prepareOperatorData.getDataFromTextFile(filePathPhoneNumbers);
		expectedOperatorA = prepareOperatorData.getDataFromTextFile(filePathOperatorA);
		expectedOperatorB = prepareOperatorData.getDataFromTextFile(filePathOperatorB);
		
		actualPhoneList.add("4673212345");
		actualPhoneList.add("68123456789");		
		assertEquals(expectedPhoneList, actualPhoneList);
		
		actualOperatorA.add("1 0.9");
		actualOperatorA.add("268 5.1");
		actualOperatorA.add("46 0.17");		
		assertEquals(expectedOperatorA, actualOperatorA);
		
		actualOperatorB.add("44 1.9");
		actualOperatorB.add("442 1.0");
		assertEquals(expectedOperatorB, actualOperatorB);
	}
}