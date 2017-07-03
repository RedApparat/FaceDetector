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
using std::vector;
using std::function;

namespace fotoapparat {

    /**
     * Resizes image so that it's biggest side is not bigger than maxSide, preserving aspect ratio.
     *
     * @param source original image.
     * @param maxSide max desired side in pixels.
     * @return resized copy of an original image.
     */
    Mat resizeImage(Mat source, uint maxSide) {
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
     * Runs detection function on a downscaled image and returns result in coordinate space of original image.
     *
     * @param image original image.
     * @param maxSide max side to which image will be downscaled.
     * @param detect detection function.
     * @return result of detection in coordinate space of original image.
     */
    vector<Rect> detectOnDownscaledImage(const Mat &image, uint maxSide, function<vector<Rect>(const Mat &)> detect) {
        auto resizedImage = resizeImage(image, maxSide);

        const float scaleFactor = image.cols / (float) resizedImage.cols;

        auto downscaledRectangles = detect(resizedImage);

        vector<Rect> result(downscaledRectangles.size());

        for (auto rect : downscaledRectangles) {
            result.push_back(Rect(
                    (int) (rect.x * scaleFactor),
                    (int) (rect.y * scaleFactor),
                    (int) (rect.width * scaleFactor),
                    (int) (rect.height * scaleFactor)
            ));
        }

        return result;
    }

}

#endif //FACEDETECTOR_CV_H
