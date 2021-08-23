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

-keepattributes *Annotation*
-keepclassmembers class * {
    @org.greenrobot.eventbus.Subscribe <methods>;
}
-keep enum org.greenrobot.eventbus.ThreadMode { *; }

# Only required if you use AsyncExecutor
-keepclassmembers class * extends org.greenrobot.eventbus.util.ThrowableFailureEvent {
    <init>(java.lang.Throwable);
}
##---------------Begin: proguard configuration for Gson  ----------
# Gson uses generic type information stored in a class file when working with fields. Proguard
# removes such information by default, so configure it to keep all of it.
-keepattributes Signature

# For using GSON @Expose annotation
-keepattributes *Annotation*

# Gson specific classes
#noinspection ShrinkerUnresolvedReference
-keep class sun.misc.Unsafe { *; }
-keep class com.google.gson.stream.** { *; }

# Explicitly preserve all serialization members. The Serializable interface
# is only a marker interface, so it wouldn't save them.
-keepclassmembers class * implements java.io.Serializable {
    static final long serialVersionUID;
    private static final java.io.ObjectStreamField[] serialPersistentFields;
    private void writeObject(java.io.ObjectOutputStream);
    private void readObject(java.io.ObjectInputStream);
    java.lang.Object writeReplace();
    java.lang.Object readResolve();
}

-keep public class * implements java.io.Serializable {*;}
##---------------End: proguard configuration for Gson  ----------

