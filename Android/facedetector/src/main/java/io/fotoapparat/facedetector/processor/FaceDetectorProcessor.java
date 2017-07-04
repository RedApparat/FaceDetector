package io.fotoapparat.facedetector.processor;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import java.util.List;

import io.fotoapparat.facedetector.FaceDetector;
import io.fotoapparat.facedetector.Rectangle;
import io.fotoapparat.preview.Frame;
import io.fotoapparat.preview.FrameProcessor;

/**
 * {@link FrameProcessor} which detects faces on camera frames.
 * <p>
 * Use {@link #with(Context)} to create a new instance.
 */
public class FaceDetectorProcessor implements FrameProcessor {

    private static Handler MAIN_THREAD_HANDLER = new Handler(Looper.getMainLooper());

    private final FaceDetector faceDetector;
    private final OnFacesDetectedListener listener;

    private FaceDetectorProcessor(Builder builder) {
        faceDetector = FaceDetector.create(builder.context);
        listener = builder.listener;
    }

    public static Builder with(Context context) {
        return new Builder(context);
    }

    @Override
    public void processFrame(Frame frame) {
        final List<Rectangle> faces = faceDetector.detectFaces(
                frame.image,
                frame.size.width,
                frame.size.height,
                frame.rotation
        );

        MAIN_THREAD_HANDLER.post(new Runnable() {
            @Override
            public void run() {
                listener.onFacesDetected(faces);
            }
        });
    }

    /**
     * Notified when faces are detected.
     */
    public interface OnFacesDetectedListener {

        /**
         * Null-object for {@link OnFacesDetectedListener}.
         */
        OnFacesDetectedListener NULL = new OnFacesDetectedListener() {
            @Override
            public void onFacesDetected(List<Rectangle> faces) {
                // Do nothing
            }
        };

        /**
         * Called when faces are detected. Always called on the main thread.
         *
         * @param faces detected faces. If no faces were detected - an empty list.
         */
        void onFacesDetected(List<Rectangle> faces);

    }

    /**
     * Builder for {@link FaceDetectorProcessor}.
     */
    public static class Builder {

        private final Context context;
        private OnFacesDetectedListener listener = OnFacesDetectedListener.NULL;

        private Builder(Context context) {
            this.context = context;
        }

        /**
         * @param listener which will be notified when faces are detected.
         */
        public Builder listener(OnFacesDetectedListener listener) {
            this.listener = listener != null
                    ? listener
                    : OnFacesDetectedListener.NULL;

            return this;
        }

        public FaceDetectorProcessor build() {
            return new FaceDetectorProcessor(this);
        }

    }

}
