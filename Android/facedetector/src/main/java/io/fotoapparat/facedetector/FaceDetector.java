package io.fotoapparat.facedetector;

/**
 * Detects faces on an image.
 */
public class FaceDetector {

    static {
        System.loadLibrary("opencv_java3");
        System.loadLibrary("FaceDetector_android");
    }

}
