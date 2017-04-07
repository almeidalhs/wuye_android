package com.atman.wysq.ui.base;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Environment;
import android.support.multidex.MultiDex;
import android.text.TextUtils;
import android.widget.Toast;

import com.atman.wysq.R;
import com.atman.wysq.model.bean.TouChuanGiftNotice;
import com.atman.wysq.model.bean.TouChuanOtherNotice;
import com.atman.wysq.model.event.YunXinAddFriendEvent;
import com.atman.wysq.model.event.YunXinMessageEvent;
import com.atman.wysq.model.greendao.gen.DaoMaster;
import com.atman.wysq.model.greendao.gen.DaoSession;
import com.atman.wysq.model.greendao.gen.TouChuanOtherNoticeDao;
import com.atman.wysq.model.request.AddPostContentModel;
import com.atman.wysq.model.response.ConfigModel;
import com.atman.wysq.model.response.GetGoldenRoleModel;
import com.atman.wysq.model.response.GetMyUserIndexModel;
import com.atman.wysq.model.response.GoodsListModel;
import com.atman.wysq.ui.MainActivity;
import com.atman.wysq.ui.receiver.RootMessageObserver;
import com.atman.wysq.yunxin.DemoCache;
import com.atman.wysq.yunxin.utils.SystemUtil;
import com.base.baselibs.base.BaseApplication;
import com.base.baselibs.net.YunXinAuthOutEvent;
import com.base.baselibs.util.LogUtils;
import com.base.baselibs.util.PhoneInfo;
import com.base.baselibs.util.PreferenceUtil;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.google.gson.Gson;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.Observer;
import com.netease.nimlib.sdk.RequestCallback;
import com.netease.nimlib.sdk.SDKOptions;
import com.netease.nimlib.sdk.StatusBarNotificationConfig;
import com.netease.nimlib.sdk.StatusCode;
import com.netease.nimlib.sdk.auth.AuthServiceObserver;
import com.netease.nimlib.sdk.auth.LoginInfo;
import com.netease.nimlib.sdk.msg.MsgServiceObserve;
import com.netease.nimlib.sdk.msg.attachment.FileAttachment;
import com.netease.nimlib.sdk.msg.constant.AttachStatusEnum;
import com.netease.nimlib.sdk.msg.constant.SessionTypeEnum;
import com.netease.nimlib.sdk.msg.model.CustomNotification;
import com.netease.nimlib.sdk.msg.model.IMMessage;
import com.netease.nimlib.sdk.uinfo.UserInfoProvider;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.cache.disc.naming.HashCodeFileNameGenerator;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.cache.memory.impl.UsingFreqLimitedMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.nostra13.universalimageloader.utils.StorageUtils;
import com.umeng.socialize.PlatformConfig;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * 描述
 * 作者 tangbingliang
 * 时间 16/6/30 14:31
 * 邮箱 bltang@atman.com
 * 电话 18578909061
 */
public class MyBaseApplication extends BaseApplication {

    private static MyBaseApplication mInstance;
    private String mUserName = "";
    private String mPassWord = "";
    private String mUserKey = "";
    private String mUserToken = "";
    private String mUserId = "";

    public static String mLiveStatue = "0";//0是正常，1是主播，2是听众
    public static String mDeviceToken = "";
    public static String mVersionName = "";
    public static String mChannel = "";
    public static String mPhoneModel = "";
    public static String mPhoneMac = "";
    public static String mPhoneDeviceId = "";
    public static String mWEB_URL = "";
    public static String KDiamondChargeCoin = "";
    public static String KDiamondCashStart = "";
    public static String KDiamondCash = "";
    public static String mWEB_TYPE = "";
    public static String mWEB_ID = "";
    public static String mDownLoad_URL = "";
    public static String kPrivateChatCost = "";
    public static String mHEAD_URL = "";
    public static String mLocationID = "0";
    public int mLOGIN_STATUS = 0;//0:登录中，1：登录成功，2：登录失败
    public static int mUserCion = 0;
    public static List<ConfigModel.ShopBean> mShop ;
    public static GetGoldenRoleModel mGetGoldenRoleModel ;
    public static GetMyUserIndexModel mGetMyUserIndexModel ;
    public boolean isLock = true;
    public boolean isFilterLock = false;
    public static boolean isReportUnRead = false;
    public boolean isError = false;
    public static String appId = "";

