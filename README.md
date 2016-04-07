#ScreenCapture2Gif#
- 录屏(已完成)
- 把录屏转为gif

##Notes
### 至少android5.0###
因为使用了在android5.0引入的接口`MediaProjection`

### show touch feedback###
http://stackoverflow.com/questions/14374169/android-implement-show-touches-programatically
https://github.com/googlesamples/android-ScreenCapture

### transplant ffmpeg to android
https://github.com/tainzhi/ffmpeg-for-android-shared-library

### use ffmpeg jni to get gif from video
https://github.com/tainzhi/ffmpeg-jni-example

##Todo##
- [+] 绘制圆底start
- [+] Settings选项: name, resolution 480x270, delay 100
- [+] 判断当前的屏幕分辨率, 然后缩放
- [+] 绘制AppIcon(Android图标+GIF)
- [+] Settings: from Video or from pictures
- [+] notification
- [ ] video to gif
- [ ] Martial Design
- [ ] 后台执行任务
- [ ] gif drawable
- [ ] 

## References
[GifAssistant](https://github.com/dxjia/GifAssistant)
[Record Screen: Telecine](https://github.com/JakeWharton/Telecine)