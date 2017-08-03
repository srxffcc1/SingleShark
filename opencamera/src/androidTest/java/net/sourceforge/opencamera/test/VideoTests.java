package net.sourceforge.opencamera.test;

import junit.framework.Test;
import junit.framework.TestSuite;
public class VideoTests {
	// Tests related to video recording; note that tests to do with video mode that don't record are still part of MainTests
	public static Test suite() {
		TestSuite suite = new TestSuite(MainTests.class.getName());
		suite.addTest(TestSuite.createTest(OpMainActivityTest.class, "testTakeVideo"));
		suite.addTest(TestSuite.createTest(OpMainActivityTest.class, "testTakeVideoAudioControl"));
		suite.addTest(TestSuite.createTest(OpMainActivityTest.class, "testTakeVideoSAF"));
		suite.addTest(TestSuite.createTest(OpMainActivityTest.class, "testTakeVideoSubtitles"));
		suite.addTest(TestSuite.createTest(OpMainActivityTest.class, "testTakeVideoSubtitlesGPS"));
		suite.addTest(TestSuite.createTest(OpMainActivityTest.class, "testImmersiveMode"));
		suite.addTest(TestSuite.createTest(OpMainActivityTest.class, "testImmersiveModeEverything"));
		suite.addTest(TestSuite.createTest(OpMainActivityTest.class, "testTakeVideoStabilization"));
		suite.addTest(TestSuite.createTest(OpMainActivityTest.class, "testTakeVideoExposureLock"));
		suite.addTest(TestSuite.createTest(OpMainActivityTest.class, "testTakeVideoFocusArea"));
		suite.addTest(TestSuite.createTest(OpMainActivityTest.class, "testTakeVideoQuick"));
		suite.addTest(TestSuite.createTest(OpMainActivityTest.class, "testTakeVideoQuickSAF"));
		suite.addTest(TestSuite.createTest(OpMainActivityTest.class, "testTakeVideoMaxDuration"));
		suite.addTest(TestSuite.createTest(OpMainActivityTest.class, "testTakeVideoMaxDurationRestart"));
		suite.addTest(TestSuite.createTest(OpMainActivityTest.class, "testTakeVideoMaxDurationRestartInterrupt"));
		suite.addTest(TestSuite.createTest(OpMainActivityTest.class, "testTakeVideoSettings"));
		suite.addTest(TestSuite.createTest(OpMainActivityTest.class, "testTakeVideoMacro"));
		suite.addTest(TestSuite.createTest(OpMainActivityTest.class, "testTakeVideoPause"));
		suite.addTest(TestSuite.createTest(OpMainActivityTest.class, "testTakeVideoPauseStop"));
		suite.addTest(TestSuite.createTest(OpMainActivityTest.class, "testTakeVideoFlashVideo"));
		suite.addTest(TestSuite.createTest(OpMainActivityTest.class, "testVideoTimerInterrupt"));
		suite.addTest(TestSuite.createTest(OpMainActivityTest.class, "testVideoPopup"));
		suite.addTest(TestSuite.createTest(OpMainActivityTest.class, "testVideoTimerPopup"));
		suite.addTest(TestSuite.createTest(OpMainActivityTest.class, "testTakeVideoAvailableMemory"));
		suite.addTest(TestSuite.createTest(OpMainActivityTest.class, "testTakeVideoAvailableMemory2"));
		suite.addTest(TestSuite.createTest(OpMainActivityTest.class, "testTakeVideoMaxFileSize1"));
		suite.addTest(TestSuite.createTest(OpMainActivityTest.class, "testTakeVideoMaxFileSize2"));
		suite.addTest(TestSuite.createTest(OpMainActivityTest.class, "testTakeVideoMaxFileSize3"));
		suite.addTest(TestSuite.createTest(OpMainActivityTest.class, "testTakeVideoForceFailure"));
		// put tests which change bitrate, fps or test 4K at end
		// update: now deprecating these tests, as setting these settings can be dodgy on some devices
		/*suite.addTest(TestSuite.createTest(OpMainActivityTest.class, "testTakeVideoBitrate"));
		suite.addTest(TestSuite.createTest(OpMainActivityTest.class, "testTakeVideoFPS"));
		suite.addTest(TestSuite.createTest(OpMainActivityTest.class, "testTakeVideo4K"));*/
        return suite;
    }
}