    public static List<GoodsListModel> creatPostGoods = new ArrayList<>();
    public static List<AddPostContentModel> creatPostContents = new ArrayList<>();
    public static int isRelation = 0;//0复位，1图文帖，2语音，3视频
    public static String imagetextPostTitle = "";
    public static String voicePath = "";
    public static int voiceLength = 0;

    private DisplayImageOptions options,optionsHead, optionsNot;

    private DaoMaster.DevOpenHelper mHelper;
    private SQLiteDatabase db;
    private DaoMaster mDaoMaster;
    private DaoSession mDaoSession;

    @Override
    public void onCreate() {
        super.onCreate();

        mInstance = this;

        initPhoneInfo();
        initLoginData();

        setConfigLoad();
        InitDownImageConfig();
        initDisplayConfig();

        UMmengInit();

        setDatabase();

        YunXinInit();

        Fresco.initialize(this);

    }

    public int getmLOGIN_STATUS() {
        return mLOGIN_STATUS;
    }

    public void setmLOGIN_STATUS(int mLOGIN_STATUS) {
        this.mLOGIN_STATUS = mLOGIN_STATUS;
    }

    public static MyBaseApplication getApplication() {
        return mInstance;
    }

    /**     * 设置greenDao     */
    private void setDatabase() {

        // 通过DaoMaster 的内部类 DevOpenHelper，你可以得到一个便利的SQLiteOpenHelper 对象。
        // 可能你已经注意到了，你并不需要去编写「CREATE TABLE」这样的 SQL 语句，因为greenDAO 已经帮你做了。
        // 注意：默认的DaoMaster.DevOpenHelper 会在数据库升级时，删除所有的表，意味着这将导致数据的丢失。
        // 所以，在正式的项目中，你还应该做一层封装，来实现数据库的安全升级。
        mHelper = new DaoMaster.DevOpenHelper(this,"atmandb", null);
        db =mHelper.getWritableDatabase();
        // 注意：该数据库连接属于DaoMaster，所以多个 Session 指的是相同的数据库连接。
        mDaoMaster = new DaoMaster(db);
        mDaoSession = mDaoMaster.newSession();
    }


    public DaoSession getDaoSession() {
        return mDaoSession;
    }


    public SQLiteDatabase getDb() {
        return db;
    }

    private void YunXinInit() {
        DemoCache.setContext(this);
        // SDK初始化（启动后台服务，若已经存在用户登录信息， SDK 将完成自动登录）
        NIMClient.init(this, loginInfo(), options());

        if (isLogined()) {
            initObserver(true);
        }
    }

    public void initObserver(boolean b) {
        if (inMainProcess()) {
            // 注意：以下操作必须在主进程中进行
            // 1、UI相关初始化操作
            // 2、相关Service调用
            setAuthServiceObserver(b);//监听用户在线状态
            ReceiveMessageObserver(b);
            setTouChuanMessageObserver(b);
        }
    }

