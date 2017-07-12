package io.fotoapparat.facedetector;

import android.content.Context;

import java.util.List;

/**
 * Detects faces on an image.
 */
public class FaceDetector {

    static {
        System.loadLibrary("FaceDetector_android");
    }

    private final AssetsExtractor assetsExtractor;

    private FaceDetectorWrapper faceDetectorWrapper = null;

    private FaceDetector(AssetsExtractor assetsExtractor) {
        this.assetsExtractor = assetsExtractor;
    }

    /**
     * @return new instance of {@link FaceDetector}.
     */
    public static FaceDetector create(Context context) {
        return new FaceDetector(
                new AssetsExtractor(context, "faceDetectorCascade.xml")
        );
    }

    /**
     * Detects faces on given NV21 image.
     *
     * @param nv21Image   image in NV21 format (the one which is usually sent by camera preview)
     * @param frameWidth  width of the image in pixels.
     * @param frameHeight height of the image in pixels.
     * @return list of detected faces in normalized coordinates.
     */
    public List<Rectangle> detectFaces(byte[] nv21Image, int frameWidth, int frameHeight, int frameRotationDegrees) {
        ensureInitialized();

        return faceDetectorWrapper.detectFaces(nv21Image, frameWidth, frameHeight, frameRotationDegrees);
    }

    private void ensureInitialized() {
        if (faceDetectorWrapper == null) {
            faceDetectorWrapper = FaceDetectorWrapper.create(
                    assetsExtractor.extractIfNeeded().getPath()
            );
        }
    }

}
