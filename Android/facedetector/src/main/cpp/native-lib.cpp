#include "../../../../../../../Library/Android/sdk/ndk-bundle/platforms/android-15/arch-x86/usr/include/jni.h"
#include <string>

extern "C"
JNIEXPORT jstring JNICALL
Java_io_fotoapparat_facedetector_MainActivity_stringFromJNI(
        JNIEnv *env,
        jobject /* this */) {
    std::string hello = "Hello from C++";
    return env->NewStringUTF(hello.c_str());
}
