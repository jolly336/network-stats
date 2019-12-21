
[TOC]

# network-stats
network-stats 是一个统计 HTTP 流量的 Android 库，目前只支持统计 okHttp 网络库，默认统计的流量数据支持外部设置。

## 使用

* 项目根目录 build.gradle 中增加 AspectJ 插件依赖

```groovy
buildscript {
    dependencies {
        classpath 'com.hujiang.aspectjx:gradle-android-plugin-aspectjx:2.0.8'
    }
}
```

* 主项目 build.gradle 中添加 AspectJ 和流量监控组件依赖

```groovy
apply plugin: 'android-aspectjx'
   
dependencies {
    implementation 'com.nelson.android:network-stats:1.0.0'
}
```

* 排除某些 package

```groovy
aspectjx {
    exclude 'com.nelson.crm.stats'
}
```

* 代码使用

以下 2 步就可以使用流量统计功能

```java

// 1.set user identity
NetworkStatsManager.setUser(new User(userId,userName));

// 2.get stats bytes
NetworkStatsManager.getTodayBytes(new LoadCallBack() {
    @Override
    public void onSuccess(long bytes) {
        // total bytes = request + response
    }
    
    @Override
    public void onFailure(int errorCode, String errorMsg) {
        // handle exception
    }
});

// 可选，set save data in days，default 2 days
NetworkStatsManager.setSaveDataInDays(days);

```

说明：

基本设置 build.gradle 如下：

```groovy
apply plugin: 'android-aspectjx'

android {
    ...
}
   
dependencies {
    implementation 'com.nelson.android:network-stats:1.0.0'
}

aspectjx {
    exclude 'com.nelson.stats'
}
```

## TODO

- [ ] 无法直接获取 App 使用移动网络和 WiFi 网络的流量统计，区分网络类型进行统计流量，需要开启后台服务，并监控网络状态变化广播，根据网络状态变化自己记录并计算出不同网络类型下的流量统计
- [ ] 提供的网络流量统计数据不仅限于包括移动网络和 WiFi 网络，也可能包括 App 间 socket 通信产生的流量；
- [ ] 适配其它网络库，目前只适配支持了应用程序 okHttp 网络库
- [ ] 支持获取所有所有或者指定 App 使用网络的流量和包的数量，目前只支持获取当前 App 所有网络的流量和包的数量，

## 参考

* [HujiangTechnology/gradle_plugin_android_aspectjx](https://github.com/HujiangTechnology/gradle_plugin_android_aspectjx)
* [AOP 之 AspectJ 全面剖析 in Android
](https://www.jianshu.com/p/f90e04bcb326)
* [SpringBootUnity/TimeUtil.java](https://github.com/houko/SpringBootUnity/blob/master/core/src/main/java/info/xiaomo/core/untils/TimeUtil.java)

---

## Change Log

### Version 1.0.0 *(2019-12-13)* lastest release
----------------------------

* New: 初始化 network-stats 流量统计组件