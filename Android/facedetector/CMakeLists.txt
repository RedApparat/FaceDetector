cmake_minimum_required(VERSION 3.4.1)

# OpenCV
include_directories(${OPENCV_HOME}/sdk/native/jni/include)
add_library( lib_opencv SHARED IMPORTED )
set_target_properties(lib_opencv PROPERTIES IMPORTED_LOCATION ${CMAKE_CURRENT_SOURCE_DIR}/src/main/jniLibs/${ANDROID_ABI}/libopencv_java3.so)

# Djinni
include_directories(${DJINNI_HOME}/support-lib/jni)
include_directories(${CMAKE_CURRENT_SOURCE_DIR}/build/generated/cpp)
include_directories(${CMAKE_CURRENT_SOURCE_DIR}/build/generated/cpp/jni)

# FaceDetector
include_directories(../../Core/library/src)

# Android
find_library(log-lib log)

# Output
add_library( # Sets the name of the library.
             FaceDetector_android

             # Sets the library as a shared library.
             SHARED

             # Provides a relative path to your source file(s).
             src/main/cpp/FaceDetectorImpl.cpp
             ../../Core/library/src/FaceDetector.cpp

             # Djinni
             ${DJINNI_HOME}/support-lib/jni/djinni_main.cpp
             ${DJINNI_HOME}/support-lib/jni/djinni_support.cpp
             ${CMAKE_CURRENT_SOURCE_DIR}/build/generated/cpp/jni/JniRectangle.cpp
             ${CMAKE_CURRENT_SOURCE_DIR}/build/generated/cpp/jni/JniFaceDetectorWrapper.cpp

             )

target_link_libraries(FaceDetector_android lib_opencv ${log-lib})