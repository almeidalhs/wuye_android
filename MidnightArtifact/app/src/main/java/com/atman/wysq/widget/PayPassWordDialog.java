package com.atman.wysq.widget;

import android.app.Dialog;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.atman.wysq.R;

/**
 * Created by tangbingliang on 17/3/28.
 */

public class PayPassWordDialog extends Dialog {

    private Context mContext;
    private PasswordView mPasswordView;
    private onPayPassWordListener onPayListener;

    public PayPassWordDialog(Context context, onPayPassWordListener onPayListener) {
        super(context);
        this.mContext = context;
        this.onPayListener = onPayListener;
        init();
    }

    private void init() {
        initWindow();
        View contentView = View.inflate(mContext, R.layout.dialog_paypassword, null);
        setCanceledOnTouchOutside(true);
        setContentView(contentView);

        mPasswordView = (PasswordView) contentView.findViewById(R.id.dialog_pay_pw_view);
        mPasswordView.setOnFinishInput(new PasswordView.OnPasswordInputFinish() {
            @Override
            public void inputFinish() {
                dismiss();
                onPayListener.inputFinish(mPasswordView.getStrPassword());
            }
        });
        mPasswordView.getCancelImageView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                onPayListener.cancelDialog();
            }
        });
        mPasswordView.getForgetTextView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                onPayListener.forgetPassWord();
            }
        });
    }

    /**
     * 初始化window参数
     */
    private void initWindow() {
        requestWindowFeature(Window.FEATURE_NO_TITLE); // 取消标题
        Window dialogWindow = getWindow();
        dialogWindow.getDecorView().setPadding(0, 0, 0, 0);
        dialogWindow.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        dialogWindow.setBackgroundDrawableResource(android.R.color.transparent);
        dialogWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN | WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        DisplayMetrics d = mContext.getResources().getDisplayMetrics();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.gravity = Gravity.BOTTOM;
        dialogWindow.setAttributes(lp);
        // 设置显示动画
        dialogWindow.setWindowAnimations(R.style.take_photo_anim);
    }

    public interface onPayPassWordListener {
        void inputFinish(String pw);
        void cancelDialog();
        void forgetPassWord();
    }
}
