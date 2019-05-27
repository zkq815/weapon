## Add project specific ProGuard rules here.
## You can control the set of applied configuration files using the
## proguardFiles setting in build.gradle.
##
## For more details, see
##   http://developer.android.com/guide/developing/tools/proguard.html
#
## If your project uses WebView with JS, uncomment the following
## and specify the fully qualified class name to the JavaScript interface
## class:
##-keepclassmembers class fqcn.of.javascript.interface.for.webview {
##   public *;
##}
#
## Uncomment this to preserve the line number information for
## debugging stack traces.
##-keepattributes SourceFile,LineNumberTable
#
## If you keep the line number information, uncomment this to
## hide the original source file name.
##-renamesourcefileattribute SourceFile
##------------------------------基本设置-------------------------------
# 混淆时不使用大小写混合，混淆后的类名为小写
-dontusemixedcaseclassnames

# 指定不去忽略非公共的库的类
-dontskipnonpubliclibraryclasses

# 有了verbose这句话，混淆后就会生成映射文件
# 包含有类名->混淆后类名的映射关系
# 然后可以使用printmapping指定映射文件的名称
-verbose

# Optimization is turned off by default. Dex does not like code run
# through the ProGuard optimize and preverify steps (and performs some
# of these optimizations on its own).
# 不启用优化,建议使用此选项
# proguard的优化选项和java虚拟机中的字节码dex优化有冲突，可能会产生一些未知的问题
-dontoptimize

# 不做预校验，preverify是proguard的4个步骤之一
# Android不需要preverify，去掉这一步可加快混淆速度
-dontpreverify

# Note that if you want to enable optimization, you cannot just
# include optimization flags in your own project configuration file;
# instead you will need to point to the
# "proguard-android-optimize.txt" file instead of this one from your
# project.properties file.

# 保留注解，因为注解是通过反射机制来实现的
-keepattributes *Annotation*
# 保留google的授权服务
-keep public class com.google.vending.licensing.ILicensingService
-keep public class com.android.vending.licensing.ILicensingService

# 保留本地NDK方法
# For native methods, see http://proguard.sourceforge.net/manual/examples.html#native
-keepclasseswithmembernames class * {
    native <methods>;
}

# 保留View中的set和get方法
# keep setters in Views so that animations can still work.
# see http://proguard.sourceforge.net/manual/examples.html#beans
-keepclassmembers public class * extends android.view.View {
   void set*(***);
   *** get*();
}

# 保留Activity当中的View相关方法
# We want to keep methods in Activity that could be used in the XML attribute onClick
-keepclassmembers class * extends android.app.Activity {
   public void *(android.view.View);
}

-keepclassmembers class * extends com.zkq.weapon.base.BaseActivity {
   public void *(android.view.View);
}

# 保留枚举类
# For enumeration classes, see http://proguard.sourceforge.net/manual/examples.html#enumerations
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

# 保留序列号相关类即方法
-keepclassmembers class * implements android.os.Parcelable {
  public static final android.os.Parcelable$Creator CREATOR;
}

# 保留资源应用名
-keepclassmembers class **.R$* {
    public static <fields>;
}

# 忽略support包下的警告
-dontwarn android.support.**

# Understand the @Keep support annotation.
# 保留support包下的动画
-keep class android.support.annotation.Keep

-keep @android.support.annotation.Keep class * {*;}

-keepclasseswithmembers class * {
    @android.support.annotation.Keep <methods>;
}

-keepclasseswithmembers class * {
    @android.support.annotation.Keep <fields>;
}

-keepclasseswithmembers class * {
    @android.support.annotation.Keep <init>(...);
}

##---------------------------通用保留部分------------------------------------
-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.app.backup.BackupAgentHelper
-keep public class * extends android.preference.Preference
-keep public class * extends com.zkq.weapon.application.BaseApplication
-keep public class * extends com.zkq.weapon.base.BaseActivity
-keep public class * extends com.zkq.weapon.base.BaseActionBarActivity
-keep public class * extends com.zkq.weapon.base.BaseFragment
-keep public class * extends com.zkq.weapon.base.WebViewPluginActivity
-keep public class * extends com.zkq.weapon.base.BaseWebPluginFragment
-keep public class * extends com.zkq.weapon.base.WebViewPluginFragment
-keep public class * extends android.view.View
-keep public class com.android.vending.licensing.ILicensingService
-keep public class android.arch.** { *;}
#
-keep class android.support.** {*;}                                             # 保持support包

-keepclasseswithmembernames class * {
    native <methods>;
}
-keepclassmembers class * extends android.app.Activity{
    public void *(android.view.View);
}
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}
-keep public class * extends android.view.View{
    *** get*();
    void set*(***);
    public <init>(android.content.Context);
    public <init>(android.content.Context, android.util.AttributeSet);
    public <init>(android.content.Context, android.util.AttributeSet, int);
}
-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet);
    public <init>(android.content.Context, android.util.AttributeSet, int);
}
-keep class * implements android.os.Parcelable {
  public static final android.os.Parcelable$Creator *;
}
-keepclassmembers class * implements java.io.Serializable {
    static final long serialVersionUID;
    private static final java.io.ObjectStreamField[] serialPersistentFields;
    private void writeObject(java.io.ObjectOutputStream);
    private void readObject(java.io.ObjectInputStream);
    java.lang.Object writeReplace();
    java.lang.Object readResolve();
}

