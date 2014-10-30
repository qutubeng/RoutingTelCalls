package main.java;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import org.junit.Before;
import org.junit.Test;

public class MainRoutingPhoneCallsTest {
	
	private MainRoutingPhoneCalls routingPhoneCalls;
	private PrepareOperatorData prepareOperatorData;

	@Before
	public void setUp() throws Exception {
		routingPhoneCalls = new MainRoutingPhoneCalls();
		prepareOperatorData = new PrepareOperatorData();
	}
	
	@Test
	public final void testGetOperatorInfo() throws FileNotFoundException {
		ArrayList<String> operatorList = new ArrayList<String>();
		ArrayList<String> expectedList = new ArrayList<String>();
		
		String path = new File("").getAbsolutePath();
		path = path.replace("\\", "/");
		
		String filePathOperatorB = path + "/src/test/resources/OperatorB.txt";
		
		expectedList.add("442");
		expectedList.add("1.0");
		expectedList.add("44");
		expectedList.add("1.9");
		
		operatorList = routingPhoneCalls.getOperatorInfo(filePathOperatorB);
		assertEquals(expectedList, operatorList);
	}

	@Test
	public final void testGetCheapOperatorByPhoneNumber() {
		
		ArrayList<String> operatorListA = new ArrayList<String>();
		ArrayList<String> operatorListB = new ArrayList<String>();
		String phoneNumber = "4423548785";
		
		operatorListA.add("44 1.9");
		operatorListA.add("442 1.1");		
		
		Collections.sort(operatorListA);
		Collections.reverse(operatorListA);
		
		operatorListA = prepareOperatorData.getOperatorInfoList(operatorListA);
		operatorListB.add("45 0.4");
		operatorListB.add("442 1.0");
		
		Collections.sort(operatorListB);
		Collections.reverse(operatorListB);
		
		operatorListB = prepareOperatorData.getOperatorInfoList(operatorListB);
		String result = routingPhoneCalls.getCheapOperatorByPhoneNumber(phoneNumber, operatorListA, operatorListB);
		assertEquals("4423548785 : operator B($1.0) is cheaper than operator A($1.1)", result);
		
		operatorListA.clear();
		operatorListB.clear();
		
		operatorListA.add("4423 0.2");
		operatorListA.add("44 1.1");		
		
		Collections.sort(operatorListA);
		Collections.reverse(operatorListA);		
		
		operatorListA = prepareOperatorData.getOperatorInfoList(operatorListA);
		operatorListB.add("4423 0.8");
		operatorListB.add("442 1.0");
		
		Collections.sort(operatorListB);
		Collections.reverse(operatorListB);
		
		operatorListB = prepareOperatorData.getOperatorInfoList(operatorListB);
		result = routingPhoneCalls.getCheapOperatorByPhoneNumber(phoneNumber, operatorListA, operatorListB);
		
		assertEquals("4423548785 : operator A($0.2) is cheaper than operator B($0.8)", result);
		
		operatorListA.clear();
		operatorListB.clear();
		
		operatorListA.add("4423 0.2");
		operatorListA.add("44 1.1");		
		
		Collections.sort(operatorListA);
		Collections.reverse(operatorListA);
		
		operatorListA = prepareOperatorData.getOperatorInfoList(operatorListA);
		
		operatorListB.add("452 0.8");
		operatorListB.add("45 1.0");
		
		Collections.sort(operatorListB);
		Collections.reverse(operatorListB);
		
		operatorListB = prepareOperatorData.getOperatorInfoList(operatorListB);
		
		result = routingPhoneCalls.getCheapOperatorByPhoneNumber(phoneNumber, operatorListA, operatorListB);
		
		assertEquals("4423548785 : only operator A($0.2) is found, operator B is missing", result);
		
		operatorListA.clear();
		operatorListB.clear();
		
		operatorListA.add("4523 0.2");
		operatorListA.add("45 1.1");		
		
		Collections.sort(operatorListA);
		Collections.reverse(operatorListA);
		
		operatorListA = prepareOperatorData.getOperatorInfoList(operatorListA);
		
		operatorListB.add("442 0.8");
		operatorListB.add("44 1.0");
		
		Collections.sort(operatorListB);
		Collections.reverse(operatorListB);
		
		operatorListB = prepareOperatorData.getOperatorInfoList(operatorListB);
		
		result = routingPhoneCalls.getCheapOperatorByPhoneNumber(phoneNumber, operatorListA, operatorListB);
		assertEquals("4423548785 : only operator B($0.8) is found, operator A is missing", result);
		
		operatorListA.clear();
		operatorListB.clear();
		
		operatorListA.add("4523 0.2");
		operatorListA.add("45 1.1");		
		
		Collections.sort(operatorListA);
		Collections.reverse(operatorListA);
		
		operatorListA = prepareOperatorData.getOperatorInfoList(operatorListA);
		
		operatorListB.add("462 0.8");
		operatorListB.add("46 1.0");
		
		Collections.sort(operatorListB);
		Collections.reverse(operatorListB);
		
		operatorListB = prepareOperatorData.getOperatorInfoList(operatorListB);
		
		result = routingPhoneCalls.getCheapOperatorByPhoneNumber(phoneNumber, operatorListA, operatorListB);
		assertEquals("4423548785 : no operator is found", result);
				
		operatorListA.clear();
		operatorListB.clear();
		
		operatorListA.add("4423 0.2");
		operatorListA.add("45 1.1");		
		
		Collections.sort(operatorListA);
		Collections.reverse(operatorListA);
		
		operatorListA = prepareOperatorData.getOperatorInfoList(operatorListA);
		
		operatorListB.add("462 0.8");
		operatorListB.add("44 0.2");
		
		Collections.sort(operatorListB);
		Collections.reverse(operatorListB);
		
		operatorListB = prepareOperatorData.getOperatorInfoList(operatorListB);
		
		result = routingPhoneCalls.getCheapOperatorByPhoneNumber(phoneNumber, operatorListA, operatorListB);
		assertEquals("4423548785 : operator A($0.2) and operator B($0.2) both are same", result);
	}
}
