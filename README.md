# FaceDetector

Want to detect human faces on a camera preview stream in real time? Well, you came to the right place.

FaceDetector is:

- detects faces
- works on Android
- very simple to use
- comes with (https://github.com/Fotoapparat/Fotoapparat)[Fotoapparat] integration (but you can use it with whatever you like)
- with a C++ core which can easily be ported to iOS (we have plans for that)

## How it works

For simplicity let's assume that you are using Fotoapparat as your camera library. If you don't - plese refer to `FaceDetector` documentation (it is just 1 method).

### Step One

If you would like to display detected faces right on top of camera, set up your layout as following (otherwise skip this step).

```xml
<io.fotoapparat.facedetector.view.CameraOverlayLayout
        android:layout_width="match_parent"
        android:layout_height="match_parent">

    <!-- Adjust parameters as you like. But cameraView has to be inside CameraOverlayLayout -->
    <io.fotoapparat.view.CameraView
        android:id="@+id/cameraView"
        android:layout_width="match_parent"
        android:layout_height="match_parent" />

    <!-- This view will display detected faces -->
    <io.fotoapparat.facedetector.view.RectanglesView
        android:id="@+id/rectanglesView"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        app:rectanglesColor="@color/colorAccent"
        app:rectanglesStrokeWidth="2dp"/>

</io.fotoapparat.facedetector.view.CameraOverlayLayout>
```

### Step Two

Create `FaceDetectorProcessor`:

```java
FaceDetectorProcessor processor = FaceDetectorProcessor.with(this)
    .listener(faces -> {
        rectanglesView.setRectangles(faces);  // (Optional) Show detected faces on the view.
        
        // ... or do whatever you want with the result
    })
    .build()
```

### Step Three

Attach it as processor to `Fotoapparat`

```java
Fotoapparat.with(this)
    .frameProcessor(processor)
    // the rest of configuration
```

And you are good to go!

## Set up

Add dependency to your `build.grade`

```groovy

```
