package com.atman.wysq.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.drawable.AnimationDrawable;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.atman.wysq.R;
import com.atman.wysq.model.bean.ImMessage;
import com.atman.wysq.ui.base.MyBaseApplication;
import com.atman.wysq.utils.Common;
import com.atman.wysq.utils.MyTools;
import com.atman.wysq.widget.face.SmileUtils;
import com.atman.wysq.yunxin.model.ContentTypeInter;
import com.base.baselibs.util.DensityUtil;
import com.base.baselibs.util.LogUtils;
import com.base.baselibs.widget.CustomImageView;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 描述
 * 作者 tangbingliang
 * 时间 16/9/1 16:06
 * 邮箱 bltang@atman.com
 * 电话 18578909061
 */
public class P2PChatAdapter extends BaseAdapter {

    private List<ImMessage> mImMessage;

    private Context context;
    protected LayoutInflater layoutInflater;
    private long time = 0;
    private PullToRefreshListView p2pChatLv;
    private boolean isBottom = false;
    private P2PAdapterInter mP2PAdapterInter;
    private int width;
    private long l1 = 5L;//定义一个long型变量
    private boolean leftChange = false;
    private boolean rightChange = false;
    private String leftImageUrl = "";
    private String rightImageUrl = "";
    private boolean isPay;
    private Handler handler;
    private Runnable runnable;

    public P2PChatAdapter(Context context, int width, PullToRefreshListView p2pChatLv, boolean isPay
            , Handler handler, Runnable runnable, P2PAdapterInter mP2PAdapterInter) {
        this.context = context;
        this.mImMessage = new ArrayList<>();
        this.p2pChatLv = p2pChatLv;
        this.isPay = isPay;
        this.mP2PAdapterInter = mP2PAdapterInter;
        this.width = width;
        this.handler = handler;
        this.runnable = runnable;
        layoutInflater = LayoutInflater.from(context);
    }

    public void setLeftImageUrl(String leftImageUrl) {
        this.leftImageUrl = leftImageUrl;
        notifyDataSetChanged();
    }

    public void setRightImageUrl(String rightImageUrl) {
        this.rightImageUrl = rightImageUrl;
        notifyDataSetChanged();
    }

    public void setLeftChange(boolean leftChange) {
        this.leftChange = leftChange;
    }

    public void setRightChange(boolean rightChange) {
        this.rightChange = rightChange;
    }

    public void clearData() {
        this.mImMessage.clear();
        notifyDataSetChanged();
    }

    public void addImMessageDao(List<ImMessage> mImMessageDao) {
        this.mImMessage.addAll(mImMessageDao);
        notifyDataSetChanged();
        p2pChatLv.getRefreshableView().setSelection(p2pChatLv.getRefreshableView().getBottom());
    }

    public void setImMessageStatus(String id, int status) {
        for (int i = 0; i < mImMessage.size(); i++) {
            if (mImMessage.get(i).getUuid().equals(id)) {
                mImMessage.get(i).setIsSeedSuccess(status);
            }
        }
        notifyDataSetChanged();
    }

    public void addImMessageDao(ImMessage mImMessageDao) {
        this.mImMessage.add(mImMessageDao);
        notifyDataSetChanged();
        p2pChatLv.getRefreshableView().setSelection(p2pChatLv.getRefreshableView().getBottom());
    }

    @Override
    public int getViewTypeCount() {
        return 5;
    }

    @Override
    public int getCount() {
        return mImMessage.size();
    }

    @Override
    public int getItemViewType(int position) {
        return mImMessage.get(position).getContentType();
    }

