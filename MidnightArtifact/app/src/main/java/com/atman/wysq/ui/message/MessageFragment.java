package com.atman.wysq.ui.message;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.atman.wysq.R;
import com.atman.wysq.adapter.BlackListAdapter;
import com.atman.wysq.adapter.GetFansListAdapter;
import com.atman.wysq.adapter.MessageSessionListAdapter;
import com.atman.wysq.adapter.MyConcernListAdapter;
import com.atman.wysq.model.bean.ImMessage;
import com.atman.wysq.model.bean.ImSession;
import com.atman.wysq.model.bean.TouChuanOtherNotice;
import com.atman.wysq.model.event.YunXinMessageEvent;
import com.atman.wysq.model.greendao.gen.ImMessageDao;
import com.atman.wysq.model.greendao.gen.ImSessionDao;
import com.atman.wysq.model.greendao.gen.TouChuanOtherNoticeDao;
import com.atman.wysq.model.response.GetFansListModel;
import com.atman.wysq.ui.MainActivity;
import com.atman.wysq.ui.base.MyBaseApplication;
import com.atman.wysq.ui.base.MyBaseFragment;
import com.atman.wysq.ui.personal.AddressListInvitationActivity;
import com.atman.wysq.ui.yunxinfriend.MessageCenterActivity;
import com.atman.wysq.ui.yunxinfriend.OtherPersonalActivity;
import com.atman.wysq.ui.yunxinfriend.P2PChatActivity;
import com.atman.wysq.utils.Common;
import com.atman.wysq.utils.UiHelper;
import com.base.baselibs.iimp.AdapterInterface;
import com.base.baselibs.net.MyStringCallback;
import com.base.baselibs.util.PreferenceUtil;
import com.base.baselibs.widget.PromptDialog;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.handmark.pulltorefresh.library.extras.recyclerview.PullToRefreshRecyclerView;
import com.tbl.okhttputils.OkHttpUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;

/**
 * 描述 消息
 * 作者 tangbingliang
 * 时间 16/7/1 18:11
 * 邮箱 bltang@atman.com
 * 电话 18578909061
 */
