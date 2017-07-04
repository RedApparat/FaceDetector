//
// Created by Dmitry Zaytsev on 04/07/2017.
//

#include <DjinniFaceDetectorWrapper.hpp>
#include <DjinniRectangle.hpp>
#include <opencv2/imgproc.hpp>
#include <FaceDetector.h>

using namespace std;

vector<Rectangle> toRectangles(vector<cv::Rect2f> input) {
    vector<Rectangle> result;

    for (auto rectangle : input) {
        result.push_back(Rectangle(
                rectangle.x,
                rectangle.y,
                rectangle.width,
                rectangle.height
        ));
    }

    return result;
}

void rotateImage(cv::Mat &image, int32_t rotationDegrees) {
    switch (rotationDegrees) {
        case 90:
            cv::rotate(image, image, cv::ROTATE_90_COUNTERCLOCKWISE);
            break;
        case 180:
            cv::rotate(image, image, cv::ROTATE_180);
            break;
        case 270:
            cv::rotate(image, image, cv::ROTATE_90_CLOCKWISE);
            break;
        default:
            break;
    }
}

class FaceDetectorImpl : public FaceDetectorWrapper {
public:

    FaceDetectorImpl(std::string classifierPath) : faceDetector(classifierPath) {
    }

    virtual vector<Rectangle> detectFaces(const std::vector<uint8_t> &image,
                                          int32_t frameWidth,
                                          int32_t frameHeight,
                                          int32_t rotationDegrees) override {
        cv::Mat nv21(frameHeight + frameHeight / 2, frameWidth, CV_8UC1, (uchar *) image.data());
        cv::cvtColor(nv21, nv21, CV_YUV2RGBA_NV21);

        rotateImage(nv21, rotationDegrees);

        auto faces = faceDetector.detectFaces(nv21);

        return toRectangles(faces);
    }

private:

    fotoapparat::FaceDetector faceDetector;

};

shared_ptr<FaceDetectorWrapper> FaceDetectorWrapper::create(const string &classifierPath) {
    return std::make_shared<FaceDetectorImpl>(classifierPath);
}