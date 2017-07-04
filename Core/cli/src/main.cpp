//
// Created by Dmitry Zaytsev on 04/07/2017.
//

#include <iostream>
#include <opencv2/imgcodecs.hpp>
#include <opencv/cv.hpp>
#include <FaceDetector.h>

using namespace std;
using namespace std::chrono;
using namespace cv;
using namespace fotoapparat;

template<typename T>
T measureTime(function<T()> func) {
    high_resolution_clock::time_point start = high_resolution_clock::now();
    auto result = func();
    high_resolution_clock::time_point end = high_resolution_clock::now();

    printf("Execution time: %lld ms", duration_cast<milliseconds>(end - start).count());

    return result;
}

Rect denormalizeRectangle(const Mat &image, const Rect2f &normalized) {
    return Rect(
            (int) (normalized.x * image.cols),
            (int) (normalized.y * image.rows),
            (int) (normalized.width * image.cols),
            (int) (normalized.height * image.rows)
    );
}

void displayResult(Mat image, vector<Rect2f> faces) {
    for (auto face : faces) {
        Rect denormalized = denormalizeRectangle(image, face);

        rectangle(image, denormalized, Scalar(255, 255, 0), 3);
    }

    imshow("Result", image);
    waitKey();
}

void printHelp() {
    printf("Arguments: [pre-trained classifier path] [image path]\n");
}

int main(int argumentsCount, char **arguments) {
    if (argumentsCount < 3) {
        printHelp();
        return 0;
    }

    string classifierPath = arguments[1];
    string imagePath = arguments[2];

    auto image = imread(imagePath);

    FaceDetector faceDetector(classifierPath);

    auto faces = measureTime<vector<Rect2f>>([&] {
        return faceDetector.detectFaces(image);
    });

    displayResult(image, faces);
}