    private void setTouChuanMessageObserver(boolean b) {
        NIMClient.getService(MsgServiceObserve.class).observeCustomNotification(new Observer<CustomNotification>() {
            @Override
            public void onEvent(CustomNotification message) {
                // 在这里处理自定义通知。
                LogUtils.e("touchuan>>>>getSessionType:"+message.getSessionType());
                LogUtils.e("touchuan>>>>getContent:"+message.getContent());
                TouChuanGiftNotice mTouChuanGiftNotice = new Gson().fromJson(message.getContent(), TouChuanGiftNotice.class);
                if (mTouChuanGiftNotice.getType()==8) {//礼物通知
                    getDaoSession().getTouChuanGiftNoticeDao().insert(mTouChuanGiftNotice);
                    LogUtils.e("getType:"+ mTouChuanGiftNotice.getType()
                            +",getGiftContent:"+ mTouChuanGiftNotice.getGiftContent()
                            +",getGiftIcon:"+ mTouChuanGiftNotice.getGiftIcon());

                } else {
                    TouChuanOtherNotice mTouChuanOtherNotice = new Gson().fromJson(message.getContent(), TouChuanOtherNotice.class);

                    if (mTouChuanOtherNotice.getAddfriendType()==2) {
                        Toast.makeText(getApplication(), "\""+mTouChuanOtherNotice.getReceive_nickName()
                                +"\" 与你成为朋友啦", Toast.LENGTH_SHORT).show();
                    } else if (mTouChuanOtherNotice.getAddfriendType()==3) {
                        Toast.makeText(getApplication(), "\""+mTouChuanOtherNotice.getReceive_nickName()
                                +"\" 拒绝了你的加好友请求", Toast.LENGTH_SHORT).show();
                    }

                    if (mTouChuanOtherNotice.getNoticeType()==1) {//加好友通知
                        TouChuanOtherNotice temp = getDaoSession().getTouChuanOtherNoticeDao().queryBuilder()
                                .where(TouChuanOtherNoticeDao.Properties.NoticeType.eq(1)
                                        , TouChuanOtherNoticeDao.Properties.Send_userId.eq(mTouChuanOtherNotice.getSend_userId())
                                        , TouChuanOtherNoticeDao.Properties.Receive_userId.eq(mTouChuanOtherNotice.getReceive_userId())
                                        , TouChuanOtherNoticeDao.Properties.IsRead.eq(0)).build().unique();
                        if (temp==null) {
                            mTouChuanOtherNotice.setTime(String.valueOf(System.currentTimeMillis()));
                            getDaoSession().getTouChuanOtherNoticeDao().insert(mTouChuanOtherNotice);
                        } else {
                            temp.setIsEmbalmed(false);
                            temp.setIsRead(0);
                            temp.setTime(String.valueOf(System.currentTimeMillis()));
                            getDaoSession().getTouChuanOtherNoticeDao().update(temp);
                        }
                        EventBus.getDefault().post(new YunXinAddFriendEvent(mTouChuanOtherNotice.getAddfriendType()));
                    }
                }
                EventBus.getDefault().post(new YunXinMessageEvent());
            }
        }, b);
    }

    public boolean isOriginImageHasDownloaded(final IMMessage message) {
        if (message.getAttachStatus() == AttachStatusEnum.transferred &&
                !TextUtils.isEmpty(((FileAttachment) message.getAttachment()).getPath())) {
            return true;
        }
        return false;
    }

    private RequestCallback<List<IMMessage>> callback = new RequestCallback<List<IMMessage>>() {
        @Override
        public void onSuccess(List<IMMessage> imMessages) {
            if (imMessages!=null) {
            } else {
            }
        }

        @Override
        public void onFailed(int i) {

        }

        @Override
        public void onException(Throwable throwable) {

        }
    };

    public void ReceiveMessageObserver(boolean b) {
        NIMClient.getService(MsgServiceObserve.class).observeReceiveMessage(new RootMessageObserver(this), b);
    }

    public boolean inMainProcess() {
        String packageName = getPackageName();
        String processName = SystemUtil.getProcessName(this);
        return packageName.equals(processName);
    }