public class MessageFragment extends MyBaseFragment implements AdapterInterface
        , MyConcernListAdapter.onFollowSlidingViewClickListener, BlackListAdapter.onBalckSlidingViewClickListener {

    @Bind(R.id.message_listview)
    ListView messageListview;
    @Bind(R.id.part_message_topleft_ll)
    LinearLayout partMessageTopleftLl;
    @Bind(R.id.tab_privateletter)
    RadioButton tabPrivateletter;
    @Bind(R.id.tab_follow)
    RadioButton tabFollow;
    @Bind(R.id.tab_fans)
    RadioButton tabFans;
    @Bind(R.id.tab_blacklist)
    RadioButton tabBlacklist;
    @Bind(R.id.tabs_rg)
    RadioGroup tabsRg;
    @Bind(R.id.part_message_topright_ll)
    LinearLayout partMessageToprightLl;
    @Bind(R.id.message_ll)
    LinearLayout messageLl;
    @Bind(R.id.myconcern_empty_tx)
    TextView myconcernEmptyTx;
    @Bind(R.id.pull_follow_recycler)
    PullToRefreshRecyclerView pullFollowRecycler;
    @Bind(R.id.message_follow_ll)
    LinearLayout messageFollowLl;
    @Bind(R.id.pullToRefreshListView)
    PullToRefreshListView pullToRefreshListView;
    @Bind(R.id.message_fans_ll)
    LinearLayout messageFansLl;
    @Bind(R.id.black_empty_tx)
    TextView blackEmptyTx;
    @Bind(R.id.pull_blacklist_recycler)
    PullToRefreshRecyclerView pullBlacklistRecycler;
    @Bind(R.id.message_blacklist_ll)
    LinearLayout messageBlacklistLl;

    private List<ImSession> mImSession;
    private ImSessionDao mImSessionDao;
    private TouChuanOtherNoticeDao mOtherNoticeDao;
    private ImMessageDao mImMessageDao;
    private List<ImMessage> mImMessage;
    private List<ImMessage> mImMessageDelete;
    private List<TouChuanOtherNotice> mTouChuanOtherNotice;
    private ImSession mImSessionDelete;

    private MessageSessionListAdapter mAdapter;

    private BlackListAdapter mBlackListAdapter;
    private GetFansListModel mGetBlackListModel;
    private RecyclerView mBlackRecyclerView;
    private int mBlackPage = 1;
    private int mBlackPosition = 0;

    private MyConcernListAdapter mFollowAdapter;
    private GetFansListModel mGetFollowListModel;
    private RecyclerView mFollowRecyclerView;
    private int mFolowPage = 1;
    private int mFollowPosition = 0;

    private GetFansListAdapter mFansAdapter;
    private GetFansListModel mGetFansListModel;
    private int mFansPage = 1;

    private int selectTopId = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_message, null);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    public void initWidget(View... v) {
        super.initWidget(v);

        mImMessageDao = MyBaseApplication.getApplication().getDaoSession().getImMessageDao();

        initListView();
        initFollowListView();
        initFansListView();
        initBlackListView();
    }

    private void initListView() {
        mAdapter = new MessageSessionListAdapter(getActivity(), this);
        messageListview.setAdapter(mAdapter);
        messageListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (mAdapter.getItem(position).getVerify_status() != -1) {
                    ImSession mImSession = mImSessionDao.queryBuilder()
                            .where(ImSessionDao.Properties.UserId.eq(mAdapter.getItem(position).getUserId())).build().unique();
                    if (mImSession != null) {
                        mImSession.setUnreadNum(0);
                        mImSessionDao.update(mImSession);
                        mAdapter.clearUnreadNum(position);
                        startActivity(P2PChatActivity.buildIntent(getActivity(), mAdapter.getItem(position).getUserId()
                                , mAdapter.getItem(position).getNickName(), mAdapter.getItem(position).getSex()
                                , mAdapter.getItem(position).getIcon(), mAdapter.getItem(position).getVerify_status()));
                    }
                } else {
                    if (!isLogin()) {
                        showLogin();
                        return;
                    }
                    mAdapter.clearUnreadNum(position);
                    startActivity(MessageCenterActivity.buildIntent(getActivity()));
                }
            }
        });
        messageListview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, final long id) {
                if (mAdapter.getItem(position).getVerify_status() != -1) {
                    PromptDialog.Builder builder = new PromptDialog.Builder(getActivity());
                    builder.setMessage("您确定要将该消息删除吗？");
                    builder.setPositiveButton("删除", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            mImMessageDelete = mImMessageDao.queryBuilder().where(ImMessageDao.Properties.ChatId.eq(mAdapter.getItem(position).getUserId()), ImMessageDao.Properties.LoginUserId.eq(
                                    String.valueOf(MyBaseApplication.getApplication().mGetMyUserIndexModel.getBody().getUserDetailBean().getUserId()))).build().list();
                            for (ImMessage imMessageDelete : mImMessageDelete) {
                                mImMessageDao.delete(imMessageDelete);
                            }

                            mImSessionDelete = mImSessionDao.queryBuilder().where(ImSessionDao.Properties.UserId.eq(mAdapter.getItem(position).getUserId()), ImSessionDao.Properties.LoginUserId.eq(
                                    String.valueOf(MyBaseApplication.getApplication().mGetMyUserIndexModel.getBody().getUserDetailBean().getUserId()))).build().unique();
                            if (mImSessionDelete != null) {
                                mImSessionDao.delete(mImSessionDelete);
                                mAdapter.deleteItemById(position);
                            }
                        }
                    });
                    builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    builder.show();
                }
                return true;
            }
        });
    }

    private void initFollowListView() {
        initRefreshView(PullToRefreshBase.Mode.BOTH, pullFollowRecycler);
        mFollowAdapter = new MyConcernListAdapter(getActivity(), getmWidth(), "1", this);

        mFollowRecyclerView = pullFollowRecycler.getRefreshableView();
        mFollowRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));//这里用线性显示 类似于listview
        mFollowRecyclerView.setAdapter(mFollowAdapter);
    }

    private void initFansListView() {
        initRefreshView(PullToRefreshBase.Mode.BOTH, pullToRefreshListView);

        View mEmpty = LayoutInflater.from(getActivity()).inflate(R.layout.part_list_empty, null);
        TextView mEmptyTX = (TextView) mEmpty.findViewById(R.id.empty_list_tx);
        mEmptyTX.setText("你还没有粉丝哦");

        mFansAdapter = new GetFansListAdapter(getActivity(), this);
        pullToRefreshListView.setEmptyView(mEmpty);
        pullToRefreshListView.setAdapter(mFansAdapter);
    }

    private void initBlackListView() {
        initRefreshView(PullToRefreshBase.Mode.BOTH, pullBlacklistRecycler);
        mBlackListAdapter = new BlackListAdapter(getActivity(), getmWidth(), "2", this);

        mBlackRecyclerView = pullBlacklistRecycler.getRefreshableView();
        mBlackRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));//这里用线性显示 类似于listview
        mBlackRecyclerView.setAdapter(mBlackListAdapter);
    }

    @Override
    public void initIntentAndMemData() {
        super.initIntentAndMemData();
    }

    @Override
    public void doInitBaseHttp() {
        super.doInitBaseHttp();
    }

    @Override
    public void onResume() {
        super.onResume();
        setUnreadMessageNum();
    }

    private void setUnreadMessageNum() {
        mImSessionDao = MyBaseApplication.getApplication().getDaoSession().getImSessionDao();
        mOtherNoticeDao = MyBaseApplication.getApplication().getDaoSession().getTouChuanOtherNoticeDao();

        ImSession temp = null;

        if (mImSession != null) {
            mImSession.clear();
        }
        if (mAdapter != null) {
            mAdapter.clearData();
        }
        if (isLogin()) {
            mTouChuanOtherNotice = mOtherNoticeDao.queryBuilder().orderDesc(TouChuanOtherNoticeDao.Properties.Time).build().list();
            String str = "暂时还没有通知";
            int num = 0;
            long time = System.currentTimeMillis();
            if (mTouChuanOtherNotice != null && mTouChuanOtherNotice.size() > 0) {
                for (int i = 0; i < mTouChuanOtherNotice.size(); i++) {
                    if (mTouChuanOtherNotice.get(i).getIsRead() == 0) {
                        num += 1;
                    }
                }
                String s = String.valueOf(mTouChuanOtherNotice.get(0).getTime());
                if (s.contains(".")) {
                    time = Long.parseLong(s.split("\\.")[0]);
                } else {
                    time = Long.parseLong(s);
                }
                if (mTouChuanOtherNotice.get(0).getNoticeType() == 1) {
                    if (mTouChuanOtherNotice.get(0).getAddfriendType() == 1) {
                        str = mTouChuanOtherNotice.get(0).getSend_nickName() + ":请求加你为好友";
                    } else if (mTouChuanOtherNotice.get(0).getAddfriendType() == 2) {
                        str = mTouChuanOtherNotice.get(0).getSend_nickName() + ":我们已经成为朋友啦";
                    } else if (mTouChuanOtherNotice.get(0).getAddfriendType() == 3) {
                        str = mTouChuanOtherNotice.get(0).getReceive_nickName() + ":拒绝了你的加好友请求";
                    }
                } else if (mTouChuanOtherNotice.get(0).getNoticeType() == 8
                        || mTouChuanOtherNotice.get(0).getNoticeType() == 4
                        || mTouChuanOtherNotice.get(0).getNoticeType() == 3) {
                    str = mTouChuanOtherNotice.get(0).getSend_nickName() + ":" + mTouChuanOtherNotice.get(0).getGiftMessage();
                }
            }
            temp = new ImSession("0", PreferenceUtil.getPreferences(getActivity(), PreferenceUtil.PARM_USERID)
                    , str, "通知中心", "", "", -1, time, num);
            mImSession = mImSessionDao.queryBuilder().where(ImSessionDao.Properties.NickName.notEq(""), ImSessionDao.Properties.LoginUserId.eq(
                    PreferenceUtil.getPreferences(getActivity(), PreferenceUtil.PARM_USERID)))
                    .orderDesc(ImSessionDao.Properties.Time).build().list();
        } else {
            temp = new ImSession("0", PreferenceUtil.getPreferences(getActivity(), PreferenceUtil.PARM_USERID)
                    , "暂时还没有通知", "通知中心", "", "", -1, System.currentTimeMillis(), 0);
        }
        if (temp != null) {

        }
        mAdapter.addBody(temp);
        if (mImSession != null) {
            mAdapter.addBody(mImSession);
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
    }

    @Subscribe(threadMode = ThreadMode.MAIN) //第2步:注册一个在后台线程执行的方法,用于接收事件
    public void onMessageEvent(YunXinMessageEvent event) {//参数必须是ClassEvent类型, 否则不会调用此方法
        if (mAdapter != null) {
            mAdapter.setChange(true);
        }
        setUnreadMessageNum();
        ((MainActivity) getActivity()).countUnReadNum();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);

        OkHttpUtils.getInstance().cancelTag(Common.NET_Get_MYCONCERNLIST_ID);
        OkHttpUtils.getInstance().cancelTag(Common.NET_CANCEL_MYCONCERNLIST_ID);
        OkHttpUtils.getInstance().cancelTag(Common.NET_GET_FANSLIST_ID);
        OkHttpUtils.getInstance().cancelTag(Common.NET_GET_BLACKLIST_ID);
        OkHttpUtils.getInstance().cancelTag(Common.NET_CANCEL_BLACKLIST_ID);
    }

    @Override
    public void onItemClick(View view, int position) {
        if (selectTopId==2) {
            startActivity(OtherPersonalActivity.buildIntent(getActivity()
                    , mFansAdapter.getItem(position).getUser_id()));
        }
    }

    @OnClick({R.id.part_message_topright_ll, R.id.tab_privateletter, R.id.tab_follow, R.id.tab_fans
            , R.id.tab_blacklist})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.part_message_topright_ll:
                if (!isLogin()) {
                    showLogin();
                } else {
                    if (!UiHelper.isTabletDevice(getActivity())) {
                        startActivity(new Intent(getActivity(), AddressListInvitationActivity.class));
                    } else {
                        showToast("你的设备暂不支持通讯录功能。");
                    }
                }
                break;
            case R.id.tab_privateletter:
                selectTopId = 0;
                messageLl.setVisibility(View.VISIBLE);
                messageFollowLl.setVisibility(View.GONE);
                messageFansLl.setVisibility(View.GONE);
                messageBlacklistLl.setVisibility(View.GONE);
                break;
            case R.id.tab_follow:
                if (!isLogin()) {
                    ((RadioButton) tabsRg.getChildAt(selectTopId)).setChecked(true);
                    showLogin();
                } else {
                    mFolowPage = 1;
                    selectTopId = 1;
                    mFollowAdapter.clearData();
                    messageLl.setVisibility(View.GONE);
                    messageFollowLl.setVisibility(View.VISIBLE);
                    messageFansLl.setVisibility(View.GONE);
                    messageBlacklistLl.setVisibility(View.GONE);

                    doFollowHttp(true);
                }
                break;
            case R.id.tab_fans:
                if (!isLogin()) {
                    ((RadioButton) tabsRg.getChildAt(selectTopId)).setChecked(true);
                    showLogin();
                } else {
                    mFansPage = 1;
                    selectTopId = 2;
                    mFansAdapter.clearData();
                    messageLl.setVisibility(View.GONE);
                    messageFollowLl.setVisibility(View.GONE);
                    messageFansLl.setVisibility(View.VISIBLE);
                    messageBlacklistLl.setVisibility(View.GONE);

                    doFansHttp(true);
                }
                break;
            case R.id.tab_blacklist:
                if (!isLogin()) {
                    ((RadioButton) tabsRg.getChildAt(selectTopId)).setChecked(true);
                    showLogin();
                } else {
                    selectTopId = 3;
                    mBlackPage = 1;
                    mBlackListAdapter.clearData();
                    messageLl.setVisibility(View.GONE);
                    messageFollowLl.setVisibility(View.GONE);
                    messageFansLl.setVisibility(View.GONE);
                    messageBlacklistLl.setVisibility(View.VISIBLE);

                    doBlackHttp(true);
                }
                break;

        }
    }

    @Override
    public void onStringResponse(String data, Response response, int id) {
        super.onStringResponse(data, response, id);
        if (id == Common.NET_Get_MYCONCERNLIST_ID) {
            mGetFollowListModel = mGson.fromJson(data, GetFansListModel.class);

            if (mGetFollowListModel.getBody() == null
                    || mGetFollowListModel.getBody().size() == 0) {
                if (mFollowAdapter != null && mFollowAdapter.getItemCount() > 0) {
                    showToast("没有更多");
                }
                onLoad(PullToRefreshBase.Mode.PULL_FROM_START, pullFollowRecycler);
            } else {
                onLoad(PullToRefreshBase.Mode.BOTH, pullFollowRecycler);
                if (mFolowPage == 1) {
                    mFollowAdapter.clearData();
                }
                mFollowAdapter.addData(mGetFollowListModel.getBody());
            }
        } else if (id == Common.NET_CANCEL_MYCONCERNLIST_ID) {
            mFollowAdapter.removeData(mFollowPosition);
        } else if (id == Common.NET_GET_FANSLIST_ID) {
            mGetFansListModel = mGson.fromJson(data, GetFansListModel.class);
            if (mGetFansListModel.getBody() == null
                    || mGetFansListModel.getBody().size() == 0) {
                if (mFansAdapter != null && mFansAdapter.getCount() > 0) {
                    showToast("没有更多");
                }
                onLoad(PullToRefreshBase.Mode.PULL_FROM_START, pullToRefreshListView);
            } else {
                onLoad(PullToRefreshBase.Mode.BOTH, pullToRefreshListView);
                if (mFolowPage == 1) {
                    mFansAdapter.clearData();
                }
                mFansAdapter.addBody(mGetFansListModel.getBody());
            }
        } else if (id == Common.NET_GET_BLACKLIST_ID) {
            mGetBlackListModel = mGson.fromJson(data, GetFansListModel.class);
            if (mGetBlackListModel.getBody() == null
                    || mGetBlackListModel.getBody().size() == 0) {
                if (mBlackListAdapter != null && mBlackListAdapter.getItemCount() > 0) {
                    showToast("没有更多");
                }
                onLoad(PullToRefreshBase.Mode.PULL_FROM_START, pullBlacklistRecycler);
            } else {
                onLoad(PullToRefreshBase.Mode.BOTH, pullBlacklistRecycler);
                if (mBlackPage == 1) {
                    mBlackListAdapter.clearData();
                }
                mBlackListAdapter.addData(mGetBlackListModel.getBody());
            }
        } else if (id == Common.NET_CANCEL_BLACKLIST_ID) {
            mBlackListAdapter.removeData(mBlackPosition);
        }

        if (selectTopId==1) {
            isNullChangeForFollow();
        } else if (selectTopId==3) {
            isBlackNullChange();
        }
    }

    private void isBlackNullChange() {
        if (mBlackListAdapter!=null && mBlackListAdapter.getItemCount()>0) {
            blackEmptyTx.setVisibility(View.GONE);
            pullBlacklistRecycler.setVisibility(View.VISIBLE);
        } else {
            blackEmptyTx.setVisibility(View.VISIBLE);
            pullBlacklistRecycler.setVisibility(View.GONE);
        }
    }

    @Override
    public void onPullDownToRefresh(PullToRefreshBase refreshView) {
        if (selectTopId==1) {
            mFolowPage = 1;
            mFollowAdapter.clearData();
            doFollowHttp(false);
        } else if (selectTopId==2) {
            mFansPage = 1;
            doFansHttp(false);
        } else if (selectTopId==3) {
            mBlackPage = 1;
            mBlackListAdapter.clearData();
            doBlackHttp(false);
        }
    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase refreshView) {
        if (selectTopId==1) {
            mFolowPage += 1;
            doFollowHttp(false);
        } else if (selectTopId==2) {
            mFansPage += 1;
            doFansHttp(false);
        } else if (selectTopId==3) {
            mBlackPage += 1;
            doBlackHttp(false);
        }
    }

    @Override
    public void onError(Call call, Exception e, int code, int id) {
        super.onError(call, e, code, id);
        if (selectTopId==1) {
            mFolowPage = 1;
            onLoad(PullToRefreshBase.Mode.BOTH, pullFollowRecycler);
        } else if (selectTopId==2) {
            mFansPage = 1;
            onLoad(PullToRefreshBase.Mode.BOTH, pullToRefreshListView);
        } else if (selectTopId==3) {
            mBlackPage = 1;
            onLoad(PullToRefreshBase.Mode.BOTH, pullBlacklistRecycler);
        }
    }

    private void isNullChangeForFollow() {
        if (mFollowAdapter!=null && mFollowAdapter.getItemCount()>0) {
            myconcernEmptyTx.setVisibility(View.GONE);
            pullFollowRecycler.setVisibility(View.VISIBLE);
        } else {
            myconcernEmptyTx.setVisibility(View.VISIBLE);
            pullFollowRecycler.setVisibility(View.GONE);
        }
    }

    private void doFollowHttp(boolean b) {
        OkHttpUtils.get().url(Common.Url_Get_MyConcernList + mFolowPage)
                .addHeader("cookie", MyBaseApplication.getApplication().getCookie())
                .tag(Common.NET_Get_MYCONCERNLIST_ID).id(Common.NET_Get_MYCONCERNLIST_ID).build()
                .execute(new MyStringCallback(getActivity(), this, b));
    }

    @Override
    public void onFollowItemClick(View view, int position) {
        mFollowPosition = position;
        startActivityForResult(OtherPersonalActivity.buildIntent(getActivity()
                , mFollowAdapter.getItemById(position).getUser_id()), Common.toOtherPersonal);
    }

    @Override
    public void onFollowDeleteBtnCilck(View view, int position) {
        mFollowPosition = position;
        switch (view.getId()) {
            case R.id.tv_edit:
                break;
            case R.id.tv_delete:
                PromptDialog.Builder builder = new PromptDialog.Builder(getActivity());
                builder.setMessage("确定取消关注TA吗?");
                builder.setPositiveButton("不了", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.setNegativeButton("取消关注", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        OkHttpUtils.postString()
                                .url(Common.Url_Cancel_MyConcernList + mFollowAdapter.getItemById(mFollowPosition).getUser_id())
                                .tag(Common.NET_CANCEL_MYCONCERNLIST_ID).id(Common.NET_CANCEL_MYCONCERNLIST_ID)
                                .content(mGson.toJson(""))
                                .addHeader("cookie", MyBaseApplication.getApplication().getCookie())
                                .build().execute(new MyStringCallback(getActivity(), MessageFragment.this, true));
                    }
                });
                builder.show();
                break;
        }
    }

    private void doFansHttp(boolean b) {
        OkHttpUtils.get().url(Common.Url_Get_FansList + mFansPage)
                .addHeader("cookie", MyBaseApplication.getApplication().getCookie())
                .tag(Common.NET_GET_FANSLIST_ID).id(Common.NET_GET_FANSLIST_ID).build()
                .execute(new MyStringCallback(getActivity(), this, b));
    }

    @Override
    public void onBlackItemClick(View view, int position) {

    }

    @Override
    public void onBlackDeleteBtnCilck(View view, int position) {
        mBlackPosition = position;
        switch (view.getId()) {
            case R.id.tv_edit:
                break;
            case R.id.tv_delete:
                PromptDialog.Builder builder = new PromptDialog.Builder(getActivity());
                builder.setMessage("确定将TA从黑名单移除吗?");
                builder.setPositiveButton("不了", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.setNegativeButton("移除", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        OkHttpUtils.postString()
                                .url(Common.Url_Cancel_BlackList + mBlackListAdapter.getItemById(mBlackPosition).getUser_id())
                                .tag(Common.NET_CANCEL_BLACKLIST_ID).id(Common.NET_CANCEL_BLACKLIST_ID)
                                .content(mGson.toJson(""))
                                .addHeader("cookie", MyBaseApplication.getApplication().getCookie())
                                .build().execute(new MyStringCallback(getActivity(), MessageFragment.this, true));
                    }
                });
                builder.show();
                break;
        }
    }

    private void doBlackHttp(boolean b) {
        OkHttpUtils.get().url(Common.Url_Get_BlackList + mBlackPage)
                .addHeader("cookie", MyBaseApplication.getApplication().getCookie())
                .tag(Common.NET_GET_BLACKLIST_ID).id(Common.NET_GET_BLACKLIST_ID).build()
                .execute(new MyStringCallback(getActivity(), this, b));
    }
}
