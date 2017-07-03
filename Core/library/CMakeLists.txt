cmake_minimum_required(VERSION 3.7)
project(FaceDetector_library)

set(CMAKE_CXX_STANDARD 11)

find_package(OpenCV REQUIRED)

set(SOURCE_FILES
        src/FaceDetector.cpp src/FaceDetector.h)

add_library(faceDetector ${SOURCE_FILES})
target_include_directories(faceDetector PUBLIC src)