    // 如果返回值为 null，则全部使用默认参数。
    private SDKOptions options() {
        SDKOptions options = new SDKOptions();

        // 如果将新消息通知提醒托管给 SDK 完成，需要添加以下配置。否则无需设置。
        StatusBarNotificationConfig config = new StatusBarNotificationConfig();
        config.notificationEntrance = MainActivity.class; // 点击通知栏跳转到该Activity
        config.notificationSmallIconId = R.drawable.ic_app;
        // 呼吸灯配置
        config.ledARGB = Color.GREEN;
        config.ledOnMs = 1000;
        config.ledOffMs = 1500;
        // 通知铃声的uri字符串
        config.notificationSound = "android.resource://com.netease.nim.demo/raw/msg";
//        options.statusBarNotificationConfig = config;

        // 配置保存图片，文件，log 等数据的目录
        // 如果 options 中没有设置这个值，SDK 会使用下面代码示例中的位置作为 SDK 的数据目录。
        // 该目录目前包含 log, file, image, audio, video, thumb 这6个目录。
        // 如果第三方 APP 需要缓存清理功能， 清理这个目录下面个子目录的内容即可。
        String sdkPath = Environment.getExternalStorageDirectory() + "/" + getPackageName() + "/nim";
        LogUtils.e("sdkPath:"+sdkPath);
        options.sdkStorageRootPath = sdkPath;

        // 配置是否需要预下载附件缩略图，默认为 true
        options.preloadAttach = true;

        // 配置附件缩略图的尺寸大小。表示向服务器请求缩略图文件的大小
        // 该值一般应根据屏幕尺寸来确定， 默认值为 Screen.width / 2
        options.thumbnailSize = 360;

        // 用户资料提供者, 目前主要用于提供用户资料，用于新消息通知栏中显示消息来源的头像和昵称
        options.userInfoProvider = new UserInfoProvider() {
            @Override
            public UserInfo getUserInfo(String account) {
                return null;
            }

            @Override
            public int getDefaultIconResId() {
                return R.mipmap.avatar_def;
            }

            @Override
            public Bitmap getTeamIcon(String tid) {
                return null;
            }

            @Override
            public Bitmap getAvatarForMessageNotifier(String account) {
                return null;
            }

            @Override
            public String getDisplayNameForMessageNotifier(String account, String sessionId,
                                                           SessionTypeEnum sessionType) {
                return null;
            }
        };
        return options;
    }

    // 如果已经存在用户登录信息，返回LoginInfo，否则返回null即可
    private LoginInfo loginInfo() {
        // 从本地读取上次登录成功时保存的用户登录信息
        String account = PreferenceUtil.getPreferences(getApplicationContext(), PreferenceUtil.PARM_USERID);
        String token = PreferenceUtil.getPreferences(getApplicationContext(), PreferenceUtil.PARM_YUNXIN_TOKEN);

        if (!TextUtils.isEmpty(account) && !TextUtils.isEmpty(token)) {
            DemoCache.setAccount(account.toLowerCase());
            return new LoginInfo(account, token);
        } else {
            return null;
        }
    }

    private void UMmengInit() {
        //微信(朋友圈) appid appsecret
        PlatformConfig.setWeixin("wx142af8d518f19762", "e581275d1a31b0df7d7c29b371cbfdfb");
        //新浪微博 appkey appsecret
        PlatformConfig.setSinaWeibo("79633130", "198c96961ac6c1db2085be3ad229bb5b");
        // QQ和Qzone appid appkey
        PlatformConfig.setQQZone("1104488827", "nSXfacXqGsgG7pU2");
    }

    public boolean isFilterLock() {
        return isFilterLock;
    }

    public void setFilterLock(boolean filterLock) {
        isFilterLock = filterLock;
    }

    public boolean isLock() {
        return isLock;
    }

    public void setLock(boolean lock) {
        isLock = lock;
    }

    public boolean isError() {
        return isError;
    }

    public void setError(boolean error) {
        isError = error;
    }

    private void initPhoneInfo() {
        PhoneInfo mPhoneInfo = new PhoneInfo(getApplicationContext());
        mVersionName = "v" + getAppInfo().split("-")[0];
        mChannel = getAppMetaData(getApplicationContext(), "UMENG_CHANNEL");
        mPhoneModel = android.os.Build.MODEL;
        mPhoneMac = mPhoneInfo.getMac();
        mPhoneDeviceId = mPhoneInfo.getIMEI();
    }

