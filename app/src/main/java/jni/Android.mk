# ANDROID_NDK := ~/Android/Sdk/ndk-bundle
# LOCAL_PATH := $(call my-dir)
# include $(CLEAR_VARS)
# LOCAL_C_INCLUDES := $(ANDROID_NDK)/sources/ffmpeg-3.0
# LOCAL_CFLAGS := -Wdeprecated-declarations
# LOCAL_MODULE := ffmpeg_jni
# ANDROID_LIB := -landroid
# LOCAL_LDLIBS += -llog -ljnigraphics -lz
# LOCAL_SRC_FILES := ffmpeg_jni.h ffmpeg_jni.c logjam.h
# LOCAL_ALLOW_UNDEFINED_SYMBOLS := true
# LOCAL_STATIC_LIBRARIES := libavdevice libavformat libavfilter libavcodec libwscale libavutil libswresample libswscale libpostproc
# include $(BUILD_SHARED_LIBRARY)
# $(call import-module,ffmpeg-3.0/android/aarch64)

LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)
LOCAL_MODULE := avcodec
LOCAL_SRC_FILES := libavcodec.so
include $(PREBUILT_SHARED_LIBRARY)

include $(CLEAR_VARS)
LOCAL_MODULE := avdevice
LOCAL_SRC_FILES := libavdevice.so
include $(PREBUILT_SHARED_LIBRARY)

include $(CLEAR_VARS)
LOCAL_MODULE := avfilter
LOCAL_SRC_FILES := libavfilter.so
include $(PREBUILT_SHARED_LIBRARY)

include $(CLEAR_VARS)
LOCAL_MODULE := avformat
LOCAL_SRC_FILES := libavformat.so
include $(PREBUILT_SHARED_LIBRARY)

include $(CLEAR_VARS)
LOCAL_MODULE := avutil
LOCAL_SRC_FILES := libavutil.so
include $(PREBUILT_SHARED_LIBRARY)

include $(CLEAR_VARS)
LOCAL_MODULE := swresample
LOCAL_SRC_FILES := libswresample.so
include $(PREBUILT_SHARED_LIBRARY)

include $(CLEAR_VARS)
LOCAL_MODULE := swscale
LOCAL_SRC_FILES := libswscale.so
include $(PREBUILT_SHARED_LIBRARY)

include $(CLEAR_VARS)
LOCAL_MODULE := ffmpeg_jni
LOCAL_SRC_FILES := ffmpeg_jni.h ffmpeg_jni.c logjam.h ffmpeg.h ffmpeg.c config.h cmdutils.c cmdutils.h ffmpeg_filter.c ffmpeg_opt.c
LOCAL_LDLIBS := -llog -ljnigraphics -lz -landroid
# LOCAL_ALLOW_UNDEFINED_SYMBOLS := true
LOCAL_C_INCLUDES := $(LOCAL_PATH)/include
LOCAL_SHARED_LIBRARIES := avcodec avdevice avfilter avformat avutil swresample swscale
include $(BUILD_SHARED_LIBRARY)

