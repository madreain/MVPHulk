MVPHulk使用指南.md

项目配置介绍
方法一：直接引入源码，在app的build.gradle

api project(':hulk')

JAVA8的支持
compileOptions {
        sourceCompatibility JavaVersion.VERSION_1_8
        targetCompatibility JavaVersion.VERSION_1_8
    }

方法二：
1.项目的build.gradle
allprojects {
    repositories {
        google()
        jcenter()
        maven {url "https://dl.bintray.com/wujun86/Madreain"}
    }
}

2.app的build.gradle

在android下添加

compileOptions {
        sourceCompatibility JavaVersion.VERSION_1_8
        targetCompatibility JavaVersion.VERSION_1_8
   }

implementation 'com.madreain:mvphulk:0.0.1'


项目入门介绍
# 1.配置Application,继承HulkApplication
⚠️注意：因为涉及到的第三方库比较多，dex的方法数量被限制在65535之内，这就是著名的64K(64*1024)事件，需引入MultiDex来解决这个问题

# 2.dagger2和mvp结合（app的build.gradle需引入相关dagger2库）
1.)BuilderModule的创建(所有的activity、fragment都要在这里进行注册)（⚠️注意：我在Demo里是放在了包名下面，我在项目开发中会使用到Template模版开发）
2.)Appcomponent的创建(Application)
3.)以及注入初始化代码。 app级别的当然在application里面出初始化

# 3.配置相关的HulkConfig

1.)在config.gradle中配置开启日志、开启切换环境、BASEURL的相关参数
###### config.gradle的相关参数

OPEN_LOG = true
OPEN_CHANGE = true
BASE_URL = " http://www.mxnzp.com"
CODE_SUCCESS = "1"

###### app的build.gradle中定义这三个参数,android下的defaultConfig

//定义网络请求成功返回码 baseurl  日志打印  切换环境  在代码中BuildConfig.BASE_URL去使用
buildConfigField "String", "CODE_SUCCESS", getCodeSuccess()
buildConfigField "String", "BASE_URL", getBaseUrl()
buildConfigField "boolean", "OPEN_LOG", getOpenLog()
buildConfigField "boolean", "OPEN_CHANGE", getOpenChange()

###### android同级上创建上面的相关三个方法

def getOpenLog() {
    return "${OPEN_LOG}"
}

def getOpenChange() {
    return "${OPEN_CHANGE}"
}

//根据判断是否使用来自jenkins的Url还是本地的baseUrl
def getBaseUrl() {
    //来自jenkins的环境地质，最后没有斜杠，需要手动拼接
    return "\"" + String.valueOf(BASE_URL).trim() + "/\""
}

def getCodeSuccess() {
    return "\"" + String.valueOf(BASE_URL).trim() + "/\""
}

###### 设置完成后需rebuild或者clean一下

2.)网络请求的相关拦截器
###### 请求头拦截

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

###### 非正常态拦截（互踢的场景）

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

###### 消息拦截器

HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
logging.setLevel(HttpLoggingInterceptor.Level.BODY);

3.)默认占位图、默认头像占位图

# 4.ApiService接口创建（Demo中是放在包名下的module/api下，因为会结合Template去生成代码）

# 5.RouterUri类创建，ARouter的路径存放（Demo中是放在包名下的consts下，因为会结合Template去生成代码）

# 6.利用HulkTemplate生成对应单Activity、单Fragment、ListActivity、ListFragment
[MVPHulkTemplate](https://github.com/madreain/MVPHulkTemplate)

# 7.生成的BuilderModule记得在进行注册
⚠️注意：Template模版会直接写入进去，可省略这步

# 8.请求接口记得先添加权限
    <uses-permission android:name="android.permission.INTERNET" />
    <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />

# 9.MVPHulkTemplate使用，请在包名下使用创建

# 10.继承IVaryViewHelperController，实现自定义View的替换

# 11.BaseListActivity\BaseListFragment对应请求接口的RSubscriberList
     BaseActivity\BaseFragment对应请求接口的RSubscriber

