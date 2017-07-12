cmake_minimum_required(VERSION 3.4.1)

# OpenCV
set(OpenCV_STATIC ON)
set(OpenCV_DIR ${OPENCV_HOME}/sdk/native/jni)
find_package (OpenCV REQUIRED)

# Djinni
include_directories(${DJINNI_HOME}/support-lib/jni)
include_directories(${CMAKE_CURRENT_SOURCE_DIR}/build/generated/cpp)
include_directories(${CMAKE_CURRENT_SOURCE_DIR}/build/generated/cpp/jni)

# FaceDetector
include_directories(../../Core/library/src)

# Android
find_library(log-lib log)

# Output
add_library(FaceDetector_android SHARED

    # Sources
    src/main/cpp/FaceDetectorImpl.cpp
    ../../Core/library/src/FaceDetector.cpp

    # Djinni
    ${DJINNI_HOME}/support-lib/jni/djinni_main.cpp
    ${DJINNI_HOME}/support-lib/jni/djinni_support.cpp
    ${CMAKE_CURRENT_SOURCE_DIR}/build/generated/cpp/jni/JniRectangle.cpp
    ${CMAKE_CURRENT_SOURCE_DIR}/build/generated/cpp/jni/JniFaceDetectorWrapper.cpp
)

target_link_libraries(FaceDetector_android ${OpenCV_LIBS} ${log-lib})