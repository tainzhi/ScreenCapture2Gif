//
// Created by muqing on 4/1/16.
//

#ifndef __FR_ENOENT_VIDEOKIT_VIDEOKIT_H
#define __FR_ENOENT_VIDEOKIT_VIDEOKIT_H

#include <jni.h>

#ifdef __cplusplus
    extern "C" {
#endif // __cplusplus

JNIEXPORT jboolean JNICALL ffmpeg_entry(JNIEnv *, jobject, jobjectArray);

#ifdef __cplusplus
    }
#endif // __cplusplus

#endif // __FR_ENOENT_VIDEOKIT_VIDEOKIT_H
