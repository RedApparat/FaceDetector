//
// Created by Dmitry Zaytsev on 03/07/2017.
//

#ifndef FACEDETECTOR_FACEDETECTOR_H
#define FACEDETECTOR_FACEDETECTOR_H

#include <opencv2/core.hpp>
#include <opencv2/objdetect.hpp>

namespace fotoapparat {

    class FaceDetector {
    public:

        FaceDetector(std::string classifierPath) : classifierPath(classifierPath), classifier() {
        }

        std::vector<cv::Rect> detectFaces(const cv::Mat &image);

    private:

        const std::string classifierPath;
        cv::CascadeClassifier classifier;

        bool loaded = false;

        void ensureLoaded() {
            if (!loaded) {
                classifier.load(classifierPath);
                loaded = true;
            }
        }

    };

}


#endif //FACEDETECTOR_FACEDETECTOR_H
