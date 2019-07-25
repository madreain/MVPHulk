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
⚠️注意：因为涉及到的第三方库比较多，dex的方法数量被限制在65535之内，这就是著名的64K(64*1024)事件，
需引入MultiDex来解决这个问题，创建好Application,记得在AndroidManifest.xml中修改application的name

记得修改application的style
```
    <style name="AppTheme" parent="Theme.AppCompat.Light.NoActionBar">
        <!-- Customize your theme here. -->
        <item name="colorPrimary">@color/colorPrimary</item>
        <item name="colorPrimaryDark">@color/colorPrimaryDark</item>
        <item name="colorAccent">@color/colorAccent</item>
        <item name="android:windowBackground">@color/windowBackground</item>
        <item name="colorControlNormal">@color/windowBackground</item>
        <item name="colorControlActivated">@color/colorAccent</item>
    </style>
```

HulkConfig配置项配置相关设置如下

```

private static Application application;//设置Application
private static String baseUrl;//服务地址
//这里可根据自身项目设置是统一返回状态的还是增删改查不同返回状态的值
//设置统一正常态返回值
private static String retSuccess;//returnCode 正常态的值
//设置增删改查不一样的正常态返回值
private static List<String> retSuccessList;
private static boolean logOpen;//日志开关
private static boolean changeBaseUrl;//切换环境
private static Retrofit retrofit;//Retrofit设置
private static List<Interceptor> okHttpInterceptors = new ArrayList<>();//oKHttp拦截器
private static List<IReturnCodeErrorInterceptor> retCodeInterceptors = new ArrayList<>();//接口返回值拦截器
private static List<IVersionDiffInterceptor> versionDiffInterceptors = new ArrayList<>();//服务端版本号和本地版本号不一致拦截器
private static long connectTimeout = 15;//连接超时时间：单位秒
private static long readTimeout = 30;//单位秒
private static long writeTimeout = 60;//单位秒
private static Drawable glidePlaceHolder;//默认图
private static Drawable glideHeaderPlaceHolder;//默认头像占位图

    
```


⚠️注意：Glide相关注意事项

Glide的引用，需创建ids.xml

```
<?xml version="1.0" encoding="utf-8"?>
<resources>
    <item name="glide_tag" type="id" />
</resources>
```