    public boolean isLogined() {
        initLoginData();
        return !mUserName.isEmpty() && !mPassWord.isEmpty() &&
                !mUserKey.isEmpty() && !mUserToken.isEmpty() && !mUserId.isEmpty();
    }

    private void initLoginData() {
        mUserName = PreferenceUtil.getPreferences(getApplicationContext(), PreferenceUtil.PARM_US);
        mPassWord = PreferenceUtil.getPreferences(getApplicationContext(), PreferenceUtil.PARM_PW);
        mUserKey = PreferenceUtil.getPreferences(getApplicationContext(), PreferenceUtil.PARM_USER_KEY);
        mUserToken = PreferenceUtil.getPreferences(getApplicationContext(), PreferenceUtil.PARM_USER_TOKEN);
        mUserId = PreferenceUtil.getPreferences(getApplicationContext(), PreferenceUtil.PARM_USERID);
        mDeviceToken = PreferenceUtil.getPreferences(getApplicationContext(), PreferenceUtil.PARM_USER_TOKEN);
//        LogUtils.e("mUserName:"+mUserName+",mPassWord:"+mPassWord+",mUserKey:"+mUserKey+",mUserToken:"+mUserToken+",mUserId:"+mUserId);
    }

    public void cleanLoginData() {
        PreferenceUtil.savePreference(getApplicationContext(), PreferenceUtil.PARM_PW, "");
        PreferenceUtil.savePreference(getApplicationContext(), PreferenceUtil.PARM_USER_KEY, "");
        PreferenceUtil.savePreference(getApplicationContext(), PreferenceUtil.PARM_USER_TOKEN, "");
        PreferenceUtil.savePreference(getApplicationContext(), PreferenceUtil.PARM_USERID, "");
        PreferenceUtil.savePreference(getApplicationContext(), PreferenceUtil.PARM_YUNXIN_TOKEN, "");
        PreferenceUtil.saveIntPreference(getApplicationContext(), PreferenceUtil.PARM_YUNXIN_WRAN, 0);
        mGetMyUserIndexModel = null;
        getDaoSession().getImMessageDao().deleteAll();
        getDaoSession().getImSessionDao().deleteAll();
        getDaoSession().getTouChuanGiftNoticeDao().deleteAll();
        getDaoSession().getTouChuanOtherNoticeDao().deleteAll();
        initObserver(false);
    }

    public String getmUserId() {
        return mUserId;
    }

    public String getCookie() {
        String key = PreferenceUtil.getPreferences(getApplicationContext(), PreferenceUtil.PARM_USER_KEY);
        String token = PreferenceUtil.getPreferences(getApplicationContext(), PreferenceUtil.PARM_USER_TOKEN);
//        if (token.isEmpty() && !key.isEmpty()) {
//            token = key.replace("-","");
//        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("USER_KEY=");
        stringBuilder.append(key);
        stringBuilder.append(";USER_TOKEN=");
        stringBuilder.append(token);
        return stringBuilder.toString();
    }

    private String getAppInfo() {
        try {
            String pkName = this.getPackageName();
            String versionName = this.getPackageManager().getPackageInfo(pkName, 0).versionName;
            int versionCode = this.getPackageManager().getPackageInfo(pkName, 0).versionCode;
            return versionName;
        } catch (Exception e) {
        }
        return null;
    }

