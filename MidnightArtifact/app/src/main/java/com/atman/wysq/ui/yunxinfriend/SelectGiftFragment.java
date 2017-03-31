package com.atman.wysq.ui.yunxinfriend;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.atman.wysq.R;
import com.atman.wysq.adapter.SelectGiftAdapter;
import com.atman.wysq.model.response.GiftListModel;
import com.atman.wysq.ui.base.MyBaseApplication;
import com.atman.wysq.ui.base.MyBaseFragment;
import com.atman.wysq.ui.personal.wallet.DiamondsToCoinActivity;
import com.atman.wysq.ui.personal.wallet.RechargeActivity;
import com.atman.wysq.utils.Common;
import com.atman.wysq.utils.SortComparator;
import com.base.baselibs.iimp.AdapterInterface;
import com.base.baselibs.net.MyStringCallback;
import com.base.baselibs.util.LogUtils;
import com.base.baselibs.widget.PromptDialog;
import com.tbl.okhttputils.OkHttpUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Response;

/**
 * 描述
 * 作者 tangbingliang
 * 时间 16/9/8 11:21
 * 邮箱 bltang@atman.com
 * 电话 18578909061
 */
public class SelectGiftFragment extends MyBaseFragment implements AdapterInterface {

    @Bind(R.id.select_gift_gv)
    GridView selectGiftGv;

    private int page = 0;
//    private GiftListModel mGiftListModel = new GiftListModel();
    private List<GiftListModel.BodyEntity> data = new ArrayList<>();
    private List<GiftListModel.BodyEntity> mGiftList = new ArrayList<>();
    private SelectGiftAdapter mAdapter;

    private int cionNum;
    private int myPosition;
    private String id;
    private int fromId;
    private int pageSize = 8;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_selectgift, null);
        ButterKnife.bind(this, view);
        Bundle b = getArguments();
        page = b.getInt("page");
        id = b.getString("id");
        fromId = b.getInt("fromId", 0);
        pageSize = b.getInt("pageSize", 8);
        GiftListModel mGiftListModel = (GiftListModel) b.getSerializable("data");
        mGiftList = mGiftListModel.getBody();
        Comparator comp = new SortComparator();
        Collections.sort(mGiftList, comp);
        for (int i=(page*pageSize);i<mGiftList.size();i++) {
            data.add(mGiftList.get(i));
        }
        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initWidget(View... v) {
        super.initWidget(v);
        mAdapter = new SelectGiftAdapter(getActivity(), getmWidth(), data, pageSize, this);
        selectGiftGv.setNumColumns(pageSize/2);
        selectGiftGv.setAdapter(mAdapter);
        selectGiftGv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (((SelectGiftActivity)getActivity()).isFaceLl()) {
                    ((SelectGiftActivity)getActivity()).isInput(view);
                    if (cionNum >= mAdapter.getItem(position).getCharm()) {
                        showPayDialog(position);
                    } else {
                        showToast("金币余额不足");
                    }
                }
            }
        });
    }

    private void showPayDialog(final int po) {
        myPosition = po;
        String str = "确定花费"+mAdapter.getItem(po).getCharm()
                +"金币赠送对方礼物["+mAdapter.getItem(po).getName()+"]吗？";
        PromptDialog.Builder builder = new PromptDialog.Builder(getActivity());
        builder.setMessage(str);
        builder.setPositiveButton("不送了", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.setNegativeButton("赠送", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                if (mAdapter.getItem(po).getCharm()>MyBaseApplication.getApplication().mGetMyUserIndexModel
                        .getBody().getUserDetailBean().getUserExt().getLeft_coin()) {
                    showNotDialog();
                    return;
                }
                String url = Common.Url_Pay_GiftList;
                int reponseId = Common.NET_PAY_GIFTLIST;
                if (fromId == 1) {
                    url = Common.Url_Reward;
                    reponseId = Common.NET_REWARD_ID;
                }
                OkHttpUtils.postString().url(url + mAdapter.getItem(po).getGift_id()+"/"+id).content("")
                        .mediaType(Common.JSON).addHeader("cookie", MyBaseApplication.getApplication().getCookie())
                        .tag(reponseId).id(reponseId).build()
                        .execute(new MyStringCallback(getActivity(), SelectGiftFragment.this, true));
            }
        });
        builder.show();
    }

    private void showNotDialog() {
        String str = "钻石"+MyBaseApplication.getApplication().mGetMyUserIndexModel
                .getBody().getUserDetailBean().getUserExt().getConvert_coin()
                +"     金币："+MyBaseApplication.getApplication().mGetMyUserIndexModel
                .getBody().getUserDetailBean().getUserExt().getLeft_coin();
        PromptDialog.Builder builder = new PromptDialog.Builder(getActivity());
        builder.setTitle("很抱歉，您的账户余额不足");
        builder.setMessage(str);
        builder.setPositiveButton("钻石兑换金币", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                startActivity(new Intent(getActivity(), DiamondsToCoinActivity.class));
            }
        });
        builder.setNegativeButton("前往充值金币", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                startActivity(RechargeActivity.buildIntent(getActivity()
                        , MyBaseApplication.getApplication().mGetMyUserIndexModel.getBody().getUserDetailBean().getUserExt().getLeft_coin()
                        , MyBaseApplication.getApplication().mGetMyUserIndexModel.getBody().getUserDetailBean().getUserExt().getConvert_coin()));
            }
        });
        builder.show();
    }

    @Override
    public void onStringResponse(String data, Response response, int id) {
        super.onStringResponse(data, response, id);
        if (id == Common.NET_PAY_GIFTLIST
                || id == Common.NET_REWARD_ID) {
            ((SelectGiftActivity)getActivity()).backResuilt(mAdapter.getItem(myPosition).getPic_url()
                    , mAdapter.getItem(myPosition).getPaopao(), mAdapter.getItem(myPosition).getPrice()
                    , mAdapter.getItem(myPosition).getName(), mAdapter.getItem(myPosition).getPic_url()
                    , mAdapter.getItem(myPosition).getGift_id());
        }
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
        cionNum = MyBaseApplication.getApplication().mGetMyUserIndexModel
                .getBody().getUserDetailBean().getUserExt().getLeft_coin();
        if (mAdapter!=null) {
            mAdapter.setMyCion(cionNum);
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
    }

    @Override
    public void onError(Call call, Exception e, int code, int id) {
        super.onError(call, e, code, id);
        cancelLoading();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        OkHttpUtils.getInstance().cancelTag(Common.NET_PAY_GIFTLIST);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onItemClick(View view, int position) {

    }
}
