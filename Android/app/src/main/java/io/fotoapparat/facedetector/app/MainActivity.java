package io.fotoapparat.facedetector.app;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import io.fotoapparat.facedetector.FaceDetector;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FaceDetector faceDetector = new FaceDetector();
    }

}
