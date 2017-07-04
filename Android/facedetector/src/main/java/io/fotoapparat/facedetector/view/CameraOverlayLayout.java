package io.fotoapparat.facedetector.view;

import android.content.Context;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import io.fotoapparat.view.CameraView;

/**
 * Behaves like {@link android.widget.FrameLayout}, but laids out every children to be as big as
 * preview size. This simplifies drawing on top of the camera stream.
 */
public class CameraOverlayLayout extends FrameLayout {

    public CameraOverlayLayout(@NonNull Context context) {
        super(context);
    }

    public CameraOverlayLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CameraOverlayLayout(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        final CameraView cameraView = findCameraView();
        final View textureView = findTextureView(cameraView);

        final int childrenCount = getChildCount();

        for (int i = 0; i < childrenCount; i++) {
            View view = getChildAt(i);

            if (view == cameraView) {
                view.layout(left, top, right, bottom);
            } else {
                view.layout(
                        textureView.getLeft(),
                        textureView.getTop(),
                        textureView.getRight(),
                        textureView.getBottom()
                );
            }
        }
    }

    private CameraView findCameraView() {
        final int childrenCount = getChildCount();
        for (int i = 0; i < childrenCount; i++) {
            View view = getChildAt(i);

            if (view instanceof CameraView) {
                return (CameraView) view;
            }
        }

        throw new IllegalStateException("Can't find CameraView");
    }

    private View findTextureView(View view) {
        View queryView = view;
        while (queryView instanceof ViewGroup) {
            queryView = ((ViewGroup) queryView).getChildAt(0);

            if (queryView instanceof TextureView) {
                return queryView;
            }
        }

        throw new IllegalStateException("Can't find TextureView");
    }

}
