//
// Created by Dmitry Zaytsev on 03/07/2017.
//

#ifndef FACEDETECTOR_CV_H
#define FACEDETECTOR_CV_H

#include <opencv2/core.hpp>
#include <opencv2/imgproc.hpp>

using cv::Mat;
using cv::Size;
using cv::Rect;
using cv::Rect2f;
using std::vector;

namespace fotoapparat {

    /**
     * Resizes image so that it's biggest side is not bigger than maxSide, preserving aspect ratio.
     *
     * @param source original image.
     * @param maxSide max desired side in pixels.
     * @return resized copy of an original image.
     */
    Mat resizeImage(const Mat &source, uint maxSide) {
        float scaleFactor = maxSide / (float) std::max(source.cols, source.rows);

        Size targetSize(
                (int) (source.cols * scaleFactor),
                (int) (source.rows * scaleFactor)
        );

        Mat resized;
        resize(source, resized, targetSize, 0, 0);

        return resized;
    }

    /**
     * Converts rectangles to normalized coordinates, where (0,0) depicts top left corner and (1,1) bottom right corner.
     *
     * @param sizeOfEnclosingSpace size of the space in which rectangles are contained. Usually size of the image.
     * @param rectangles rectangles to map.
     * @return vector of rectangles in normalized coordinates.
     */
    vector<Rect2f> toNormalizedCoordinates(const Size &sizeOfEnclosingSpace,
                                           const vector<Rect> &rectangles) {
        vector<Rect2f> result(rectangles.size());

        for (auto rectangle : rectangles) {
            result.push_back(Rect2f(
                    rectangle.x / (float) sizeOfEnclosingSpace.width,
                    rectangle.y / (float) sizeOfEnclosingSpace.height,
                    rectangle.width / (float) sizeOfEnclosingSpace.width,
                    rectangle.height / (float) sizeOfEnclosingSpace.height
            ));
        }

        return result;
    }

}

#endif //FACEDETECTOR_CV_H
