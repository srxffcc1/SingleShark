package net.sourceforge.opencamera.test;

import junit.framework.Test;
import junit.framework.TestSuite;

public class PhotoCamera2Tests {
	// Tests related to taking photos that require Camera2 - only need to run this suite with Camera2
	public static Test suite() {
		TestSuite suite = new TestSuite(MainTests.class.getName());
		suite.addTest(TestSuite.createTest(OpMainActivityTest.class, "testTakePhotoManualFocus"));
		suite.addTest(TestSuite.createTest(OpMainActivityTest.class, "testTakePhotoManualISOExposure"));
		suite.addTest(TestSuite.createTest(OpMainActivityTest.class, "testTakePhotoManualWB"));
		suite.addTest(TestSuite.createTest(OpMainActivityTest.class, "testTakePhotoRaw"));
		suite.addTest(TestSuite.createTest(OpMainActivityTest.class, "testTakePhotoRawMulti"));
		suite.addTest(TestSuite.createTest(OpMainActivityTest.class, "testTakePhotoRawWaitCaptureResult"));
		suite.addTest(TestSuite.createTest(OpMainActivityTest.class, "testTakePhotoPreviewPausedTrashRaw"));
		suite.addTest(TestSuite.createTest(OpMainActivityTest.class, "testTakePhotoPreviewPausedTrashRaw2"));
		suite.addTest(TestSuite.createTest(OpMainActivityTest.class, "testTakePhotoExpo5"));
		suite.addTest(TestSuite.createTest(OpMainActivityTest.class, "testTakePhotoFlashAutoFakeMode"));
		suite.addTest(TestSuite.createTest(OpMainActivityTest.class, "testTakePhotoFlashOnFakeMode"));
        return suite;
    }
}