配置参考如下，配置这些前得先参考-[dagger2和mvp结合](#2.dagger2和mvp结合)-[配置相关的HulkConfig](#3.配置相关的HulkConfig)创建相关类、接口

⚠️注意：可跳过接下来的配置参考直接从-[dagger2和mvp结合](#2.dagger2和mvp结合)开始看


```

public class MVPHulkApplication extends HulkApplication {

    private static Appcomponent appcomponent;

    public static Appcomponent getAppcomponent() {
        return appcomponent;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        MultiDex.install(this);
    }

    @Override
    public void initHulkConfig() {
        //DaggerAppComponent的生成make project一下就行
        appcomponent = DaggerAppcomponent.builder().apiModule(new com.madreain.hulk.application.ApiModule()).build();
        //消息拦截器
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        //配置项
        HulkConfig.builder()
                .setApplication(this)
                //这里只需要选择设置一个
                .setRetSuccess(BuildConfig.CODE_SUCCESS)
//                .setRetSuccessList(BuildConfig.CODELIST_SUCCESS)
                .setBaseUrl(BuildConfig.BASE_URL)
                .setChangeBaseUrl(BuildConfig.OPEN_CHANGE)
                .setOpenLog(BuildConfig.OPEN_LOG)
                .addOkHttpInterceptor(new RequestHeaderInterceptor())//请求头拦截器
                .addOkHttpInterceptor(BuildConfig.OPEN_LOG, logging)//okhttp请求日志开关+消息拦截器.md
                .addRetCodeInterceptor(new SessionInterceptor())// returnCode非正常态拦截器
                .setGlidePlaceHolder(new ColorDrawable(Color.parseColor("#ffffff")))//默认占位图--颜色
                .setGlideHeaderPlaceHolder(getResources().getDrawable(R.mipmap.ic_launcher))//默认头像占位图
                .setRetrofit(appcomponent.getRetrofit())
                .build();
        appcomponent.inject(this);
        //application 上下文
        Utils.init(this);
        //Glide设置tag
        ViewTarget.setTagId(R.id.glide_tag);
        //log日子开关
        initLog();
        //ARouter
        initArouter();
    }

    private void initArouter() {
        //ARouter的初始化
        ARouter.init(this);
    }

    private void initLog() {
        //测试环境
        if (BuildConfig.OPEN_LOG) {
            ARouter.openLog();     // 打印日志
            ARouter.openDebug();   // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
        }
    }

    //static 代码段可以防止内存泄露
    static {
        //启用矢量图兼容
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        //设置全局的Header构建器
        SmartRefreshLayout.setDefaultRefreshHeaderCreator(new DefaultRefreshHeaderCreator() {
            @Override
            public RefreshHeader createRefreshHeader(Context context, RefreshLayout layout) {
                layout.setPrimaryColorsId(R.color.colorPrimary, android.R.color.white);//全局设置主题颜色
                return new ClassicsHeader(context);//.setTimeFormat(new DynamicTimeFormat("更新于 %s"));//指定为经典Header，默认是 贝塞尔雷达Header
            }
        });
        //设置全局的Footer构建器
        SmartRefreshLayout.setDefaultRefreshFooterCreator(new DefaultRefreshFooterCreator() {
            @Override
            public RefreshFooter createRefreshFooter(Context context, RefreshLayout layout) {
                //指定为经典Footer，默认是 BallPulseFooter
                return new ClassicsFooter(context).setDrawableSize(20);
            }
        });
    }

}


```

### 2.dagger2和mvp结合

app的build.gradle需引入相关dagger2库,步骤1中已配置

1.)BuilderModule的创建(所有的activity、fragment都要在这里进行注册)（⚠️注意：我在Demo里是放在了包名下面，我在项目开发中会使用到Template模版开发）

```

@Module
public abstract class BuilderModule {
    //所有的activity、fragment都要在这里进行注册
    
}

```

2.)Appcomponent的创建(Application)

```

@Singleton
@Component(modules = {AndroidSupportInjectionModule.class, ApiModule.class, BuilderModule.class})
public interface Appcomponent extends IAppComponent {

    //HulkUnionApplication是继承HulkApplication创建
    void inject(HulkUnionApplication mvpHulkApplication);

    Retrofit getRetrofit();

}

```

⚠️注意：DaggerAppComponent的生成make project一下就行

3.)以及注入初始化代码。 app级别的当然在application里面出初始化

```
public class HulkUnionApplication extends HulkApplication {

    private static Appcomponent appcomponent;

    public static Appcomponent getAppcomponent() {
        return appcomponent;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        MultiDex.install(this);
    }

    @Override
    public void initHulkConfig() {
        //DaggerAppComponent的生成make project一下就行
        appcomponent = DaggerAppcomponent.builder().apiModule(new com.madreain.hulk.application.ApiModule()).build();
    }
}
    
```


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