    @Override
    public ImMessage getItem(int position) {
        return mImMessage.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holderText = null;

        int type = getItemViewType(position);
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.item_p2pchat_text_view, parent, false);
            holderText = new ViewHolder(convertView);
            convertView.setTag(holderText);
        } else {
            holderText = (ViewHolder) convertView.getTag();
        }

        final ImMessage temp = mImMessage.get(position);

        holderText.itemP2pchatTextLeftTx.setVisibility(View.GONE);
        holderText.itemP2pchatImageLeftIv.setVisibility(View.GONE);
        holderText.itemP2pchatFingerLeftIv.setVisibility(View.GONE);
        holderText.itemP2pchatAudioLeftLl.setVisibility(View.GONE);

        holderText.itemP2pchatTextRightTx.setVisibility(View.GONE);
        holderText.itemP2pchatImageRightIv.setVisibility(View.GONE);
        holderText.itemP2pchatFingerRightIv.setVisibility(View.GONE);
        holderText.itemP2pchatAudioRightLl.setVisibility(View.GONE);
        holderText.itemP2pchatRightPayTx.setVisibility(View.GONE);
        if (temp.getIsSelfSend()) {
            holderText.itemP2pchatTextHeadrightIv.setVisibility(View.VISIBLE);
            holderText.itemP2pchatTextHeadleftIv.setVisibility(View.GONE);
            if (holderText.itemP2pchatTextHeadrightIv.getDrawable() == null || rightChange) {
                rightChange = false;
                ImageLoader.getInstance().displayImage(Common.ImageUrl +
                        MyBaseApplication.getApplication().mGetMyUserIndexModel.getBody().getUserDetailBean().getUserExt().getIcon()
                        , holderText.itemP2pchatTextHeadrightIv, MyBaseApplication.getApplication().getOptionsNot(), mListener);
            }
        } else {
            holderText.itemP2pchatTextHeadrightIv.setVisibility(View.GONE);
            holderText.itemP2pchatTextHeadleftIv.setVisibility(View.VISIBLE);
            if (holderText.itemP2pchatTextHeadleftIv.getDrawable() == null || leftChange) {
                leftChange = false;
                ImageLoader.getInstance().displayImage(Common.ImageUrl + leftImageUrl
                        , holderText.itemP2pchatTextHeadleftIv, MyBaseApplication.getApplication().getOptionsNot(), mListener);
            }
        }
        if (Math.abs(MyTools.getGapCountM(time, temp.getTime())) >= l1 || position==0) {
            time =  mImMessage.get(position).getTime();
            holderText.itemP2pchatTextTimeTx.setVisibility(View.VISIBLE);
            holderText.itemP2pchatTextTimeTx.setText(MyTools.convertTimeS(temp.getTime()));
        } else {
            holderText.itemP2pchatTextTimeTx.setVisibility(View.INVISIBLE);
        }
