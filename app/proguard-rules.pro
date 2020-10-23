# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile


#（Basic）输出混淆日志
-verbose
#混淆注意事项第一条，保留四大组件及Android的其它组件
-keep public class * extends android.app.Activity
#（Basic）
-keepclassmembers class * extends android.app.Activity {
   public void *(android.view.View);
}
-keep public class * extends com.yxws.mvp.mvp.XActivity {
   public void *(android.view.View);
   public <methods>;#保持该类下所有的共有方法不被混淆
   public *;#保持该类下所有的共有内容不被混淆
   private <methods>;#保持该类下所有的私有方法不被混淆
   private *;#保持该类下所有的私有内容不被混淆
   public <init>(java.lang.String);#保持该类的String类型的构造方法
}
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.app.backup.BackupAgentHelper
-keep public class * extends android.preference.Preference
#（Basic）
-keep public class com.google.vending.licensing.ILicensingService
#（Basic）
-keep public class com.android.vending.licensing.ILicensingService
#（Basic）混淆注意事项第二条，保持 native 方法不被混淆
-keepclasseswithmembernames class * {
    native <methods>;
}
# 混淆注意事项第四条，保持WebView中JavaScript调用的方法
-keepclassmembers class * {
    @android.webkit.JavascriptInterface <methods>;
}
#混淆注意事项第五条 自定义View （Basic）
-keepclassmembers public class * extends android.view.View {
   void set*(***);
   *** get*();
}
# （Basic）混淆注意事项第七条，保持 Parcelable 不被混淆
-keepclassmembers class * implements android.os.Parcelable {
  public static final android.os.Parcelable$Creator CREATOR;
}
#（Basic） 混淆注意事项第八条，保持枚举 enum 类不被混淆
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}
#（Basic）
-keepclassmembers class **.R$* {
    public static <fields>;
}
#（Basic）保留注解
-keepattributes *Annotation*
# （Basic）排除警告
-dontwarn android.support.**
# Understand the @Keep support annotation.
# （Basic）不混淆指定的类及其类成员
-keep class android.support.annotation.Keep
# （Basic）不混淆使用注解的类及其类成员
-keep @android.support.annotation.Keep class * {*;}
# （Basic）不混淆所有类及其类成员中的使用注解的方法
-keepclasseswithmembers class * {
    @android.support.annotation.Keep <methods>;
}
# （Basic）不混淆所有类及其类成员中的使用注解的字段
-keepclasseswithmembers class * {
    @android.support.annotation.Keep <fields>;
}
# 不混淆所有类及其类成员中的使用注解的初始化方法
-keepclasseswithmembers class * {
    @android.support.annotation.Keep <init>(...);
}
#保留源文件以及行号 方便查看具体的崩溃信息
-keepattributes SourceFile,LineNumberTable


#-libraryjars libs/AuthClient-qwt-v2.0.0.jar
#-libraryjars libs/DevinfoManager-2.0.1.jar
#-libraryjars libs/jsmcc_sso.jar
#-libraryjars libs/XiriFeedback.jar
#-libraryjars libs/XiriScene.jar


-keep class * implements android.os.IInterface {*;}


-dontwarn com.lxj.xpopup.widget.**
-keep class com.lxj.xpopup.widget.**{*;}


#只保留类中的成员，防止被混淆或移除
-keepclassmembers class com.yxws.tvwidget.bridge.*{*;}
#只保留类中的成员，防止被混淆或移除
-keep class com.yxws.tvwidget.bridge.*{*;}



-keep class com.chinamobile.**{*;}
-keep class android.app.**{*;}
-keep class com.chinamobile.impl.**{*;}
-keep class com.chinamobile.**{*;}
-keepattributes Signature




-keep class com.jsmcc.sso.**{*;}
-keepattributes Signature


-keep class com.iflytek.**{*;}
-keepattributes Signature



-keepclassmembers public class * extends android.widget.MediaController {
 public *;
}
-keep class com.yxws.msettopboxs.view.t9.*{*;}
-keep class com.yxws.msettopboxs.bean.*{*;}


#Glide
-keep public class * implements com.bumptech.glide.module.GlideModule
-keep class * extends com.bumptech.glide.module.AppGlideModule {
 <init>(...);
}
-keep public enum com.bumptech.glide.load.ImageHeaderParser$** {
  **[] $VALUES;
  public *;
}
-keep class com.bumptech.glide.load.data.ParcelFileDescriptorRewinder$InternalRewinder {
  *** rewind();
}

# for DexGuard only
-keepresourcexmlelements manifest/application/meta-data@value=GlideModule
