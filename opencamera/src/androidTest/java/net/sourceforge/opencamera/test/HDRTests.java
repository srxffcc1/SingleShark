package net.sourceforge.opencamera.test;

import junit.framework.Test;
import junit.framework.TestSuite;

public class HDRTests {
	/** Tests for HDR algorithm - only need to run on a single device
	 *  Should manually look over the images dumped onto DCIM/
	 *  To use these tests, the testdata/ subfolder should be manually copied to the test device in the DCIM/testOpenCamera/
	 *  folder (so you have DCIM/testOpenCamera/testdata/). We don't use assets/ as we'd end up with huge APK sizes which takes
	 *  time to transfer to the device everytime we run the tests.
	 */
	public static Test suite() {
		TestSuite suite = new TestSuite(MainTests.class.getName());
		suite.addTest(TestSuite.createTest(OpMainActivityTest.class, "testDROZero"));
		suite.addTest(TestSuite.createTest(OpMainActivityTest.class, "testHDR1"));
		suite.addTest(TestSuite.createTest(OpMainActivityTest.class, "testHDR2"));
		suite.addTest(TestSuite.createTest(OpMainActivityTest.class, "testHDR3"));
		suite.addTest(TestSuite.createTest(OpMainActivityTest.class, "testHDR4"));
		suite.addTest(TestSuite.createTest(OpMainActivityTest.class, "testHDR5"));
		suite.addTest(TestSuite.createTest(OpMainActivityTest.class, "testHDR6"));
		suite.addTest(TestSuite.createTest(OpMainActivityTest.class, "testHDR7"));
		suite.addTest(TestSuite.createTest(OpMainActivityTest.class, "testHDR8"));
		suite.addTest(TestSuite.createTest(OpMainActivityTest.class, "testHDR9"));
		suite.addTest(TestSuite.createTest(OpMainActivityTest.class, "testHDR10"));
		suite.addTest(TestSuite.createTest(OpMainActivityTest.class, "testHDR11"));
		suite.addTest(TestSuite.createTest(OpMainActivityTest.class, "testHDR12"));
		suite.addTest(TestSuite.createTest(OpMainActivityTest.class, "testHDR13"));
		suite.addTest(TestSuite.createTest(OpMainActivityTest.class, "testHDR14"));
		suite.addTest(TestSuite.createTest(OpMainActivityTest.class, "testHDR15"));
		suite.addTest(TestSuite.createTest(OpMainActivityTest.class, "testHDR16"));
		suite.addTest(TestSuite.createTest(OpMainActivityTest.class, "testHDR17"));
		suite.addTest(TestSuite.createTest(OpMainActivityTest.class, "testHDR18"));
		suite.addTest(TestSuite.createTest(OpMainActivityTest.class, "testHDR19"));
		suite.addTest(TestSuite.createTest(OpMainActivityTest.class, "testHDR20"));
		suite.addTest(TestSuite.createTest(OpMainActivityTest.class, "testHDR21"));
		suite.addTest(TestSuite.createTest(OpMainActivityTest.class, "testHDR22"));
		suite.addTest(TestSuite.createTest(OpMainActivityTest.class, "testHDR23"));
		suite.addTest(TestSuite.createTest(OpMainActivityTest.class, "testHDR24"));
		suite.addTest(TestSuite.createTest(OpMainActivityTest.class, "testHDR25"));
		suite.addTest(TestSuite.createTest(OpMainActivityTest.class, "testHDR26"));
		suite.addTest(TestSuite.createTest(OpMainActivityTest.class, "testHDR27"));
		suite.addTest(TestSuite.createTest(OpMainActivityTest.class, "testHDR28"));
		suite.addTest(TestSuite.createTest(OpMainActivityTest.class, "testHDR29"));
		suite.addTest(TestSuite.createTest(OpMainActivityTest.class, "testHDR30"));
		suite.addTest(TestSuite.createTest(OpMainActivityTest.class, "testHDR31"));
		suite.addTest(TestSuite.createTest(OpMainActivityTest.class, "testHDR32"));
		suite.addTest(TestSuite.createTest(OpMainActivityTest.class, "testHDR33"));
		suite.addTest(TestSuite.createTest(OpMainActivityTest.class, "testHDR34"));
		suite.addTest(TestSuite.createTest(OpMainActivityTest.class, "testHDR35"));
		suite.addTest(TestSuite.createTest(OpMainActivityTest.class, "testHDR36"));
		suite.addTest(TestSuite.createTest(OpMainActivityTest.class, "testHDR37"));
		suite.addTest(TestSuite.createTest(OpMainActivityTest.class, "testHDR38"));
        return suite;
    }
}