-optimizationpasses 5                                                           # 指定代码的压缩级别
-dontusemixedcaseclassnames                                                     # 是否使用大小写混合
#-dontskipnonpubliclibraryclasses                                                # 是否混淆第三方jar
-dontpreverify                                                                  # 混淆时是否做预校验
-verbose                                                                        # 混淆时是否记录日志
-optimizations !code/simplification/arithmetic,!field/*,!class/merging/*        # 混淆时所采用的算法

-keep public class * extends android.app.Activity                               # 保持哪些类不被混淆
-keep public class * extends android.app.Application                            # 保持哪些类不被混淆
-keep public class * extends android.app.Service                                # 保持哪些类不被混淆
-keep public class * extends android.content.BroadcastReceiver                  # 保持哪些类不被混淆
-keep public class * extends android.content.ContentProvider                    # 保持哪些类不被混淆
-keep public class * extends android.app.backup.BackupAgentHelper               # 保持哪些类不被混淆
-keep public class * extends android.preference.Preference                      # 保持哪些类不被混淆
#noinspection ShrinkerUnresolvedReference
-keep public class com.android.vending.licensing.ILicensingService              # 保持哪些类不被混淆

-keepclasseswithmembernames class * {                                           # 保持 native 方法不被混淆
    native <methods>;
}

-keepclasseswithmembers class * {                                               # 保持自定义控件类不被混淆
    public <init>(android.content.Context, android.util.AttributeSet);
}

-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet, int);     # 保持自定义控件类不被混淆
}

-keepclassmembers class * extends android.app.Activity {                        # 保持自定义控件类不被混淆
   public void *(android.view.View);
}

-keepclassmembers enum * {                                                      # 保持枚举 enum 类不被混淆
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

-keep class * implements android.os.Parcelable {                                # 保持 Parcelable 不被混淆
  #noinspection ShrinkerUnresolvedReference
  public static final android.os.Parcelable$Creator *;
}

#-keep class MyClass;                                                            # 保持自己定义的类不被混淆
#如果有引用v4包可以添加下面这行
-keep class android.support.v4.** { *; }
-keep public class * extends android.support.v4.**
-keep public class * extends android.app.Fragment

#如果引用了v4或者v7包，可以忽略警告，因为用不到android.support
-dontwarn android.support.**

#保持自定义组件不被混淆
-keep public class * extends android.view.View {
    public <init>(android.content.Context);
    public <init>(android.content.Context, android.util.AttributeSet);
    public <init>(android.content.Context, android.util.AttributeSet, int);
    public void set*(...);
}

#保持 Serializable 不被混淆
-keepnames class * implements java.io.Serializable

#保持 Serializable 不被混淆并且enum 类也不被混淆
-keepclassmembers class * implements java.io.Serializable {
    static final long serialVersionUID;
    private static final java.io.ObjectStreamField[] serialPersistentFields;
    private void writeObject(java.io.ObjectOutputStream);
    private void readObject(java.io.ObjectInputStream);
    java.lang.Object writeReplace();
    java.lang.Object readResolve();
}

-keepclassmembers class * {
    public void *ButtonClicked(android.view.View);
}

#不混淆资源类
-keepclassmembers class **.R$* {
    public static <fields>;
}

 -keep class me.jessyan.autosize.** { *; }
 -keep interface me.jessyan.autosize.** { *; }

 ###-------- Gson 相关的混淆配置--------
 -keepattributes Signature
 -keepattributes *Annotation*
 -keep class sun.misc.Unsafe { *; }
 ###-------- ShareSDK 相关的混淆配置---------
 -keep class cn.sharesdk.** { *; }
 -keep class com.sina.sso.** { *; }
 ###--------------umeng 相关的混淆配置-----------
 -keep class com.umeng.** { *; }
 -keep class com.umeng.analytics.** { *; }
 -keep class com.umeng.common.** { *; }
 -keep class com.umeng.newxp.** { *; }
 #EventBus
 -keepclassmembers class ** {
     public void onEvent*(**);
 }
 -dontwarn com.google.gson.**
 -dontwarn okio.*
 -dontwarn Android.support.**
 -dontwarn android.support.design.**
 -keep class android.support.design.** { *; }
 -keep interface android.support.design.** { *; }
 -keep public class android.support.design.R$* { *; }
 -keep public class android.support.v7.widget.** { *; }
 -keep public class android.support.v7.internal.widget.** { *; }
 -keep public class android.support.v7.internal.view.menu.** { *; }
 -keep public class * extends android.support.v4.view.ActionProvider {
     public <init>(android.content.Context);
 }
 # ButterKnife 7
 -keep class butterknife.** { *; }
 -dontwarn butterknife.internal.**
 -keep class **$$ViewBinder { *; }
 -keepclasseswithmembernames class * {
     @butterknife.* <fields>;
 }
 -keepclasseswithmembernames class * {
     @butterknife.* <methods>;
 }
 ## New rules for EventBus 3.0.x ##
 # http://greenrobot.org/eventbus/documentation/proguard/
 -keepattributes *Annotation*
 -keepclassmembers class ** {
     @org.greenrobot.eventbus.Subscribe <methods>;
 }
 -keep enum org.greenrobot.eventbus.ThreadMode { *; }
 # Only required if you use AsyncExecutor
 -keepclassmembers class * extends org.greenrobot.eventbus.util.ThrowableFailureEvent {
     <init>(java.lang.Throwable);
 }
 # Glide specific rules #
 # https://github.com/bumptech/glide
 -keep public class * implements com.bumptech.glide.module.GlideModule
 -keep public enum com.bumptech.glide.load.resource.bitmap.ImageHeaderParser$** {
     **[] $VALUES;
     public *;
 }
 #---------------------------------2.第三方库---------------------------------
 #okhttp3
 -dontwarn com.squareup.okhttp3.**
 -keep class com.squareup.okhttp3.** { *;}
 -keep class okhttp3.** { *;}
 -keep class okio.** { *;}
 -dontwarn sun.security.**
 -keep class sun.security.** { *;}
 -dontwarn okio.**
 -dontwarn okhttp3.**

 #retrofit2
 -dontwarn retrofit2.**
 -keep class retrofit2.** { *; }
 -keepattributes Signature
 -keepattributes Exceptions
 -dontwarn org.robovm.**
 -keep class org.robovm.** { *; }
 # RxJava RxAndroid
 -dontwarn sun.misc.**
 -keepclassmembers class rx.internal.util.unsafe.*ArrayQueue*Field* {
     long producerIndex;
     long consumerIndex;
 }
 -keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueProducerNodeRef {
     #noinspection ShrinkerUnresolvedReference
     rx.internal.util.atomic.LinkedQueueNode producerNode;
 }
 -keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueConsumerNodeRef {
     #noinspection ShrinkerUnresolvedReference
     rx.internal.util.atomic.LinkedQueueNode consumerNode;
 }
 -dontnote rx.internal.util.PlatformDependent
 # Retrofit, OkHttp, Gson
 -keep class com.squareup.okhttp.** { *; }
 -keep interface com.squareup.okhttp.** { *; }
 -dontwarn com.squareup.okhttp.**
 -dontwarn rx.**
 -dontwarn retrofit.**
 -keep class retrofit.** { *; }
 -keepclasseswithmembers class * {
     @retrofit.http.* <methods>;
 }
 -keep class sun.misc.Unsafe { *; }
 -dontwarn java.nio.file.*
 -dontwarn org.codehaus.mojo.animal_sniffer.IgnoreJRERequirement
 # Rxjava-promises
 -keep class com.darylteo.rx.** { *; }
 -dontwarn com.darylteo.rx.**
 #极验
 -dontwarn com.geetest.onelogin.**
 -keep class com.geetest.onelogin.** {
 *;
 }
 -dontwarn com.cmic.sso.sdk.**
 -keep class com.cmic.sso.sdk.** {
 *;
 }
 -dontwarn com.unicom.xiaowo.login.**
 -keep class com.unicom.xiaowo.login.** {
 *;
 }
 -dontwarn cn.com.chinatelecom.account.api.**
 -keep class cn.com.chinatelecom.account.api.** {
 *;
 }
 #解决使用Retrofit+rxJava联网时，在6.0系统出现java.lang.InternalError奔溃的问题:http://blog.csdn.net/mp624183768/article/details/79242147
 -keepclassmembers class rx.internal.util.unsafe.*ArrayQueue*Field* {
     long producerIndex;
     long consumerIndex;
 }
 -keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueProducerNodeRef {
     #noinspection ShrinkerUnresolvedReference
     rx.internal.util.atomic.LinkedQueueNode producerNode;
 }
 -keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueConsumerNodeRef {
     #noinspection ShrinkerUnresolvedReference
     rx.internal.util.atomic.LinkedQueueNode consumerNode;
 }
 #kr\co\citex\citexclient
 -keep class kr.co.citex.citexclient.** { *; }
 # RxJava 0.21
 -keep class rx.schedulers.Schedulers {
     public static <methods>;
 }
 -keep class rx.schedulers.ImmediateScheduler {
     public <methods>;
 }
 -keep class rx.schedulers.TestScheduler {
     public <methods>;
 }
 -keep class rx.schedulers.Schedulers {
     public static ** test();
 }

 # RxLifeCycle2
 -keep class com.trello.rxlifecycle2.** { *; }
 -keep interface com.trello.rxlifecycle2.** { *; }
 -dontwarn com.trello.rxlifecycle2.**
 -keep class com.github.mikephil.charting.** { *; }
 -dontwarn com.github.mikephil.charting.data.realm.**
 #bugly
 -dontwarn com.tencent.bugly.**
 -keep public class com.tencent.bugly.**{*;}
 -keepclasseswithmembernames class * {
     @butterknife.* <fields>;
 }
 -keepclasseswithmembernames class * {
     @butterknife.* <methods>;
 }
 -dontwarn javax.annotation.**
 -dontwarn javax.inject.**
 #umeng
 -dontwarn com.umeng.**
 -keep class com.umeng.**{*;}
 -dontwarn com.tencent.mm.**
 -keep class com.tencent.mm.**{*;}
 -keep class com.smart.novel.net.** { *; }

 #start udesk================================
 -keep class udesk.** {*;}
 -keep class cn.udesk.**{*; }
 #JSONobject
 -keep class org.json.** {*; }
 #七牛
 -keep class okhttp3.** {*;}
 -keep class okio.** {*;}
 -keep class com.qiniu.**{*;}
 -keep class com.qiniu.**{public <init>();}
 -ignorewarnings
 #smack
 -keep class org.jxmpp.** {*;}
 -keep class de.measite.** {*;}
 -keep class org.jivesoftware.** {*;}
 -keep class org.xmlpull.** {*;}
 -dontwarn org.xbill.**
 -keep class org.xbill.** {*;}

 #eventbus
 -keepattributes *Annotation*
 -keepclassmembers class ** {
     @org.greenrobot.eventbus.Subscribe <methods>;
 }
 -keep enum org.greenrobot.eventbus.ThreadMode { *; }

 # Only required if you use AsyncExecutor
 -keepclassmembers class * extends org.greenrobot.eventbus.util.ThrowableFailureEvent {
     <init>(java.lang.Throwable);
 }

 #freso
 -keep class com.facebook.** {*; }
 -keep class com.facebook.imagepipeline.** {*; }
 -keep class com.facebook.animated.gif.** {*; }
 -keep class com.facebook.drawee.** {*; }
 -keep class com.facebook.drawee.backends.pipeline.** {*; }
 -keep class com.facebook.imagepipeline.** {*; }
 -keep class bolts.** {*; }
 -keep class me.relex.photodraweeview.** {*; }

 -keep,allowobfuscation interface com.facebook.common.internal.DoNotStrip
 -keep @com.facebook.common.internal.DoNotStrip class *
 -keepclassmembers class * {
     #noinspection ShrinkerUnresolvedReference
     @com.facebook.common.internal.DoNotStrip *;
 }
 # Keep native methods
 -keepclassmembers class * {
     native <methods>;
 }

 -dontwarn okio.**
 -dontwarn com.squareup.okhttp.**
 -dontwarn okhttp3.**
 -dontwarn javax.annotation.**
 -dontwarn com.android.volley.toolbox.**
 -dontwarn com.facebook.infer.**

 #bugly
 -keep class com.tencent.bugly.** {*; }

  #agora
 -keep class io.agora.**{*;}
 #end udesk================================

 #Google Pay
 -keep class com.android.vending.billing.**
 #end udesk================================

 #Appsflyer
 -keep class com.appsflyer.** { *; }
 #end udesk================================


 -keep class com.sensorsdata.analytics.android.** { *; }

 -keep public class com.alibaba.android.arouter.routes.**{*;}
 -keep public class com.alibaba.android.arouter.facade.**{*;}
 -keep class * implements com.alibaba.android.arouter.facade.template.ISyringe{*;}

 # 如果使用了 byType 的方式获取 Service，需添加下面规则，保护接口
 -keep interface * implements com.alibaba.android.arouter.facade.template.IProvider

 -dontwarn java.util.concurrent.Flow*

 -keep class com.huantansheng.easyphotos.models.** { *; }