# MVPHulk Android MVP 快速集成方案

![Logo](https://github.com/madreain/MVPHulk/blob/master/file/MVPHulk.png)

---
> MVPHulk是一款采用当前最新技术RxJava+Rxlifecycle+Okhttp+Retrofit+Dagger封装的一个MVP框架

## 主要功能

1.支持接口多状态、单状态返回值配置；支持服务器地址配置；拦截器相关配置等
2.支持support、AndroidX两个版本
3.支持单Activity、列表Activity、单Fragment、列表Fragment、单DialogFragment，
结合BaseRecyclerViewAdapterHelper+SmartRefreshLayout的封装，让我们只需要关注业务逻辑的实现；对于复杂业务场景可根据基类进行扩展
4.支持接口状态不同显示不同状态界面和可执行操作，支持自定义状态界面，可集成(lottie)[https://github.com/airbnb/lottie-android]让界面更酷炫
5.支持模版开发，[MVPHulkTemplate](https://github.com/madreain/MVPHulkTemplate)让开发者减少重复的操作，解放双手

## 传送门

1.[MVPHulk上手文档](https://github.com/madreain/MVPHulk/blob/master/README.md)
2.[MVPHulkTemplate使用文档](https://github.com/madreain/MVPHulkTemplate/blob/master/README.md)
3.[MVPHulk源码下载](https://github.com/madreain/MVPHulk/releases)

## 简单用例

### 1.设置依赖项
1.项目的build.gradle

```
allprojects {
    repositories {
        maven { url "https://jitpack.io" }
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
//1.0.0版本
api 'com.madreain:hulk:1.0.0'
//androidx版本
api 'com.madreain:hulk:1.0.0-andx'

//dagger2
annotationProcessor rootProject.ext.dependencies["dagger2-compiler"]
annotationProcessor rootProject.ext.dependencies["dagger2-android-processor"]
//butterknife
api rootProject.ext.dependencies["butterknife"]
annotationProcessor rootProject.ext.dependencies["butterknife-compiler"]
//arouter
annotationProcessor rootProject.ext.dependencies["arouter-compiler"]
```

### 2.配置Application,继承HulkApplication
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

Glide的引用，需创建ids.xml

```
<?xml version="1.0" encoding="utf-8"?>
<resources>
    <item name="glide_tag" type="id" />
</resources>
```

### 3.dagger2和mvp结合

app的build.gradle需引入相关dagger2库,-[项目配置介绍](#项目配置介绍)中已配置，此部分内容自行参考

1.)BuilderModule的创建(所有的activity、fragment都要在这里进行注册)（⚠️注意：我在Demo里是放在了包名下面，我在项目开发中会使用到Template模版开发）

参考代码
```

@Module
public abstract class BuilderModule {
    //所有的activity、fragment都要在这里进行注册

}

```

2.)Appcomponent的创建(Application)


参考代码
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

参考代码
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


### 4.配置相关的HulkConfig

1.)在config.gradle中配置开启日志、开启切换环境、BASEURL的相关参数
##### config.gradle的相关参数

参考代码
```
OPEN_LOG = true
OPEN_CHANGE = true
BASE_URL = " http://www.mxnzp.com"
CODE_SUCCESS = "1"
//增删改查返回网络请求成功，因为不支持集合，这里采用,分割
CODELIST_SUCCESS = "1,0"
```

⚠️注意：优先级：CODE_SUCCESS>CODELIST_SUCCESS，针对项目只需要设置其中一个就行

##### app的build.gradle中定义这三个参数,android下的defaultConfig

参考代码
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

参考代码
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

参考代码
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

参考代码
```
HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
logging.setLevel(HttpLoggingInterceptor.Level.BODY);
```

⚠️注意：可进行扩展设置多种拦截器，可参考OkHttp拦截器

3.)默认占位图、默认头像占位图

到此为止，继承HulkApplication的配置项就完成了，这里还设置了Glide、ARouter、SmartRefreshLayout，完整代码参考如下
```
public class HulkUnionApplication extends HulkApplication {

    private static Appcomponent appcomponent;

    public static Appcomponent getAppcomponent() {
        return appcomponent;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
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

### 5.ApiService接口创建（Demo中是放在包名下的module/api下，因为会结合Template去生成代码，因此建议也使用ApiService命名）

```
public interface ApiService {

    @GET("api/address/list")
    Flowable<BaseRes<List<CityListListData>>> getCityList();

}
```

### 6.ARouterUri类创建，ARouter的路径存放（Demo中是放在包名下的consts下，因为会结合Template去生成代码，因此建议也使用ARouterUri命名）,ARouterKey用于数据传递的key

```
public class ARouterUri {

    public static final String MainActivity = "/mvphulk/ui/MainActivity";

}

```

接下来，我们要真正的进入业务开发阶段了

### 7.利用HulkTemplate生成对应单Activity、单Fragment、单DialogFragment、ListActivity、ListFragment
[MVPHulkTemplate使用指南](https://github.com/madreain/MVPHulkTemplate?_blank)

### 8.第6步生成的记得在BuilderModule进行注册

```
@Module
public abstract class BuilderModule {

    @ContributesAndroidInjector(modules = MainModule.class)
    abstract MainActivity mainActivity();

    @ContributesAndroidInjector(modules = HomeModule.class)
    abstract HomeFragment homeFragment();

}
```

### 9.ARouter进行路由跳转、及其参数传递

参考官方文档[ARouter使用指南](https://github.com/alibaba/ARouter/blob/master/README_CN.md)

Activity需设置路由
```
@Route(path = ARouterUri.CityListActivity)
```

### 10.请求接口

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

⚠️注意：项目中，还是建议GET、HEAD、POST、DELETE、PUT、DELETE，遵循restful风格

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

⚠️注意：RSubscriberList（适用于List界面，可刷新加载更多）、RSubscriber（适用于单界面）

Transformer中retrofit（BaseRes类中的泛型 -即有result）、retrofitBaseRes（接口响应只有BaseRes,内部的泛型为null）


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

到此为止，程序可以跑起来，想展示的界面也能展示出来了

[详细相关使用说明参考](https://github.com/madreain/MVPHulk/blob/master/README.md)