# R文件不混淆
-keep class **.R$* {
 *;
}
-keepclassmembers class * {
    void *(**On*Event);
}

#公共库类
-keep class com.zkq.weapon.** { *;}
-keep class com.zkq.weapon.entity.**{ *;}
-keep class com.zkq.weapon.market.glide.** { *;}
-dontwarn com.zkq.weapon.**
#控件不混淆
-keep class com.zkq.weapon.customview.** { *;}

#-libraryjars <java.home>/lib/rt.jar(java/**,javax/security/**,javax/activation/**)
#保持greenDao的方法不被混淆
-keep class org.greenrobot.dao.** {*;}
-keepclassmembers class * extends org.greenrobot.dao.AbstractDao {
   public static java.lang.String TABLENAME;
}
-keep class **$Properties
#-keep class org.sqlite.** { *; }
-keep class android.database.sqlite.**
-keep class com.db.models.**
-keepclassmembers class com.db.models.** { *; }

#MenuBuilder
-keep class * extends android.support.v7.internal.view.menu.MenuBuilder
-keep class * implements android.support.v7.internal.view.menu.MenuBuilder
-keep class android.support.v7.internal.view.menu.MenuBuilder

##---------------------------------webview-----------------------------------
-dontnote fqcn.of.**
-dontwarn fqcn.of.**
-dontnote android.webkit.**
-dontwarn android.webkit.**
-keepclassmembers class fqcn.of.javascript.interface.for.webview {
   public *;
}
-keepattributes *JavascriptInterface*
-keepclassmembers class * extends android.webkit.webViewClient {
    public void *(android.webkit.WebView, java.lang.String, android.graphics.Bitmap);
    public boolean *(android.webkit.WebView, java.lang.String);
    public void *(android.webkit.webView, java.lang.String);
}

-keepclassmembers class * {
    @android.webkit.JavascriptInterface <methods>;
}
##--------------------------------第三方--------------------------------------------

#alibaba fastjson
-keepattributes Signature
-dontwarn com.alibaba.fastjson.**
-dontnote com.alibaba.fastjson.**
-keep class com.alibaba.fastjson.** { *; }

#alibaba arouter
-keep class com.alibaba.android.arouter.**{ *;}
-dontnote com.alibaba.android.arouter.**
-dontwarn com.alibaba.android.arouter.**

#glide
-keep public class * implements com.bumptech.glide.module.GlideModule
-keep public class * extends com.bumptech.glide.module.AppGlideModule
-keep public enum com.bumptech.glide.load.resource.bitmap.ImageHeaderParser$** {
  **[] $VALUES;
  public *;
}
# glide升级4.7.1
-dontwarn com.bumptech.**

#squareup picasso、retrofit、
-keep class com.squareup.** { *;}
-dontwarn com.squareup.**

# OkHttp3
-keep class okhttp3.** { *; }
-keep class okhttp3.internal.**{*;}
-keep interface okhttp3.** { *; }
-dontwarn okio.**
-dontwarn okhttp3.logging.**
-dontwarn okhttp3.**

-keep class okio.** { *; }
#OkHttp3Downloader
-keep class com.jakewharton.**{ *;}
-dontwarn com.jakewharton.**

# Retrofit
-dontwarn retrofit2.**
-keep class retrofit2.** { *; }
-keepattributes Signature
-keepattributes Exceptions
# RxJava RxAndroid
-dontwarn sun.misc.**
-dontnote io.reactivex.**
-keepclassmembers class rx.internal.util.unsafe.*ArrayQueue*Field* {
    long producerIndex;
    long consumerIndex;
}
-keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueProducerNodeRef {
    rx.internal.util.atomic.LinkedQueueNode producerNode;
}
-keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueConsumerNodeRef {
    rx.internal.util.atomic.LinkedQueueNode consumerNode;
}

#picasso
-keep class com.parse.*{ *; }
-keep class com.squareup.okhttp.** { *; }
-keep interface com.squareup.okhttp.** { *; }
-dontwarn com.parse.**
-dontwarn com.squareup.picasso.**
-keepclasseswithmembernames class * {
    native <methods>;
}

# Gson
-keepattributes EnclosingMethod
-keep class sun.misc.** { *;}
-keep class com.google.gson.** { *;}
-keep class com.google.gson.stream.** { *;}
# Application classes that will be serialized/deserialized over Gson
-keep class com.google.gson.examples.android.model.** { *;}

#
-keep class com.sun.** { *;}
-dontwarn com.sun.**

#gif库
-keep class pl.droidsonroids.gif.** { *;}
-dontwarn pl.droidsonroids.**

#org.apache.commons
-keep class org.apache.** { *;}
-dontwarn org.apache.**