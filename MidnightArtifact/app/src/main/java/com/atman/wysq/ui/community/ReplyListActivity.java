package com.atman.wysq.ui.community;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.atman.wysq.R;
import com.atman.wysq.adapter.ReplayListAdapter;
import com.atman.wysq.model.bean.TouChuanOtherNotice;
import com.atman.wysq.model.event.YunXinAddFriendEvent;
import com.atman.wysq.model.event.YunXinMessageEvent;
import com.atman.wysq.model.greendao.gen.TouChuanOtherNoticeDao;
import com.atman.wysq.model.response.ReplayListModel;
import com.atman.wysq.ui.base.MyBaseActivity;
import com.atman.wysq.ui.base.MyBaseApplication;
import com.atman.wysq.ui.yunxinfriend.OtherPersonalActivity;
import com.atman.wysq.utils.Common;
import com.atman.wysq.utils.UiHelper;
import com.atman.wysq.widget.face.FaceRelativeLayout;
import com.base.baselibs.iimp.AdapterInterface;
import com.base.baselibs.net.MyStringCallback;
import com.base.baselibs.util.LogUtils;
import com.base.baselibs.widget.MyCleanEditText;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
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
 * 描述
 * 作者 tangbingliang
 * 时间 16/8/8 14:53
 * 邮箱 bltang@atman.com
 * 电话 18578909061
 */
public class ReplyListActivity extends MyBaseActivity implements AdapterInterface {

    @Bind(R.id.pullToRefreshListView)
    PullToRefreshListView pullToRefreshListView;
    @Bind(R.id.reply_bar_title_one_tx)
    TextView replyBarTitleOneTx;
    @Bind(R.id.reply_bar_title_one_iv)
    ImageView replyBarTitleOneIv;
    @Bind(R.id.reply_bar_title_one_ll)
    LinearLayout replyBarTitleOneLl;
    @Bind(R.id.reply_bar_title_two_tx)
    TextView replyBarTitleTwoTx;
    @Bind(R.id.reply_bar_title_two_iv)
    ImageView replyBarTitleTwoIv;
    @Bind(R.id.reply_bar_title_two_ll)
    LinearLayout replyBarTitleTwoLl;
    @Bind(R.id.bottom_ll)
    LinearLayout bottomLl;
    @Bind(R.id.reply_bar_title_back_iv)
    ImageView replyBarTitleBackIv;
    @Bind(R.id.reply_bar_title_back_ll)
    LinearLayout replyBarTitleBackLl;
    @Bind(R.id.blogdetail_addemol_iv)
    ImageView blogdetailAddemolIv;
    @Bind(R.id.blogdetail_addcomment_et)
    MyCleanEditText blogdetailAddcommentEt;
    @Bind(R.id.blogdetail_send_bt)
    Button blogdetailSendBt;
    @Bind(R.id.ll1)
    LinearLayout ll1;
    @Bind(R.id.vp_contains)
    ViewPager vpContains;
    @Bind(R.id.iv_image)
    LinearLayout ivImage;
    @Bind(R.id.ll_facechoose)
    RelativeLayout llFacechoose;
    @Bind(R.id.FaceRelativeLayout)
    FaceRelativeLayout faceRelativeLayout;
    @Bind(R.id.reply_bar_title_two_red_iv)
    ImageView replyBarTitleTwoRedIv;

    private Context mContext = ReplyListActivity.this;

    private int mPage = 1;
    private int mStateId = 0;//0:我评论的 1:评论我的

    private ReplayListAdapter mAdapter;
    private int mPosition;
    private String mReplayName;
    private long blogId;
    private long commentId;

