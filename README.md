# FaceDetector

Want to detect human faces on a camera preview stream in real time? Well, you came to the right place.

FaceDetector is a library which:

- detects faces
- works on Android
- very simple to use
- works greatly with [Fotoapparat](https://github.com/Fotoapparat/Fotoapparat)
- you can use it with whichever camera library or source you like
- uses C++ core which can easily be ported to iOS (we have plans for that)

Detecting faces with `Fotoapparat` is as simple as:

```java
Fotoapparat
    .with(context)
    .into(cameraView)
    .frameProcessor(
      FaceDetectorProcessor
        .with(context)
        .build()
    )
    .build()
```


<img src="/screenshot.gif" width="200" />


## How it works

### Step One (optional)

To display detected faces on top of the camera view, set up your layout as following.

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

Attach the processor to `Fotoapparat`

```java
Fotoapparat.with(this)
    .into(cameraView)
    // the rest of configuration
    .frameProcessor(processor)
    .build()
```

And you are good to go!

## Set up

Add dependency to your `build.gradle`

```groovy
repositories {
    maven {
        url  "http://dl.bintray.com/fotoapparat/fotoapparat"
    }
}

compile 'io.fotoapparat:facedetector:1.0.0'

// If you are using Fotoapparat add this one as well
compile 'io.fotoapparat.fotoapparat:library:1.2.0' // or later version
```

## Contact us

Impressed? We are actually open for your projects.

If you want some particular computer vision algorithm (document recognition, photo processing or more), drop us a line at fotoapparat.team@gmail.com.

## License

```
Copyright 2017 Fotoapparat

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
