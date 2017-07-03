//
// Created by Dmitry Zaytsev on 03/07/2017.
//

#include "FaceDetector.h"
#include <util/cv.h>

using namespace fotoapparat;
using namespace std;
using namespace cv;

vector<Rect> FaceDetector::detectFaces(const Mat &image) {
    ensureLoaded();

    return detectOnDownscaledImage(image, 1280, [&](const Mat &downscaled) {
        vector<Rect> result;
        classifier.detectMultiScale(downscaled, result, 1.3, 5);

        return result;
    });
}