    /**
     * 获取application中指定的meta-data
     * @return 如果没有获取成功(没有对应值，或者异常)，则返回值为空
     */
    public static String getAppMetaData(Context ctx, String key) {
        if (ctx == null || TextUtils.isEmpty(key)) {
            return null;
        }
        String resultData = null;
        try {
            PackageManager packageManager = ctx.getPackageManager();
            if (packageManager != null) {
                ApplicationInfo applicationInfo = packageManager.getApplicationInfo(ctx.getPackageName(), PackageManager.GET_META_DATA);
                if (applicationInfo != null) {
                    if (applicationInfo.metaData != null) {
                        resultData = applicationInfo.metaData.getString(key);
                    }
                }
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        return resultData;
    }

    private void InitDownImageConfig() {
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getApplication())
                .memoryCacheExtraOptions(100, 100) // default = device screen dimensions 内存缓存文件的最大长宽
                .diskCacheExtraOptions(100, 100, null)  // 本地缓存的详细信息(缓存的最大长宽)，最好不要设置这个
//                .taskExecutor()
//              .taskExecutorForCachedImages(...)
                .threadPoolSize(8) // default  线程池内加载的数量
                .threadPriority(Thread.NORM_PRIORITY - 2) // default 设置当前线程的优先级
                .tasksProcessingOrder(QueueProcessingType.FIFO) // default
                .denyCacheImageMultipleSizesInMemory()
                .memoryCache(new LruMemoryCache(2 * 1024 * 1024)) //可以通过自己的内存缓存实现
                .memoryCacheSize(2 * 1024 * 1024)  // 内存缓存的最大值
                .memoryCacheSizePercentage(13) // default
//                .diskCache(new UnlimitedDiscCache(cacheDir)) // default 可以自定义缓存路径
                .diskCacheSize(50 * 1024 * 1024) // 50 Mb sd卡(本地)缓存的最大值
                .diskCacheFileCount(100)  // 可以缓存的文件数量
                // default为使用HASHCODE对UIL进行加密命名， 还可以用MD5(new Md5FileNameGenerator())加密
                .diskCacheFileNameGenerator(new HashCodeFileNameGenerator())
                .imageDownloader(new BaseImageDownloader(getApplication())) // default
//                .imageDecoder(new BaseImageDecoder()) // default
                .defaultDisplayImageOptions(DisplayImageOptions.createSimple()) // default
                .writeDebugLogs() // 打印debug log
                .build(); //开始构建
        // Initialize ImageLoader with configuration.
        ImageLoader.getInstance().init(config);
    }

    private void initDisplayConfig() {
        options = new DisplayImageOptions.Builder()
//                .showImageOnLoading(R.mipmap.ic_app) //设置图片在下载期间显示的图片
                .showImageForEmptyUri(R.mipmap.ic_launcher)//设置图片Uri为空或是错误的时候显示的图片
                .showImageOnFail(R.mipmap.ic_launcher)  //设置图片加载/解码过程中错误时候显示的图片
                .cacheInMemory(true)//设置下载的图片是否缓存在内存中
                .cacheOnDisc(true)//设置下载的图片是否缓存在SD卡中
                .considerExifParams(true)  //是否考虑JPEG图像EXIF参数（旋转，翻转）
                .imageScaleType(ImageScaleType.EXACTLY_STRETCHED)//设置图片以如何的编码方式显示
                .bitmapConfig(Bitmap.Config.RGB_565)//设置图片的解码类型//
//                .decodingOptions(android.graphics.BitmapFactory.Options decodingOptions)//设置图片的解码配置
//                .delayBeforeLoading(int delayInMillis)//int delayInMillis为你设置的下载前的延迟时间
//                .preProcessor(BitmapProcessor preProcessor)  //设置图片加入缓存前，对bitmap进行设置
                .resetViewBeforeLoading(true)//设置图片在下载前是否重置，复位
                .displayer(new RoundedBitmapDisplayer(20))//是否设置为圆角，弧度为多少
                .displayer(new FadeInBitmapDisplayer(100))//是否图片加载好后渐入的动画时间
                .build();//构建完成

        optionsNot = new DisplayImageOptions
                .Builder()
                .cacheInMemory(false)//设置下载的图片是否缓存在内存中
                .cacheOnDisc(true)//设置下载的图片是否缓存在SD卡中
                .considerExifParams(true)  //是否考虑JPEG图像EXIF参数（旋转，翻转）
                .imageScaleType(ImageScaleType.EXACTLY_STRETCHED)//设置图片以如何的编码方式显示
                .bitmapConfig(Bitmap.Config.RGB_565)//设置图片的解码类型
                .resetViewBeforeLoading(true)//设置图片在下载前是否重置，复位
                .displayer(new RoundedBitmapDisplayer(20))//是否设置为圆角，弧度为多少
                .displayer(new FadeInBitmapDisplayer(100))//是否图片加载好后渐入的动画时间
                .build();//构建完成

        optionsHead = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.mipmap.niming_ic) //设置图片在下载期间显示的图片
                .showImageForEmptyUri(R.mipmap.niming_ic)//设置图片Uri为空或是错误的时候显示的图片
                .showImageOnFail(R.mipmap.niming_ic)  //设置图片加载/解码过程中错误时候显示的图片
                .cacheInMemory(false)//设置下载的图片是否缓存在内存中
                .cacheOnDisc(true)//设置下载的图片是否缓存在SD卡中
                .considerExifParams(true)  //是否考虑JPEG图像EXIF参数（旋转，翻转）
                .imageScaleType(ImageScaleType.EXACTLY_STRETCHED)//设置图片以如何的编码方式显示
                .bitmapConfig(Bitmap.Config.RGB_565)//设置图片的解码类型
                .resetViewBeforeLoading(true)//设置图片在下载前是否重置，复位
                .displayer(new RoundedBitmapDisplayer(20))//是否设置为圆角，弧度为多少
                .displayer(new FadeInBitmapDisplayer(100))//是否图片加载好后渐入的动画时间
                .build();//构建完成
    }