到此为止，继承HulkApplication的配置项就完成了，这里还设置了Glide、ARouter、SmartRefreshLayout，完整代码参考如下
```
public class HulkUnionApplication extends HulkApplication {

    private static Appcomponent appcomponent;

    public static Appcomponent getAppcomponent() {
        return appcomponent;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        MultiDex.install(this);
    }

    @Override
    public void initHulkConfig() {
        //DaggerAppComponent的生成make project一下就行
        appcomponent = DaggerAppcomponent.builder().apiModule(new com.madreain.hulk.application.ApiModule()).build();
        //消息拦截器
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        //配置项
        HulkConfig.builder()
                .setApplication(this)
                //这里只需要选择设置一个
                .setRetSuccess(BuildConfig.CODE_SUCCESS)
//                .setRetSuccessList(BuildConfig.CODELIST_SUCCESS)
                .setBaseUrl(BuildConfig.BASE_URL)
                .setChangeBaseUrl(BuildConfig.OPEN_CHANGE)
                .setOpenLog(BuildConfig.OPEN_LOG)
                .addOkHttpInterceptor(new RequestHeaderInterceptor())//请求头拦截器
                .addOkHttpInterceptor(BuildConfig.OPEN_LOG, logging)//okhttp请求日志开关+消息拦截器.md
                .addRetCodeInterceptor(new SessionInterceptor())// returnCode非正常态拦截器
                .setGlidePlaceHolder(new ColorDrawable(Color.parseColor("#ffffff")))//默认占位图--颜色
                .setGlideHeaderPlaceHolder(getResources().getDrawable(R.mipmap.ic_launcher))//默认头像占位图
                .setRetrofit(appcomponent.getRetrofit())
                .build();
        appcomponent.inject(this);
        //application 上下文
        Utils.init(this);
        //Glide设置tag
        ViewTarget.setTagId(R.id.glide_tag);
        //log日子开关
        initLog();
        //ARouter
        initArouter();
    }

    private void initArouter() {
        //ARouter的初始化
        ARouter.init(this);
    }

    private void initLog() {
        //测试环境
        if (BuildConfig.OPEN_LOG) {
            ARouter.openLog();     // 打印日志
            ARouter.openDebug();   // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
        }
    }

    //static 代码段可以防止内存泄露
    static {
        //启用矢量图兼容
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        //设置全局的Header构建器
        SmartRefreshLayout.setDefaultRefreshHeaderCreator(new DefaultRefreshHeaderCreator() {
            @Override
            public RefreshHeader createRefreshHeader(Context context, RefreshLayout layout) {
                layout.setPrimaryColorsId(R.color.colorPrimary, android.R.color.white);//全局设置主题颜色
                return new ClassicsHeader(context);//.setTimeFormat(new DynamicTimeFormat("更新于 %s"));//指定为经典Header，默认是 贝塞尔雷达Header
            }
        });
        //设置全局的Footer构建器
        SmartRefreshLayout.setDefaultRefreshFooterCreator(new DefaultRefreshFooterCreator() {
            @Override
            public RefreshFooter createRefreshFooter(Context context, RefreshLayout layout) {
                //指定为经典Footer，默认是 BallPulseFooter
                return new ClassicsFooter(context).setDrawableSize(20);
            }
        });
    }
}
```

### 4.ApiService接口创建（Demo中是放在包名下的module/api下，因为会结合Template去生成代码）

```
public interface ApiService {

    @GET("api/address/list")
    Flowable<BaseRes<List<CityListListData>>> getCityList();

}
```

### 5.ARouterUri类创建，ARouter的路径存放（Demo中是放在包名下的consts下，因为会结合Template去生成代码）,ARouterKey用于数据传递的key

```
public class ARouterUri {

    public static final String MainActivity = "/mvphulk/ui/MainActivity";

}

```

接下来，我们要真正的进入业务开发阶段了

### 6.利用HulkTemplate生成对应单Activity、单Fragment、ListActivity、ListFragment
[MVPHulkTemplate使用指南](https://github.com/madreain/MVPHulkTemplate)

### 7.第6步生成的记得在BuilderModule进行注册
⚠️注意：Template模版会直接写入进去，可省略这步

```
@Module
public abstract class BuilderModule {

    @ContributesAndroidInjector(modules = MainModule.class)
    abstract MainActivity mainActivity();

    @ContributesAndroidInjector(modules = HomeModule.class)
    abstract HomeFragment homeFragment();

}
```

### 8.ARouter进行路由跳转、及其参数传递

参考官方文档[ARouter使用指南](https://github.com/alibaba/ARouter/blob/master/README_CN.md)

Activity需设置路由
```
@Route(path = ARouterUri.CityListActivity)
```

### 9.请求接口

1.)添加请求接口相关权限
```
    <uses-permission android:name="android.permission.INTERNET" />
    <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
```

2.)补充对象的相关参数

参考如下，这里需注意@Keep的作用，就是保持哪些类或者哪些元素不被混淆
```
@Keep
public class CityListListData {

    private String code;
    private String name;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

```


3.)ApiService配置相关接口

参考如下
```
public interface ApiService {

    @GET("api/address/list")
    Flowable<BaseRes<List<CityListListData>>> getCityList();
    
}
```