    private List<TouChuanOtherNotice> mTouChuanOtherNotice;
    private TouChuanOtherNoticeDao mOtherNoticeDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_replylist);
        ButterKnife.bind(this);
    }

    @Override
    public void initWidget(View... v) {
        super.initWidget(v);
        hideTitleBar();

        initListView();

        mOtherNoticeDao = MyBaseApplication.getApplication().getDaoSession().getTouChuanOtherNoticeDao();

        if (MyBaseApplication.isReportUnRead) {
            replyBarTitleTwoRedIv.setVisibility(View.VISIBLE);
        } else {
            replyBarTitleTwoRedIv.setVisibility(View.INVISIBLE);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(YunXinAddFriendEvent event) {
        setTuBiao();
    }

    private void setTuBiao() {
        mTouChuanOtherNotice = mOtherNoticeDao.queryBuilder().where(TouChuanOtherNoticeDao.Properties.NoticeType.eq(4)
                , TouChuanOtherNoticeDao.Properties.IsRead.eq(0)).build().list();
        if (mTouChuanOtherNotice!=null && mTouChuanOtherNotice.size()>0) {
            MyBaseApplication.isReportUnRead=true;
        } else {
            MyBaseApplication.isReportUnRead=false;
        }
        if (MyBaseApplication.isReportUnRead) {
            replyBarTitleTwoRedIv.setVisibility(View.VISIBLE);
        } else {
            replyBarTitleTwoRedIv.setVisibility(View.INVISIBLE);
        }
    }

    private void initListView() {
        initRefreshView(PullToRefreshBase.Mode.BOTH, pullToRefreshListView);

        View mEmpty = LayoutInflater.from(mContext).inflate(R.layout.part_list_empty, null);
        TextView mEmptyTX = (TextView) mEmpty.findViewById(R.id.empty_list_tx);
        mEmptyTX.setText("暂无回复");

        mAdapter = new ReplayListAdapter(mContext, getmWidth(), this);
        pullToRefreshListView.setEmptyView(mEmpty);
        pullToRefreshListView.setAdapter(mAdapter);
        pullToRefreshListView.getRefreshableView().setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                    cancelIM(view);
                }
                if (llFacechoose.getVisibility() == View.VISIBLE) {
                    llFacechoose.setVisibility(View.GONE);
                }
                if (bottomLl.getVisibility() == View.VISIBLE) {
                    bottomLl.setVisibility(View.GONE);
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
            }
        });
    }

    @Override
    public void doInitBaseHttp() {
        super.doInitBaseHttp();
        doHttp(true);
    }

    private void doHttp(boolean b) {
        OkHttpUtils.get().url(Common.Url_Get_UserComment + mStateId + "/" + mPage)
                .addHeader("cookie", MyBaseApplication.getApplication().getCookie())
                .tag(Common.NET_GET_USERCOMMENT).id(Common.NET_GET_USERCOMMENT).build()
                .execute(new MyStringCallback(mContext, this, b));
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onError(Call call, Exception e, int code, int id) {
        super.onError(call, e, code, id);
        mPage = 1;
        onLoad(PullToRefreshBase.Mode.BOTH, pullToRefreshListView);
    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase refreshView) {
        super.onPullUpToRefresh(refreshView);
        mPage += 1;
        doHttp(false);
    }

    @Override
    public void onPullDownToRefresh(PullToRefreshBase refreshView) {
        super.onPullDownToRefresh(refreshView);
        mPage = 1;
        doHttp(false);
    }

    @Override
    public void onStringResponse(String data, Response response, int id) {
        super.onStringResponse(data, response, id);
        if (id == Common.NET_GET_USERCOMMENT) {
            ReplayListModel mReplayListModel = mGson.fromJson(data, ReplayListModel.class);
            if (mReplayListModel.getBody() == null
                    || mReplayListModel.getBody().size() == 0) {
                if (mAdapter != null && mAdapter.getCount() > 0) {
                    showToast("没有更多");
                }
                onLoad(PullToRefreshBase.Mode.PULL_FROM_START, pullToRefreshListView);
            } else {
                onLoad(PullToRefreshBase.Mode.BOTH, pullToRefreshListView);
                if (mPage == 1) {
                    mAdapter.clearData();
                }
                mAdapter.addBody(mReplayListModel.getBody());
            }
        } else if (id == Common.NET_ADD_COMMENT) {
            showToast("评论成功");
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        OkHttpUtils.getInstance().cancelTag(Common.NET_GET_USERCOMMENT);
        OkHttpUtils.getInstance().cancelTag(Common.NET_GET_BLOGCOLLECTION_NOT);
        OkHttpUtils.getInstance().cancelTag(Common.NET_ADD_BROWSE);
        OkHttpUtils.getInstance().cancelTag(Common.NET_ADD_COMMENT);
    }

    @OnClick({R.id.reply_bar_title_one_ll, R.id.reply_bar_title_two_ll, R.id.reply_bar_title_back_iv
            , R.id.reply_bar_title_back_ll, R.id.blogdetail_send_bt})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.reply_bar_title_one_ll:
                mStateId = 0;
                mPage = 1;
                mAdapter.setmStateId(0);
                mAdapter.clearData();
                replyBarTitleOneTx.setTextColor(getResources().getColor(R.color.reply_top_selected_tx));
                replyBarTitleOneIv.setVisibility(View.VISIBLE);
                replyBarTitleTwoTx.setTextColor(getResources().getColor(R.color.reply_top_unselect_tx));
                replyBarTitleTwoIv.setVisibility(View.INVISIBLE);
                doHttp(true);
                break;
            case R.id.reply_bar_title_two_ll:
                if (mTouChuanOtherNotice!=null) {
                    for(int i=0;i<mTouChuanOtherNotice.size();i++) {
                        mTouChuanOtherNotice.get(i).setIsRead(1);
                        mOtherNoticeDao.update(mTouChuanOtherNotice.get(i));
                    }
                    EventBus.getDefault().post(new YunXinMessageEvent());
                }
                MyBaseApplication.isReportUnRead = false;
                replyBarTitleTwoRedIv.setVisibility(View.GONE);
                mStateId = 1;
                mPage = 1;
                mAdapter.setmStateId(1);
                mAdapter.clearData();
                replyBarTitleOneTx.setTextColor(getResources().getColor(R.color.reply_top_unselect_tx));
                replyBarTitleOneIv.setVisibility(View.INVISIBLE);
                replyBarTitleTwoTx.setTextColor(getResources().getColor(R.color.reply_top_selected_tx));
                replyBarTitleTwoIv.setVisibility(View.VISIBLE);
                doHttp(true);
                break;
            case R.id.reply_bar_title_back_iv:
            case R.id.reply_bar_title_back_ll:
                finish();
                break;
            case R.id.blogdetail_send_bt:
                if (!isLogin()) {
                    showLogin();
                    return;
                }
                sendMessage(view);
                break;
        }
    }

    private void sendMessage(View v) {
        if (isIMOpen()) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0); //强制隐藏键盘
        }

        if (llFacechoose.getVisibility() == View.VISIBLE) {
            llFacechoose.setVisibility(View.GONE);
        }
        String str = blogdetailAddcommentEt.getText().toString().trim();
        if (!str.isEmpty()) {
            String jsonStr = "{\"blog_id\":\"" + blogId + "\",\"content\":\"" + str + "\",\"comment_id\":\"" + commentId + "\"}";
            OkHttpUtils.postString().url(Common.Url_Add_Comment).mediaType(Common.JSON)
                    .content(jsonStr).addHeader("cookie", MyBaseApplication.getApplication().getCookie())
                    .id(Common.NET_ADD_COMMENT).tag(Common.NET_ADD_COMMENT)
                    .build().execute(new MyStringCallback(mContext, ReplyListActivity.this, true));
        }

        bottomLl.setVisibility(View.GONE);
        blogdetailAddcommentEt.setHint("");
        blogdetailAddcommentEt.setText("");
    }

    @Override
    public void onItemClick(final View view, int position) {
        mPosition = position;
        switch (view.getId()) {
            case R.id.item_bloglist_head_rl:
                if (MyBaseApplication.getApplication().mGetMyUserIndexModel == null) {
                    showLogin();
                    return;
                }
                if (mAdapter.getItem(position).getBlog().getUser_id() ==
                        MyBaseApplication.getApplication().mGetMyUserIndexModel.getBody().getUserDetailBean().getUserId()) {
                    showWraning("亲，这是你自己哦！");
                    return;
                }
                startActivity(OtherPersonalActivity.buildIntent(mContext, mAdapter.getItem(position).getBlog().getUser_id()));
                break;
            case R.id.item_relay_root_ll:
                if (mAdapter.getItem(position).getBlog() != null) {
                    UiHelper.toCommunityDetail(this, mAdapter.getItem(position).getBlog().getCategory()
                            , mAdapter.getItem(position).getBlog().getTitle(), mAdapter.getItem(position).getBlog().getBlog_id()
                            , mAdapter.getItem(position).getBlog().getVip_level(), -1, null);

                    blogId = mAdapter.getItem(position).getBlog().getBlog_id();
                    OkHttpUtils.postString().url(Common.Url_Add_Browse + blogId).mediaType(Common.JSON)
                            .id(Common.NET_ADD_BROWSE).tag(Common.NET_ADD_BROWSE).content("{}")
                            .addHeader("cookie", MyBaseApplication.getApplication().getCookie())
                            .build().execute(new MyStringCallback(mContext, ReplyListActivity.this, false));
                } else {
                    startActivity(CommentChildrenListActivity.buildIntent(mContext
                            , mAdapter.getItem(position).getParentComment().getBlog_id()
                            , mAdapter.getItem(position).getParentComment().getBlog_comment_id()
                            , mAdapter.getItem(position).getParentComment().getIcon()
                            , 0
                            , mAdapter.getItem(position).getParentComment().getUser_name()
                            , mAdapter.getItem(position).getParentComment().getSex()
                            , mAdapter.getItem(position).getParentComment().getUserLevel()
                            , mAdapter.getItem(position).getParentComment().getCreate_time()
                            , mAdapter.getItem(position).getComment().getUser_id()
                            , mAdapter.getItem(position).getParentComment().getContent()
                            , mAdapter.getItem(position).getParentComment().getUser_id()
                            , false, "", 1
                            , mAdapter.getItem(position).getParentComment().getVip_level(), true));
                }
                break;
            case R.id.item_relay_tx:
                if (mAdapter.getItem(mPosition).getBlog() != null) {
                    mReplayName = mAdapter.getItem(mPosition).getBlog().getUser_name();
                    blogId = mAdapter.getItem(mPosition).getBlog().getBlog_id();
                    commentId = mAdapter.getItem(mPosition).getComment().getBlog_comment_id();
                } else {
                    blogId = mAdapter.getItem(mPosition).getParentComment().getBlog_id();
                    mReplayName = mAdapter.getItem(mPosition).getParentComment().getUser_name();
                    commentId = mAdapter.getItem(mPosition).getParentComment().getBlog_comment_id();
                }
                bottomLl.setVisibility(View.VISIBLE);
                blogdetailAddcommentEt.setHint("回复 " + mReplayName);

                blogdetailAddcommentEt.setFocusable(true);
                blogdetailAddcommentEt.setFocusableInTouchMode(true);
                blogdetailAddcommentEt.requestFocus();

                cancelIM(view);
                InputMethodManager imm = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
                break;
        }
    }
}
