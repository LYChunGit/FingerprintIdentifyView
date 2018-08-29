package com.wei.android.lib.fingerprintidentify.view;

import android.app.Activity;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.wei.android.lib.fingerprintidentify.FingerprintIdentify;
import com.wei.android.lib.fingerprintidentify.base.BaseFingerprint;
import com.wei.android.lib.fingerprintidentify.customtab.FingerPrinterView;
import com.wei.android.lib.fingerprintidentify.customtab.RxDialogFingerPrint;

import org.w3c.dom.Text;

public class FingerprintIdentifyView {
    private RxDialogFingerPrint mRxDialogFingerPrint;
    private FingerprintIdentify mFingerprintIdentify;
    private BaseFingerprint.FingerprintIdentifyListener mListener;
    private Activity mActivity;
    FingerprintIdentifyViewReturn mFingerprintIdentifyViewReturn;

    public FingerprintIdentifyView(Activity mActivity,FingerprintIdentifyViewReturn fingerprintIdentifyViewReturn,String mContentMsg){
        this.mActivity = mActivity;
        this.mFingerprintIdentifyViewReturn = fingerprintIdentifyViewReturn;
        isSupportFingerprint();//初始化
        if (null == mRxDialogFingerPrint) {
            synchronized (RxDialogFingerPrint.class) {
                if (null == mRxDialogFingerPrint) {
                    mRxDialogFingerPrint = new RxDialogFingerPrint(mActivity);
                    mRxDialogFingerPrint.setCancelable(false);
                    mRxDialogFingerPrint.setCanceledOnTouchOutside(false);
                    //mContentMsg设置指纹提示信息
                    if (!TextUtils.isEmpty(mContentMsg)){
                        mRxDialogFingerPrint.getContentView().setText(mContentMsg);
                    }
                }
            }
        }
    }

    public boolean isSupportFingerprint(){
        if (mFingerprintIdentify == null){
            mFingerprintIdentify = new FingerprintIdentify(mActivity);
        }
        if (mFingerprintIdentify.isFingerprintEnable() && mFingerprintIdentify.isHardwareEnable() && mFingerprintIdentify.isRegisteredFingerprint()){
            return true;
        }else {
            return false;
        }
    }

    public void showRxDialogFingerPrint() {
        if (isSupportFingerprint()) {
            // 指纹硬件可用并已经录入指纹
            // 指纹硬件是否可用
            // 是否已经录入指纹
            //指纹识别登陆
            initRxFingerPrinter(mRxDialogFingerPrint.getmFingerPrinterView());
            mRxDialogFingerPrint.getmTvCancel().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mRxDialogFingerPrint.cancel();
                }
            });
            mRxDialogFingerPrint.getmFingerPrinterView().setOnStateChangedListener(new FingerPrinterView.OnStateChangedListener() {
                @Override
                public void onChange(int state) {
                    if (state == FingerPrinterView.STATE_CORRECT_PWD) {
                        mFingerprintIdentifyViewReturn.onSucceed();
                        mRxDialogFingerPrint.cancel();
                    }
                    if (state == FingerPrinterView.STATE_WRONG_PWD) {
                        mFingerprintIdentifyViewReturn.onFailed();
                        mRxDialogFingerPrint.getmFingerPrinterView().setState(FingerPrinterView.STATE_NO_SCANING);
                    }
                }
            });
            if (!mRxDialogFingerPrint.isShowing()) {
                mRxDialogFingerPrint.show();
            }
        }
    }

    private void initRxFingerPrinter(FingerPrinterView fingerPrinterView) {
        if (fingerPrinterView.getState() == FingerPrinterView.STATE_SCANING) {
            return;
        } else if (fingerPrinterView.getState() == FingerPrinterView.STATE_CORRECT_PWD
                || fingerPrinterView.getState() == FingerPrinterView.STATE_WRONG_PWD) {
            fingerPrinterView.setState(FingerPrinterView.STATE_NO_SCANING);
        } else {
            fingerPrinterView.setState(FingerPrinterView.STATE_SCANING);
        }
        mListener = new BaseFingerprint.FingerprintIdentifyListener() {
            @Override
            public void onSucceed() {
                // 验证成功，自动结束指纹识别
                mFingerprintIdentify.cancelIdentify();// 关闭指纹识别
                mRxDialogFingerPrint.getmFingerPrinterView().setState(FingerPrinterView.STATE_CORRECT_PWD);
            }

            @Override
            public void onNotMatch(int availableTimes) {
                // 指纹不匹配，并返回可用剩余次数并自动继续验证
                Toast.makeText(mActivity,"指纹不匹配，还可以尝试" + availableTimes + "次",Toast.LENGTH_LONG).show();
                mRxDialogFingerPrint.getmFingerPrinterView().setState(FingerPrinterView.STATE_WRONG_PWD);
            }

            @Override
            public void onFailed(boolean isDeviceLocked) {
                // 错误次数达到上线或者API报错停止了验证，自动结束指纹识别
                Toast.makeText(mActivity,"1分钟后可重试!",Toast.LENGTH_LONG).show();
                mRxDialogFingerPrint.getmFingerPrinterView().setState(FingerPrinterView.STATE_WRONG_PWD);
                resumeIdentify();
            }

            @Override
            public void onStartFailedByDeviceLocked() {
                mRxDialogFingerPrint.getmFingerPrinterView().setState(FingerPrinterView.STATE_WRONG_PWD);
            }
        };
        mFingerprintIdentify.startIdentify(3, mListener);
    }

    private void resumeIdentify() {
        // 注意：倒计时时间都是毫秒。倒计时总时间+间隔
        new CountDownTimer(60000, 1000) {
            public void onTick(long millisUntilFinished) {
//                mTextField.setText("seconds remaining: " + millisUntilFinished / 1000);
            }
            public void onFinish() {
                mFingerprintIdentify.resumeIdentify();// 恢复指纹识别并保证错误次数不变
                mFingerprintIdentify.startIdentify(3, mListener);
            }
        }.start();// 调用CountDownTimer对象的start()方法开始倒计时，也不涉及到线程处理
    }

    //隐藏当前的指纹窗口
    public void dismissRxDialogFingerPrint() {
        if (mRxDialogFingerPrint != null && mRxDialogFingerPrint.isShowing()) {
            mRxDialogFingerPrint.dismiss();
        }
    }

    //activity 销毁调用
    public void fingerprintIdentifyClose(){
        if (mFingerprintIdentify != null) {
            mFingerprintIdentify.cancelIdentify();
        }
    }


    public interface FingerprintIdentifyViewReturn {
        void onSucceed();

        void onFailed();
    }
}