4.)继承自BaseModel的对应Model增加接口调用的方法

参考如下
```
public class CityListModel extends BaseModel<ApiService> implements CityListContract.Model {

    @Inject
    public CityListModel() {
        super();
    }

    @Override
    public Flowable<BaseRes<List<CityListListData>>> loadListDatas(int pageNo) {
        return apiService.getCityList();//接口调用 apiService.xxx
    }
}
```

5.)继承自BasePresenter的对应Presenter放开相对应的方法

参考如下
```
public class CityListPresenter extends BasePresenter<CityListModel, CityListContract.View> {

    @Inject
    CityListPresenter() {

    }

    @Override
    public void onStart() {
        loadPageListData(1);
    }

    @Override
    public void onDestroy() {
        model.onDestroy();
    }

    public void loadPageListData(final int pageNum) {
        model.loadListDatas(pageNum)
                .compose(Transformer.retrofit(view))
                .subscribeWith(new RSubscriberList<List<CityListListData>>(view, pageNum) {
                    @Override
                    public void _onNext(List<CityListListData> datas) {
                        view.showListData(datas, pageNum);
                    }

                    @Override
                    public void retry() {
                        loadPageListData(pageNum);
                    }

                });
    }
}
```

6.)继承自BaseAdapter的Adapter设置相关数据展示

参考如下
```

public class CityListAdapter<V extends IView> extends BaseAdapter<CityListListData, CityListActivity> {

    @Inject
    public CityListAdapter() {
        super(R.layout.item_activity_city_list, new ArrayList<>());
    }

    @Override
    protected void convert(HulkViewHolder helper, CityListListData data) {
        helper.setText(R.id.tv, data.getName());
    }
}


```


7.)继承自BaseListActivity的Activity类的设置

设置是否可刷新加载，默认都可以
```
    setLoadMoreEnable(true);
    setRefreshEnable(true);
```

Activity类完整代码参考如下
```
@Route(path = ARouterUri.CityListActivity)
public class CityListActivity extends BaseListActivity<CityListPresenter, CityListAdapter<CityListActivity>, CityListListData> implements CityListContract.View {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.smartRefreshLayout)
    SmartRefreshLayout smartRefreshLayout;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @Override
    public int getLayoutId() {
        return R.layout.activity_city_list;
    }


    @Override
    public void _init(Bundle savedInstanceState) {
        setSupportActionBarWithBack(toolbar);
        toolbar.setTitle("list展示能刷新能加载更多");
        presenter.onStart();
    }

    @Override
    public void loadPageListData(int pageNo) {
        presenter.loadPageListData(pageNo);
    }

    @Override
    public SmartRefreshLayout getSmartRefreshLayout() {
        return smartRefreshLayout;
    }


    @Override
    public RecyclerView getRecyclerView() {
        return recyclerView;
    }

    @Override
    public RecyclerView.LayoutManager getLayoutManager() {
        return new LinearLayoutManager(this);
    }

    @Override
    public View getReplaceView() {
        return smartRefreshLayout;
    }

}

```