    public DisplayImageOptions getOptions() {
        return options;
    }

    public DisplayImageOptions getOptionsNot() {
        return optionsNot;
    }

    public DisplayImageOptions getOptionsHead() {
        return optionsHead;
    }

    private void setConfigLoad() {
        File cacheDir = StorageUtils.getOwnCacheDirectory(this, "imageloader/Cache");
        ImageLoaderConfiguration config = new ImageLoaderConfiguration
                .Builder(this)
                .memoryCacheExtraOptions(200, 200) // maxwidth, max height，即保存的每个缓存文件的最大长宽
                .threadPoolSize(6)//线程池内加载的数量
                .threadPriority(Thread.NORM_PRIORITY -2)
                .denyCacheImageMultipleSizesInMemory()
                .memoryCache(new UsingFreqLimitedMemoryCache(2* 1024 * 1024)) // You can pass your own memory cache implementation/你可以通过自己的内存缓存实现
                .memoryCacheSize(4 * 1024 * 1024)
                .discCacheSize(50 * 1024 * 1024)
                .discCacheFileNameGenerator(new Md5FileNameGenerator())//将保存的时候的URI名称用MD5 加密
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                .discCacheFileCount(100) //缓存的文件数量
                .discCache(new UnlimitedDiskCache(cacheDir))//自定义缓存路径
                .defaultDisplayImageOptions(DisplayImageOptions.createSimple())
                .imageDownloader(new BaseImageDownloader(this,5 * 1000, 30 * 1000)) // connectTimeout (5 s), readTimeout (30 s)超时时间
                .writeDebugLogs() // Remove for releaseapp
                .build();//开始构建
        ImageLoader.getInstance().init(config);
    }

    public void setAuthServiceObserver(boolean b){
        NIMClient.getService(AuthServiceObserver.class).observeOnlineStatus(
                new Observer<StatusCode>() {
                    public void onEvent(StatusCode status) {
                        LogUtils.e("User status changed to: " + status);
                        if (status.wontAutoLogin()) {
                            logout();
                        }
                    }
                }, b);
    }

    public void logout() {
        // 被踢出、账号被禁用、密码错误等情况，自动登录失败，需要返回到登录界面进行重新登录操作
        cleanLoginData();
        EventBus.getDefault().post(new YunXinAuthOutEvent());
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}
