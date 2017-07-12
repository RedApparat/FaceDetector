//
// Created by Dmitry Zaytsev on 03/07/2017.
//

#include "FaceDetector.h"
#include <util/cv.h>

using namespace fotoapparat;
using namespace std;
using namespace cv;

vector<Rect2f> FaceDetector::detectFaces(const Mat &image) {
    ensureLoaded();

    auto resizedImage = resizeImage(image, 1280);

    vector<Rect> faces;
    classifier.detectMultiScale(resizedImage, faces, 1.1, 7);

    return toNormalizedCoordinates(Size(resizedImage.cols, resizedImage.rows), faces);
}
