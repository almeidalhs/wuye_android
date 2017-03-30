package com.atman.wysq.ui.community;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.atman.wysq.R;
import com.atman.wysq.adapter.ReportListAdapter;
import com.atman.wysq.model.request.ReportModel;
import com.atman.wysq.model.response.GetReportListModel;
import com.atman.wysq.ui.base.MyBaseActivity;
import com.atman.wysq.ui.base.MyBaseApplication;
import com.atman.wysq.utils.Common;
import com.base.baselibs.net.MyStringCallback;
import com.tbl.okhttputils.OkHttpUtils;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Response;

/**
 * Created by tangbingliang on 17/3/30.
 */

public class ReportListActivity extends MyBaseActivity {

    @Bind(R.id.reportlist_listview)
    ListView reportlistListview;

    private Context mContext = ReportListActivity.this;
    private GetReportListModel mGetReportListModel;

    private ReportListAdapter mAdapter;

    private long bolgId;
    private int typeId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reportlist);
        ButterKnife.bind(this);
    }

    public static Intent buildIntent(Context context, long bolgId, int typeId) {
        Intent intent = new Intent(context, ReportListActivity.class);
        intent.putExtra("bolgId", bolgId);
        intent.putExtra("typeId", typeId);
        return intent;
    }

    @Override
    public void initWidget(View... v) {
        super.initWidget(v);
        setBarTitleTx("举报");

        bolgId = getIntent().getLongExtra("bolgId", -1);
        typeId = getIntent().getIntExtra("typeId", 1);

        mAdapter = new ReportListAdapter(mContext);
        reportlistListview.setAdapter(mAdapter);
        reportlistListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (mAdapter.getItem(position).getIs_other()==1) {
                    startActivity(ReportActivity.buildIntent(mContext, (long) bolgId, 2));
                    finish();
                } else {
                    ReportModel mReportModel = new ReportModel(mAdapter.getItem(position).getDescription()
                            , typeId, bolgId);
                    OkHttpUtils.postString().url(Common.Url_RePort).mediaType(Common.JSON)
                            .content(mGson.toJson(mReportModel))
                            .addHeader("cookie", MyBaseApplication.getApplication().getCookie())
                            .id(Common.NET_REPORT).tag(Common.NET_REPORT).build()
                            .execute(new MyStringCallback(mContext, ReportListActivity.this, true));
                }
            }
        });
    }

    @Override
    public void doInitBaseHttp() {
        super.doInitBaseHttp();
        OkHttpUtils.get().url(Common.Url_Get_Report_List + typeId).id(Common.NET_GET_REPORT_LIST_ID)
                .addHeader("cookie", MyBaseApplication.getApplication().getCookie())
                .tag(Common.NET_GET_REPORT_LIST_ID).build().execute(new MyStringCallback(mContext, this, true));
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onStringResponse(String data, Response response, int id) {
        super.onStringResponse(data, response, id);
        if (id == Common.NET_GET_REPORT_LIST_ID) {
            mGetReportListModel = mGson.fromJson(data, GetReportListModel.class);

            if (mGetReportListModel.getBody().size()>0) {
                mAdapter.setBody(mGetReportListModel.getBody());
            }
        } else if (id == Common.NET_REPORT) {
            showToast("举报成功");
            finish();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        OkHttpUtils.getInstance().cancelTag(Common.NET_GET_REPORT_LIST_ID);
        OkHttpUtils.getInstance().cancelTag(Common.NET_REPORT);
    }
}
