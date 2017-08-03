package net.sourceforge.opencamera.test;

import junit.framework.Test;
import junit.framework.TestSuite;

public class PhotoTests {
	// Tests related to taking photos; note that tests to do with photo mode that don't take photos are still part of MainTests
	public static Test suite() {
		TestSuite suite = new TestSuite(MainTests.class.getName());
		// put these tests first as they require various permissions be allowed, that can only be set by user action
		suite.addTest(TestSuite.createTest(OpMainActivityTest.class, "testTakePhotoSAF"));
		suite.addTest(TestSuite.createTest(OpMainActivityTest.class, "testLocationOn"));
		suite.addTest(TestSuite.createTest(OpMainActivityTest.class, "testLocationDirectionOn"));
		suite.addTest(TestSuite.createTest(OpMainActivityTest.class, "testLocationOff"));
		suite.addTest(TestSuite.createTest(OpMainActivityTest.class, "testDirectionOn"));
		// other tests:
		suite.addTest(TestSuite.createTest(OpMainActivityTest.class, "testTakePhoto"));
		suite.addTest(TestSuite.createTest(OpMainActivityTest.class, "testTakePhotoContinuous"));
		suite.addTest(TestSuite.createTest(OpMainActivityTest.class, "testTakePhotoContinuousNoTouch"));
		suite.addTest(TestSuite.createTest(OpMainActivityTest.class, "testTakePhotoAutoStabilise"));
		suite.addTest(TestSuite.createTest(OpMainActivityTest.class, "testTakePhotoFlashAuto"));
		suite.addTest(TestSuite.createTest(OpMainActivityTest.class, "testTakePhotoFlashOn"));
		suite.addTest(TestSuite.createTest(OpMainActivityTest.class, "testTakePhotoFlashTorch"));
		suite.addTest(TestSuite.createTest(OpMainActivityTest.class, "testTakePhotoAudioButton"));
		suite.addTest(TestSuite.createTest(OpMainActivityTest.class, "testTakePhotoNoAutofocus"));
		suite.addTest(TestSuite.createTest(OpMainActivityTest.class, "testTakePhotoNoThumbnail"));
		suite.addTest(TestSuite.createTest(OpMainActivityTest.class, "testTakePhotoFlashBug"));
		suite.addTest(TestSuite.createTest(OpMainActivityTest.class, "testTakePhotoFrontCamera"));
		suite.addTest(TestSuite.createTest(OpMainActivityTest.class, "testTakePhotoFrontCameraScreenFlash"));
		suite.addTest(TestSuite.createTest(OpMainActivityTest.class, "testTakePhotoAutoFocus"));
		suite.addTest(TestSuite.createTest(OpMainActivityTest.class, "testTakePhotoLockedFocus"));
		suite.addTest(TestSuite.createTest(OpMainActivityTest.class, "testTakePhotoExposureCompensation"));
		suite.addTest(TestSuite.createTest(OpMainActivityTest.class, "testTakePhotoLockedLandscape"));
		suite.addTest(TestSuite.createTest(OpMainActivityTest.class, "testTakePhotoLockedPortrait"));
		suite.addTest(TestSuite.createTest(OpMainActivityTest.class, "testTakePhotoPreviewPaused"));
		suite.addTest(TestSuite.createTest(OpMainActivityTest.class, "testTakePhotoPreviewPausedAudioButton"));
		suite.addTest(TestSuite.createTest(OpMainActivityTest.class, "testTakePhotoPreviewPausedSAF"));
		suite.addTest(TestSuite.createTest(OpMainActivityTest.class, "testTakePhotoPreviewPausedTrash"));
		suite.addTest(TestSuite.createTest(OpMainActivityTest.class, "testTakePhotoPreviewPausedTrashSAF"));
		suite.addTest(TestSuite.createTest(OpMainActivityTest.class, "testTakePhotoPreviewPausedTrash2"));
		suite.addTest(TestSuite.createTest(OpMainActivityTest.class, "testTakePhotoQuickFocus"));
		suite.addTest(TestSuite.createTest(OpMainActivityTest.class, "testTakePhotoRepeatFocus"));
		suite.addTest(TestSuite.createTest(OpMainActivityTest.class, "testTakePhotoRepeatFocusLocked"));
		suite.addTest(TestSuite.createTest(OpMainActivityTest.class, "testTakePhotoAfterFocus"));
		suite.addTest(TestSuite.createTest(OpMainActivityTest.class, "testTakePhotoSingleTap"));
		suite.addTest(TestSuite.createTest(OpMainActivityTest.class, "testTakePhotoDoubleTap"));
		suite.addTest(TestSuite.createTest(OpMainActivityTest.class, "testTakePhotoAlt"));
		suite.addTest(TestSuite.createTest(OpMainActivityTest.class, "testTakePhotoAutoLevel"));
		suite.addTest(TestSuite.createTest(OpMainActivityTest.class, "testTakePhotoAutoLevelAngles"));
		suite.addTest(TestSuite.createTest(OpMainActivityTest.class, "testTimerBackground"));
		suite.addTest(TestSuite.createTest(OpMainActivityTest.class, "testTimerSettings"));
		suite.addTest(TestSuite.createTest(OpMainActivityTest.class, "testTimerPopup"));
		suite.addTest(TestSuite.createTest(OpMainActivityTest.class, "testTakePhotoBurst"));
		suite.addTest(TestSuite.createTest(OpMainActivityTest.class, "testContinuousPicture1"));
		suite.addTest(TestSuite.createTest(OpMainActivityTest.class, "testContinuousPicture2"));
		suite.addTest(TestSuite.createTest(OpMainActivityTest.class, "testContinuousPictureFocusBurst"));
		suite.addTest(TestSuite.createTest(OpMainActivityTest.class, "testPhotoStamp"));
		suite.addTest(TestSuite.createTest(OpMainActivityTest.class, "testTakePhotoDRO"));
		suite.addTest(TestSuite.createTest(OpMainActivityTest.class, "testTakePhotoDROPhotoStamp"));
		suite.addTest(TestSuite.createTest(OpMainActivityTest.class, "testTakePhotoHDR"));
		suite.addTest(TestSuite.createTest(OpMainActivityTest.class, "testTakePhotoHDRSaveExpo"));
		suite.addTest(TestSuite.createTest(OpMainActivityTest.class, "testTakePhotoHDRFrontCamera"));
		suite.addTest(TestSuite.createTest(OpMainActivityTest.class, "testTakePhotoHDRAutoStabilise"));
		suite.addTest(TestSuite.createTest(OpMainActivityTest.class, "testTakePhotoHDRPhotoStamp"));
		suite.addTest(TestSuite.createTest(OpMainActivityTest.class, "testTakePhotoExpo"));
		suite.addTest(TestSuite.createTest(OpMainActivityTest.class, "testCreateSaveFolder1"));
		suite.addTest(TestSuite.createTest(OpMainActivityTest.class, "testCreateSaveFolder2"));
		suite.addTest(TestSuite.createTest(OpMainActivityTest.class, "testCreateSaveFolder3"));
		suite.addTest(TestSuite.createTest(OpMainActivityTest.class, "testCreateSaveFolder4"));
		suite.addTest(TestSuite.createTest(OpMainActivityTest.class, "testCreateSaveFolderUnicode"));
		suite.addTest(TestSuite.createTest(OpMainActivityTest.class, "testCreateSaveFolderEmpty"));
        return suite;
    }
}
