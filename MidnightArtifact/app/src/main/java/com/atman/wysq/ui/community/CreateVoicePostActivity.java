package com.atman.wysq.ui.community;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.atman.wysq.R;
import com.atman.wysq.adapter.CreateVoicePostAdapter;
import com.atman.wysq.model.response.GoodsListModel;
import com.atman.wysq.ui.base.MyBaseActivity;
import com.atman.wysq.ui.base.MyBaseApplication;
import com.atman.wysq.utils.Common;
import com.base.baselibs.iimp.AdapterInterface;
import com.base.baselibs.util.DensityUtil;
import com.base.baselibs.widget.MyCleanEditText;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Response;

/**
 * Created by tangbingliang on 17/4/6.
 */

public class CreateVoicePostActivity extends MyBaseActivity implements AdapterInterface,View.OnClickListener {

    @Bind(R.id.create_voicepost_lv)
    ListView createVoicepostLv;

    private Context mContext = CreateVoicePostActivity.this;

    private CreateVoicePostAdapter mAdapter;
    private View headView;
    private MyCleanEditText postTitleEt;
    private ImageView postBgIv;
    private Button postListeningtestBt, postRecordingBt;
    private TextView postTimeTx,postChangeImgTx;
    private RelativeLayout partPostRl;

    private LinearLayout.LayoutParams paramsBg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_voicepost);
        ButterKnife.bind(this);
    }

    @Override
    public void initWidget(View... v) {
        super.initWidget(v);
        setBarTitleTx("发布语音");

        initListview();
    }

    private void initListview() {
        int w = getmWidth()- DensityUtil.dp2px(mContext, 20);
        paramsBg = new LinearLayout.LayoutParams(w, w*7/10);

        headView = LayoutInflater.from(mContext).inflate(R.layout.part_voice_post_head_view, null);
        partPostRl = (RelativeLayout) headView.findViewById(R.id.part_post_rl);
        postTitleEt = (MyCleanEditText) headView.findViewById(R.id.post_title_et);
        postBgIv = (ImageView) headView.findViewById(R.id.post_bg_iv);
        postTimeTx = (TextView) headView.findViewById(R.id.post_time_tx);
        postListeningtestBt = (Button) headView.findViewById(R.id.post_listeningtest_bt);
        postRecordingBt = (Button) headView.findViewById(R.id.post_recording_bt);
        postChangeImgTx = (TextView) headView.findViewById(R.id.post_change_img_tx);
        postListeningtestBt.setOnClickListener(this);
        postRecordingBt.setOnClickListener(this);
        postChangeImgTx.setOnClickListener(this);

        partPostRl.setLayoutParams(paramsBg);

        mAdapter = new CreateVoicePostAdapter(mContext, this);
        createVoicepostLv.addHeaderView(headView, null, true);
        createVoicepostLv.setHeaderDividersEnabled(false);
        createVoicepostLv.setAdapter(mAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (getIntent().getIntExtra("Goods_id", -1)!=-1 && mAdapter!=null && MyBaseApplication.isRelation) {
            GoodsListModel temp = new GoodsListModel(getIntent().getIntExtra("Goods_id", -1)
                    , getIntent().getStringExtra("Pic_img"), getIntent().getStringExtra("Title")
                    , getIntent().getStringExtra("Discount_price"));
            MyBaseApplication.isRelation = false;
            mAdapter.clearData();
            mAdapter.addBody(MyBaseApplication.creatPostGoods);
            boolean isRelationed = false;
            for (int i=0;i<MyBaseApplication.creatPostGoods.size();i++) {
                if (MyBaseApplication.creatPostGoods.get(i).getGoods_id()==temp.getGoods_id()) {
                    isRelationed = true;
                    showToast("此商品已关联");
                    break;
                }
            }
            if (!isRelationed) {
                mAdapter.addBody(temp);
            }
            postTitleEt.setText(MyBaseApplication.imagetextPostTitle);
            getIntent().removeExtra("Goods_id");
        }
    }

    @Override
    public void onStringResponse(String data, Response response, int id) {
        super.onStringResponse(data, response, id);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onItemClick(View view, int position) {
        switch (view.getId()) {
            case R.id.item_childtwo_delete_iv:
                mAdapter.deleteDataById(position);
                break;
            case R.id.item_childtwo_add_ll:
                MyBaseApplication.creatPostGoods.clear();
                MyBaseApplication.creatPostGoods.addAll(mAdapter.getGoodsList());
                mAdapter.clearData();
                MyBaseApplication.isRelation = true;
                MyBaseApplication.imagetextPostTitle = postTitleEt.getText().toString();
                startActivityForResult(new Intent(mContext, MallActivity.class)
                        , Common.fromCreateImageText);
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.post_listeningtest_bt:
                break;
            case R.id.post_recording_bt:
                break;
            case R.id.post_change_img_tx:
                break;
        }
    }
}
