# MVPHulk Android MVP 快速集成方案

---
> MVPHulk是RxJava+Rxlifecycle+Okhttp+Retrofit+Dagger2+MVP框架，结合BaseRecyclerViewAdapterHelper和SmartRefreshLayout封装可刷新加载的Base类并结合接口回掉做了三种接口交互方式，
Butterknife用于View注入，EventBus用于通信，ARouter用于组件化路由跳转。


## 使用第三方库

1. [`RxJava` JVM响应式扩展Reactive Extensions 用于使用Java VM的可观察序列编写异步和基于事件的程序的库](https://github.com/ReactiveX/RxJava)
2. [`RxAndroid` 用于Android的RxJava绑定](https://github.com/ReactiveX/RxAndroid)
3. [`Rxlifecycle` 使用RxJava的Android应用程序的生命周期处理API](https://github.com/trello/RxLifecycle)
4. [`RxPermissions` RxJava支持的Android运行时权限](https://github.com/tbruyelle/RxPermissions)
5. [`Okhttp` 一个用于Android、Kotlin和Java的HTTP客户端](https://github.com/square/okhttp)
6. [`Retrofit` 为Android和Java提供安全的HTTP客户端](https://github.com/square/retrofit)
7. [`Glide` 面向Android的图像加载和缓存库](https://github.com/bumptech/glide)
8. [`Gson` 一个Java序列化/反序列化库，用于将Java对象转换为JSON并返回](https://github.com/google/gson)
9. [`Dagger2` Android和Java的快速依赖注入器](https://github.com/google/dagger)
10. [`Butterknife` View注入框架](https://github.com/JakeWharton/butterknife)
11. [`EventBus` Android和Java的事件总线，简化了活动、片段、线程、服务等之间的通信。代码越少，质量越好](https://github.com/greenrobot/EventBus)
12. [`ARouter` 帮助 Android App 进行组件化改造的路由框架](https://github.com/alibaba/ARouter)
13. [`BaseRecyclerViewAdapterHelper` 功能强大、灵活的万能适配器](https://github.com/CymChad/BaseRecyclerViewAdapterHelper)
14. [`SmartRefreshLayout` Android智能下拉刷新框架](https://github.com/scwang90/SmartRefreshLayout)
15. [`topsnackbar` Top Snackbar](https://github.com/AndreiD/TSnackBar)

## 项目配置介绍

### 方法一：直接引入源码，在app的build.gradle

```
api project(':hulk')

//dagger2
annotationProcessor rootProject.ext.dependencies["dagger2-compiler"]
annotationProcessor rootProject.ext.dependencies["dagger2-android-processor"]
//butterknife
api rootProject.ext.dependencies["butterknife"]
annotationProcessor rootProject.ext.dependencies["butterknife-compiler"]
//arouter
annotationProcessor rootProject.ext.dependencies["arouter-compiler"]
```

JAVA8的支持

```
compileOptions {
        sourceCompatibility JavaVersion.VERSION_1_8
        targetCompatibility JavaVersion.VERSION_1_8
    }
```

### 方法二：设置依赖项
1.项目的build.gradle

```
allprojects {
    repositories {
        google()
        jcenter()
    }
}
```

2.app的build.gradle

在android下添加

```
compileOptions {
        sourceCompatibility JavaVersion.VERSION_1_8
        targetCompatibility JavaVersion.VERSION_1_8
   }
```


```
api 'com.madreain:hulk:0.0.1'

//dagger2
annotationProcessor rootProject.ext.dependencies["dagger2-compiler"]
annotationProcessor rootProject.ext.dependencies["dagger2-android-processor"]
//butterknife
api rootProject.ext.dependencies["butterknife"]
annotationProcessor rootProject.ext.dependencies["butterknife-compiler"]
//arouter
annotationProcessor rootProject.ext.dependencies["arouter-compiler"]
```

## 项目入门介绍
### 1.配置Application,继承HulkApplication
⚠️注意：因为涉及到的第三方库比较多，dex的方法数量被限制在65535之内，这就是著名的64K(64*1024)事件，需引入MultiDex来解决这个问题

### 2.dagger2和mvp结合（app的build.gradle需引入相关dagger2库）
1.)BuilderModule的创建(所有的activity、fragment都要在这里进行注册)（⚠️注意：我在Demo里是放在了包名下面，我在项目开发中会使用到Template模版开发）
2.)Appcomponent的创建(Application)
3.)以及注入初始化代码。 app级别的当然在application里面出初始化

### 3.配置相关的HulkConfig

1.)在config.gradle中配置开启日志、开启切换环境、BASEURL的相关参数
##### config.gradle的相关参数

```
OPEN_LOG = true
OPEN_CHANGE = true
BASE_URL = " http://www.mxnzp.com"
CODE_SUCCESS = "1"
//增删改查返回网络请求成功，因为不支持集合，这里采用,分割
CODELIST_SUCCESS = "1,0"
```

##### app的build.gradle中定义这三个参数,android下的defaultConfig


```
//定义网络请求成功返回码 baseurl  日志打印  切换环境  在代码中BuildConfig.BASE_URL去使用
buildConfigField "String", "CODE_SUCCESS", getCodeSuccess()
buildConfigField "String", "CODELIST_SUCCESS", getCodeListSuccess()
buildConfigField "String", "BASE_URL", getBaseUrl()
buildConfigField "boolean", "OPEN_LOG", getOpenLog()
buildConfigField "boolean", "OPEN_CHANGE", getOpenChange()
```

##### android同级上创建上面的相关三个方法

```
def getOpenLog() {
    return "${OPEN_LOG}"
}

def getOpenChange() {
    return "${OPEN_CHANGE}"
}

def getBaseUrl() {
    return "\"" + String.valueOf(BASE_URL).trim() + "/\""
}

def getCodeSuccess() {
    return "\"" + String.valueOf(CODE_SUCCESS)+ "\""
}

def getCodeListSuccess() {
    return "\"" + String.valueOf(CODELIST_SUCCESS)+ "\""
}
```

##### 设置完成后需rebuild或者clean一下

2.)网络请求的相关拦截器
##### 请求头拦截

```
public class RequestHeaderInterceptor implements Interceptor {

    //统一请求头的封装根据自身项目添加
    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        Request request = chain.request();
        Request authorised;
        Headers headers = new Headers.Builder()
                .add("test", "test")
                .build();
        authorised = request.newBuilder().headers(headers).build();
        return chain.proceed(authorised);
    }
}
```

##### 非正常态拦截（互踢的场景）

```
public class SessionInterceptor implements IReturnCodeErrorInterceptor {

    //和接口定义互踢的相关参数返回，然后在todo方法进行跳转

    @Override
    public boolean intercept(String code) {
        //这里的-100表示互踢的返回参数
        return "-100".equals(code);
    }

    @Override
    public void todo(IView iView, String returnCode, String msg) {

    }

}
```

##### 消息拦截器

```
HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
logging.setLevel(HttpLoggingInterceptor.Level.BODY);
```

3.)默认占位图、默认头像占位图

### 4.ApiService接口创建（Demo中是放在包名下的module/api下，因为会结合Template去生成代码）

### 5.ARouterUri类创建，ARouter的路径存放（Demo中是放在包名下的consts下，因为会结合Template去生成代码）,ARouterKey用于数据传递的key

### 6.利用HulkTemplate生成对应单Activity、单Fragment、ListActivity、ListFragment
[MVPHulkTemplate](https://github.com/madreain/MVPHulkTemplate)

### 7.第6步生成的记得在BuilderModule进行注册
⚠️注意：Template模版会直接写入进去，可省略这步

### 8.请求接口记得先添加权限

```
    <uses-permission android:name="android.permission.INTERNET" />
    <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
```

### 9.MVPHulkTemplate使用，请在包名下使用创建

### 10.继承IVaryViewHelperController，实现自定义View的替换

### 11.接口调用时，BRSubscriberList、RSubscriber对应三种接口交互方式
     NULL（无交互）
     TOAST（接口开始showDialogProgress()---->>接口结束 dismissDialog() 错误Toast）
     REPLACE（接口开始showLoading()---->>接口结束 :成功：restore(),失败：showError(); 失败、无数据情况会对应相应的ui展示）
        

