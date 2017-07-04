cmake_minimum_required(VERSION 3.7)
project(FaceDetector_cli)

set(CMAKE_CXX_STANDARD 11)

find_package(OpenCV REQUIRED)

set(SOURCE_FILES src/main.cpp)

add_executable(cli ${SOURCE_FILES})
target_link_libraries(cli faceDetector ${OpenCV_LIBS})