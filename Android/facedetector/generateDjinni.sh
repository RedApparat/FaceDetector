sh $1 \
   --java-out build/generated/java/io/fotoapparat/facedetector \
   --java-package io.fotoapparat.facedetector \
   --cpp-out build/generated/cpp \
   --jni-out build/generated/cpp/jni \
   --ident-jni-class JniFooBar \
   --ident-jni-file JniFooBar \
   --ident-cpp-file DjinniFooBar \
   --idl src/main/djinni/FaceDetector.djinni