接口调用到数据显示的代码参考[CityList城市](https://github.com/madreain/MVPHulk/tree/master/app/src/main/java/com/madreain/mvphulk/module/CityList)

到此为止，程序可以跑起来，想展示的界面也能展示出来了，接下来介绍一下可修改的自定义相关项

### 10.继承IVaryViewHelperController，实现自定义View的替换

参考[MyVaryViewHelperController](https://github.com/madreain/MVPHulk/blob/master/app/src/main/java/com/madreain/mvphulk/view/MyVaryViewHelperController.java)
```
public class MyVaryViewHelperController implements IVaryViewHelperController {

    private VaryViewHelper helper;

    //是否已经调用过restore方法
    private boolean hasRestore;

    public boolean isHasRestore() {
        return hasRestore;
    }

    public MyVaryViewHelperController(View view) {
        this(new VaryViewHelper(view));
    }

    private MyVaryViewHelperController(VaryViewHelper helper) {
        super();
        this.helper = helper;
    }

    @Override
    public void showNetworkError(View.OnClickListener onClickListener) {
        showNetworkError("网络状态异常，请刷新重试", onClickListener);
    }

    @Override
    public void showNetworkError(String msg, View.OnClickListener onClickListener) {
        hasRestore = false;
        View layout = helper.inflate(R.layout.view_page_error);
        HulkStatusButton againBtn = layout.findViewById(R.id.pager_error_loadingAgain);
        TextView tv_title = layout.findViewById(R.id.tv_title);
        tv_title.setVisibility(View.GONE);
        TextView tv_msg = layout.findViewById(R.id.tv_msg);
        tv_msg.setText(msg);
        if (null != onClickListener) {
            againBtn.setOnClickListener(onClickListener);
        }
        helper.showView(layout);
    }

    @Override
    public void showCustomView(int drawableInt, String title, String msg, String btnText, View.OnClickListener listener) {
        hasRestore = false;
        View layout = helper.inflate(R.layout.view_page_error);
        ImageView iv_flag = layout.findViewById(R.id.iv_flag);
        TextView tv_title = layout.findViewById(R.id.tv_title);
        TextView tv_msg = layout.findViewById(R.id.tv_msg);
        HulkStatusButton againBtn = layout.findViewById(R.id.pager_error_loadingAgain);

        iv_flag.setImageResource(drawableInt);
        if (TextUtils.isEmpty(title)) {
            tv_title.setVisibility(View.GONE);
        } else {
            tv_title.setVisibility(View.VISIBLE);
            tv_title.setText(title);
        }

        if (TextUtils.isEmpty(msg)) {
            tv_msg.setVisibility(View.GONE);
        } else {
            tv_msg.setVisibility(View.VISIBLE);
            tv_msg.setText(msg);
        }

        if (TextUtils.isEmpty(btnText)) {
            againBtn.setVisibility(View.GONE);
        } else {
            againBtn.setText(btnText);
            if (null != listener) {
                againBtn.setOnClickListener(listener);
            }
        }
        helper.showView(layout);
    }

    @Override
    public void showEmpty(String emptyMsg) {
        hasRestore = false;
        View layout = helper.inflate(R.layout.view_page_no_data);
        TextView textView = layout.findViewById(R.id.tv_no_data);
        if (!TextUtils.isEmpty(emptyMsg)) {
            textView.setText(emptyMsg);
        }
        helper.showView(layout);
    }

    @Override
    public void showEmpty(String emptyMsg, View.OnClickListener onClickListener) {
        hasRestore = false;
        View layout = helper.inflate(R.layout.view_page_no_data_click);
        HulkStatusButton againBtn = layout.findViewById(R.id.pager_error_loadingAgain);
        TextView textView = layout.findViewById(R.id.tv_no_data);
        if (!TextUtils.isEmpty(emptyMsg)) {
            textView.setText(emptyMsg);
        }
        if (null != onClickListener) {
            againBtn.setOnClickListener(onClickListener);
//            againBtn.setVisibility(View.VISIBLE);
            againBtn.setVisibility(View.GONE);//按钮都隐藏，空页面没有刷新 2018.9.5
        } else {
            againBtn.setVisibility(View.GONE);
        }
        helper.showView(layout);
    }

    @Override
    public void showLoading() {
        hasRestore = false;
        View layout = helper.inflate(R.layout.view_page_loading);
        helper.showView(layout);
    }

    @Override
    public void showLoading(String msg) {
        hasRestore = false;
        View layout = helper.inflate(R.layout.view_page_loading);
        TextView tv_msg = layout.findViewById(R.id.tv_msg);
        tv_msg.setText(msg);
        helper.showView(layout);
    }

    public void restore() {
        hasRestore = true;
        helper.restoreView();
    }

}

```

### 11.接口调用时，BRSubscriberList、RSubscriber对应三种接口交互方式
     NULL（无交互）
     TOAST（接口开始showDialogProgress()---->>接口结束 dismissDialog() 错误Toast）
     REPLACE（接口开始showLoading()---->>接口结束 :成功：restore(),失败：showError(); 失败、无数据情况会对应相应的ui展示）
        

