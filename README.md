#ScreenCapture2Gif#
- 录屏(已完成)
- 把录屏转为gif(因为要使用ffmpeg, 但是ffmpeg编译的过程中遇到了麻烦, 暂时搁置)

##Notes
### 至少android5.0###
因为使用了在android5.0引入的接口`MediaProjection`

### 目前遇到的问题###
**怎么用ndk编译ffmpeg到arm64-v8a**
### 参考###
http://www.cnblogs.com/hibraincol/archive/2011/05/30/2063847.html
https://trac.ffmpeg.org/wiki/CompilationGuide/Android
https://www.google.com.tw/webhp?sourceid=chrome-instant&ion=1&espv=2&ie=UTF-8#q=androidstudio+ndk
http://developer.android.com/intl/zh-cn/ndk/guides/application_mk.html
https://github.com/dxjia/GifAssistant
https://github.com/Kernald/ffmpeg-android
http://enoent.fr/blog/2014/06/20/compile-ffmpeg-for-android/
https://github.com/JakeWharton/Telecine

###Video to Gif###

https://github.com/quackware/GIFDroid

##Todo##
- [ ] Settings选项: name, resolution 480x270, delay 100
- [ ] 绘制圆底start
- [ ] Martial Design
- [ ] 判断当前的屏幕分辨率, 然后缩放
- [ ] 绘制AppIcon(Android图标+GIF)
- [ ] Settings: from Video or from pictures
