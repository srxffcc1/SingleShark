package net.sourceforge.opencamera.test;

import junit.framework.Test;
import junit.framework.TestSuite;

public class MainTests {
	// Tests that don't fit into another of the Test suites
	public static Test suite() {
        /*return new TestSuiteBuilder(AllTests.class)
        .includeAllPackagesUnderHere()
        .build();*/
		TestSuite suite = new TestSuite(MainTests.class.getName());
		// put these tests first as they require various permissions be allowed, that can only be set by user action
		suite.addTest(TestSuite.createTest(OpMainActivityTest.class, "testSwitchVideo"));
		// other tests:
		suite.addTest(TestSuite.createTest(OpMainActivityTest.class, "testPause"));
		suite.addTest(TestSuite.createTest(OpMainActivityTest.class, "testImmediatelyQuit"));
		suite.addTest(TestSuite.createTest(OpMainActivityTest.class, "testStartCameraPreviewCount"));
		suite.addTest(TestSuite.createTest(OpMainActivityTest.class, "testSaveVideoMode"));
		suite.addTest(TestSuite.createTest(OpMainActivityTest.class, "testSaveFocusMode"));
		suite.addTest(TestSuite.createTest(OpMainActivityTest.class, "testSaveFlashTorchQuit"));
		//suite.addTest(TestSuite.createTest(OpMainActivityTest.class, "testSaveFlashTorchSwitchCamera"));
		suite.addTest(TestSuite.createTest(OpMainActivityTest.class, "testFlashStartup"));
		suite.addTest(TestSuite.createTest(OpMainActivityTest.class, "testFlashStartup2"));
		suite.addTest(TestSuite.createTest(OpMainActivityTest.class, "testPreviewSize"));
		suite.addTest(TestSuite.createTest(OpMainActivityTest.class, "testPreviewSizeWYSIWYG"));
		suite.addTest(TestSuite.createTest(OpMainActivityTest.class, "testAutoFocus"));
		suite.addTest(TestSuite.createTest(OpMainActivityTest.class, "testAutoFocusCorners"));
		suite.addTest(TestSuite.createTest(OpMainActivityTest.class, "testPopup"));
		suite.addTest(TestSuite.createTest(OpMainActivityTest.class, "testSwitchResolution"));
		suite.addTest(TestSuite.createTest(OpMainActivityTest.class, "testFaceDetection"));
		suite.addTest(TestSuite.createTest(OpMainActivityTest.class, "testFocusFlashAvailability"));
		suite.addTest(TestSuite.createTest(OpMainActivityTest.class, "testFocusSwitchVideoSwitchCameras"));
		suite.addTest(TestSuite.createTest(OpMainActivityTest.class, "testFocusRemainMacroSwitchCamera"));
		suite.addTest(TestSuite.createTest(OpMainActivityTest.class, "testFocusRemainMacroSwitchPhoto"));
		suite.addTest(TestSuite.createTest(OpMainActivityTest.class, "testFocusSaveMacroSwitchPhoto"));
		suite.addTest(TestSuite.createTest(OpMainActivityTest.class, "testFocusSwitchVideoResetContinuous"));
		suite.addTest(TestSuite.createTest(OpMainActivityTest.class, "testContinuousPictureFocus"));
		suite.addTest(TestSuite.createTest(OpMainActivityTest.class, "testContinuousPictureRepeatTouch"));
		suite.addTest(TestSuite.createTest(OpMainActivityTest.class, "testContinuousPictureSwitchAuto"));
		suite.addTest(TestSuite.createTest(OpMainActivityTest.class, "testContinuousVideoFocusForPhoto"));
		suite.addTest(TestSuite.createTest(OpMainActivityTest.class, "testStartupAutoFocus"));
		suite.addTest(TestSuite.createTest(OpMainActivityTest.class, "testExposureLockNotSaved"));
		suite.addTest(TestSuite.createTest(OpMainActivityTest.class, "testSaveQuality"));
		suite.addTest(TestSuite.createTest(OpMainActivityTest.class, "testZoom"));
		suite.addTest(TestSuite.createTest(OpMainActivityTest.class, "testZoomIdle"));
		suite.addTest(TestSuite.createTest(OpMainActivityTest.class, "testZoomSwitchCamera"));
		suite.addTest(TestSuite.createTest(OpMainActivityTest.class, "testSwitchCameraIdle"));
		suite.addTest(TestSuite.createTest(OpMainActivityTest.class, "testSwitchCameraRepeat"));
		suite.addTest(TestSuite.createTest(OpMainActivityTest.class, "testTouchFocusQuick"));
		suite.addTest(TestSuite.createTest(OpMainActivityTest.class, "testGallery"));
		suite.addTest(TestSuite.createTest(OpMainActivityTest.class, "testSettings"));
		suite.addTest(TestSuite.createTest(OpMainActivityTest.class, "testFolderChooserNew"));
		suite.addTest(TestSuite.createTest(OpMainActivityTest.class, "testFolderChooserInvalid"));
		suite.addTest(TestSuite.createTest(OpMainActivityTest.class, "testSaveFolderHistory"));
		suite.addTest(TestSuite.createTest(OpMainActivityTest.class, "testSaveFolderHistorySAF"));
		suite.addTest(TestSuite.createTest(OpMainActivityTest.class, "testPreviewRotation"));
		suite.addTest(TestSuite.createTest(OpMainActivityTest.class, "testSceneMode"));
		suite.addTest(TestSuite.createTest(OpMainActivityTest.class, "testColorEffect"));
		suite.addTest(TestSuite.createTest(OpMainActivityTest.class, "testWhiteBalance"));
		suite.addTest(TestSuite.createTest(OpMainActivityTest.class, "testImageQuality"));
		suite.addTest(TestSuite.createTest(OpMainActivityTest.class, "testFailOpenCamera"));
		suite.addTest(TestSuite.createTest(OpMainActivityTest.class, "testAudioControlIcon"));
		suite.addTest(TestSuite.createTest(OpMainActivityTest.class, "testOnError"));
		suite.addTest(TestSuite.createTest(OpMainActivityTest.class, "testGPSString"));
        return suite;
    }
}