//        if (temp.getIsSeedSuccess() == 0) {
//            holderText.itemP2pchatRightProgress.setVisibility(View.VISIBLE);
//        } else {
//            holderText.itemP2pchatRightProgress.setVisibility(View.GONE);
//            if (temp.getIsSeedSuccess() == 2) {
//                holderText.itemP2pchatRightAlert.setVisibility(View.VISIBLE);
//            } else {
//                holderText.itemP2pchatRightAlert.setVisibility(View.GONE);
//            }
//        }

        switch (type) {
            case ContentTypeInter.contentTypeText:
                if (temp.getIsSelfSend()) {
                    holderText.itemP2pchatTextRightTx.setVisibility(View.VISIBLE);
                    holderText.itemP2pchatTextRightTx.setText(SmileUtils.getEmotionContent(context
                            , holderText.itemP2pchatTextRightTx, temp.getContent()));
                } else {
                    holderText.itemP2pchatTextLeftTx.setVisibility(View.VISIBLE);
                    holderText.itemP2pchatTextLeftTx.setText(SmileUtils.getEmotionContent(context
                            , holderText.itemP2pchatTextRightTx, temp.getContent()));
                }
                break;
            case ContentTypeInter.contentTypeImage:
                if (temp.getIsSelfSend()) {
                    holderText.itemP2pchatImageRightIv.setVisibility(View.VISIBLE);
                    if (temp.getImageThumUrl().startsWith("http")) {
                        ImageLoader.getInstance().displayImage(temp.getImageUrl(), holderText.itemP2pchatImageRightIv
                                , MyBaseApplication.getApplication().getOptionsNot(), new picImage(0));
                    } else {
                        File mFile = new File(temp.getImageFilePath());
                        if (mFile.exists()) {
                            ImageLoader.getInstance().displayImage("file://" + temp.getImageFilePath(), holderText.itemP2pchatImageRightIv
                                    , MyBaseApplication.getApplication().getOptionsNot(), new picImage(0));
                        } else {
                            ImageLoader.getInstance().displayImage(temp.getImageUrl(), holderText.itemP2pchatImageRightIv
                                    , MyBaseApplication.getApplication().getOptionsNot(), new picImage(0));
                        }
                    }
                } else {
                    holderText.itemP2pchatImageLeftIv.setVisibility(View.VISIBLE);
                    ImageLoader.getInstance().displayImage(temp.getImageUrl(), holderText.itemP2pchatImageLeftIv
                            , MyBaseApplication.getApplication().getOptionsNot(), new picImage(1));
                }
                break;
            case ContentTypeInter.contentTypeImageSmall:
                if (temp.getIsSelfSend()) {
                    holderText.itemP2pchatImageRightIv.setVisibility(View.VISIBLE);
                    if (temp.getImageSThumUrl().startsWith("http")) {
                        ImageLoader.getInstance().displayImage(temp.getImageSUrl(), holderText.itemP2pchatImageRightIv
                                , MyBaseApplication.getApplication().getOptionsNot(), mListener);
                    } else {
                        File mFile = new File(temp.getImageSFilePath());
                        if (mFile.exists()) {
                            ImageLoader.getInstance().displayImage("file://" + temp.getImageSFilePath(), holderText.itemP2pchatImageRightIv);
                        } else {
                            ImageLoader.getInstance().displayImage(temp.getImageSUrl(), holderText.itemP2pchatImageRightIv
                                    , MyBaseApplication.getApplication().getOptionsNot(), mListener);
                        }
                    }
                } else {
                    holderText.itemP2pchatImageLeftIv.setVisibility(View.VISIBLE);
                    ImageLoader.getInstance().displayImage(temp.getImageSUrl(), holderText.itemP2pchatImageLeftIv, MyBaseApplication.getApplication().getOptionsNot());
                }
                break;
            case ContentTypeInter.contentTypeAudio:
                int w = (int) ((175/60*temp.getAudioDuration()) + 75);
                FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(DensityUtil.dp2px(context, w)
                        , FrameLayout.LayoutParams.WRAP_CONTENT);
                if (temp.getIsSelfSend()) {
                    holderText.itemP2pchatAudioRightLl.setVisibility(View.VISIBLE);
                    holderText.itemP2pchatAudioRightLl.setLayoutParams(params);
                    holderText.itemP2pchatAudioRightTx.setText(temp.getAudioDuration()+"''");
                } else {
                    holderText.itemP2pchatAudioLeftLl.setLayoutParams(params);
                    holderText.itemP2pchatAudioLeftLl.setVisibility(View.VISIBLE);
                    holderText.itemP2pchatAudioLeftTx.setText(temp.getAudioDuration()+"''");
                }
                break;
            case ContentTypeInter.contentTypeFinger:
                if (temp.getIsSelfSend()) {
                    holderText.itemP2pchatFingerRightIv.setVisibility(View.VISIBLE);
                    if (temp.getContent().equals("[石头]")) {
                        holderText.itemP2pchatFingerRightIv.setImageResource(R.mipmap.message_actionfunc_finger_0001);
                    } else if (temp.getContent().equals("[剪刀]")) {
                        holderText.itemP2pchatFingerRightIv.setImageResource(R.mipmap.message_actionfunc_finger_0002);
                    } else if (temp.getContent().equals("[布]")) {
                        holderText.itemP2pchatFingerRightIv.setImageResource(R.mipmap.message_actionfunc_finger_0003);
                    }
                } else {
                    holderText.itemP2pchatFingerLeftIv.setVisibility(View.VISIBLE);
                    if (temp.getContent().equals("[石头]")) {
                        holderText.itemP2pchatFingerLeftIv.setImageResource(R.mipmap.message_actionfunc_finger_0001);
                    } else if (temp.getContent().equals("[剪刀]")) {
                        holderText.itemP2pchatFingerLeftIv.setImageResource(R.mipmap.message_actionfunc_finger_0002);
                    } else if (temp.getContent().equals("[布]")) {
                        holderText.itemP2pchatFingerLeftIv.setImageResource(R.mipmap.message_actionfunc_finger_0003);
                    }
                }
                break;
        }

        holderText.itemP2pchatRightPayTx.setVisibility(View.GONE);
        if (isPay && !temp.getIsGiftMessage() && temp.getIsSelfSend()) {
            holderText.itemP2pchatRightPayTx.setVisibility(View.VISIBLE);
            holderText.itemP2pchatRightPayTx.setText("-"+MyBaseApplication.kPrivateChatCost);
        }

        final ViewHolder finalHolderText = holderText;
        holderText.itemP2pchatAudioRightLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mP2PAdapterInter.onItemAudio(v, position, (AnimationDrawable) finalHolderText.itemP2pchatAudioRightIv.getDrawable());
            }
        });

        holderText.itemP2pchatAudioLeftLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mP2PAdapterInter.onItemAudio(v, position, (AnimationDrawable) finalHolderText.itemP2pchatAudioLeftIv.getDrawable());
            }
        });

        holderText.itemP2pchatImageRightIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (temp.getContentType()!=ContentTypeInter.contentTypeImageSmall) {
                    mP2PAdapterInter.onItem(v, position);
                }
            }
        });
        holderText.itemP2pchatImageLeftIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (temp.getContentType()!=ContentTypeInter.contentTypeImageSmall) {
                    mP2PAdapterInter.onItem(v, position);
                }
            }
        });
        holderText.itemP2pchatRootRl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mP2PAdapterInter.onItem(v, position);
            }
        });

        holderText.itemP2pchatTextHeadleftIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mP2PAdapterInter.onItem(v, position);
            }
        });

        return convertView;
    }

    private ImageLoadingListener mListener = new ImageLoadingListener() {
        @Override
        public void onLoadingStarted(String s, View view) {

        }

        @Override
        public void onLoadingFailed(String s, View view, FailReason failReason) {

        }

        @Override
        public void onLoadingComplete(String s, View view, Bitmap bitmap) {
            if (!isBottom) {
                isBottom = true;
                handler.postDelayed(runnable, 500);
            }
        }

        @Override
        public void onLoadingCancelled(String s, View view) {

        }
    };

    class picImage implements ImageLoadingListener{
        private int direct;

        public picImage (int direct) {
            this.direct = direct;
        }

        @Override
        public void onLoadingStarted(String s, View view) {

        }

        @Override
        public void onLoadingFailed(String s, View view, FailReason failReason) {

        }

        @Override
        public void onLoadingComplete(String s, View view, Bitmap bitmap) {
            ImageView im = (ImageView) view;
            int w = DensityUtil.dp2px(context, 90);
            int h = DensityUtil.dp2px(context, 122);
            im.getLayoutParams().height = h;
            im.getLayoutParams().width = w;
            im.setImageBitmap(canvasTriangle(bitmap, direct, w, h));
            if (!isBottom) {
                isBottom = true;
                handler.postDelayed(runnable, 500);
            }
        }

        @Override
        public void onLoadingCancelled(String s, View view) {

        }
    }

    /**
     * 绘制成微信聊天效果
     * @param bitmapimg
     * @param direct
     * @return
     */
    public Bitmap canvasTriangle(Bitmap bitmapimg, int direct, int width, int height) {
        Bitmap srcmapimg = bitmapimg;
        Bitmap output = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_4444);
        Canvas canvas = new Canvas(output);

        srcmapimg = bigImage(srcmapimg, (float) width, (float) height);

        //设置默认背景颜色
        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, srcmapimg.getWidth(), srcmapimg.getHeight());

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        if (direct == 0) {//右边
            Bitmap bpRight = BitmapFactory.decodeResource(context.getResources(),R.mipmap.im_imageview_right_bg);
            Rect rectRight = new Rect(0, 0, bpRight.getWidth(), bpRight.getHeight());
            canvas.drawBitmap(bpRight, rectRight, rectRight, paint);
        } else if (direct == 1) {//左边
            Bitmap bpLeft = BitmapFactory.decodeResource(context.getResources(),R.mipmap.im_imageview_left_bg);
            Rect rectLeft = new Rect(0, 0, bpLeft.getWidth(), bpLeft.getHeight());
            canvas.drawBitmap(bpLeft, rectLeft, rectLeft, paint);
        }
        //两层绘制交集。显示上层
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(srcmapimg, rect, rect, paint);
        return output;
    }

    //把传进来的bitmap对象转换为宽度为x,长度为y的bitmap对象
    public Bitmap bigImage(Bitmap b,float x,float y) {

        float scale;
        float dx = 0, dy = 0;

        if (b.getWidth() * y > x * b.getHeight()) {
            scale = (float) y / (float) b.getHeight();
            dx = (x - b.getWidth() * scale) * 0.5f;
        } else {
            scale = (float) x / (float) b.getWidth();
            dy = (y - b.getHeight() * scale) * 0.5f;
        }

        Matrix mDrawMatrix = new Matrix();
        mDrawMatrix.setScale(scale, scale);
        mDrawMatrix.postTranslate((int) (dx + 0.5f), (int) (dy + 0.5f));
        Bitmap resizeBmp = Bitmap.createBitmap(b, 0, 0, b.getWidth(), b.getHeight(), mDrawMatrix, true);
        int n = (resizeBmp.getWidth() - (int) x)/2;
        resizeBmp = Bitmap.createBitmap(resizeBmp, n, 0, (int) x, resizeBmp.getHeight());
        return resizeBmp;
    }

    static class ViewHolder {
        @Bind(R.id.item_p2pchat_text_time_tx)
        TextView itemP2pchatTextTimeTx;
        @Bind(R.id.item_p2pchat_text_headleft_iv)
        CustomImageView itemP2pchatTextHeadleftIv;
        @Bind(R.id.item_p2pchat_text_headright_iv)
        CustomImageView itemP2pchatTextHeadrightIv;
        @Bind(R.id.item_p2pchat_text_left_tx)
        TextView itemP2pchatTextLeftTx;
        @Bind(R.id.item_p2pchat_image_left_iv)
        ImageView itemP2pchatImageLeftIv;
        @Bind(R.id.item_p2pchat_finger_left_iv)
        ImageView itemP2pchatFingerLeftIv;
        @Bind(R.id.item_p2pchat_audio_left_tx)
        TextView itemP2pchatAudioLeftTx;
        @Bind(R.id.item_p2pchat_audio_left_iv)
        ImageView itemP2pchatAudioLeftIv;
        @Bind(R.id.item_p2pchat_audio_left_ll)
        LinearLayout itemP2pchatAudioLeftLl;
        @Bind(R.id.item_p2pchat_right_progress)
        ProgressBar itemP2pchatRightProgress;
        @Bind(R.id.item_p2pchat_right_alert)
        ImageView itemP2pchatRightAlert;
        @Bind(R.id.item_p2pchat_text_right_tx)
        TextView itemP2pchatTextRightTx;
        @Bind(R.id.item_p2pchat_image_right_iv)
        ImageView itemP2pchatImageRightIv;
        @Bind(R.id.item_p2pchat_finger_right_iv)
        ImageView itemP2pchatFingerRightIv;
        @Bind(R.id.item_p2pchat_audio_right_iv)
        ImageView itemP2pchatAudioRightIv;
        @Bind(R.id.item_p2pchat_audio_right_tx)
        TextView itemP2pchatAudioRightTx;
        @Bind(R.id.item_p2pchat_right_pay_tx)
        TextView itemP2pchatRightPayTx;
        @Bind(R.id.item_p2pchat_audio_right_ll)
        LinearLayout itemP2pchatAudioRightLl;
        @Bind(R.id.item_p2pchat_root_Rl)
        RelativeLayout itemP2pchatRootRl;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    public interface P2PAdapterInter {
        void onItem(View v, int position);

        void onItemAudio(View v, int position, AnimationDrawable animationDrawable);
    }
}
