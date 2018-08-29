package com.wei.android.lib.fingerprintidentify.customtab;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.wei.android.lib.fingerprintidentify.R;

public class RxDialogFingerPrint extends CustomtabDialog {
    private TextView mTvContent;
    private TextView mTvCancel;
    private FingerPrinterView mFingerPrinterView;

    public FingerPrinterView getmFingerPrinterView() {
        return mFingerPrinterView;
    }

    public void setmFingerPrinterView(FingerPrinterView mFingerPrinterView) {
        this.mFingerPrinterView = mFingerPrinterView;
    }

    public TextView getmTvCancel() {
        return mTvCancel;
    }

    public void setmTvCancel(TextView mTvCancel) {
        this.mTvCancel = mTvCancel;
    }

    public void setCancelListener(View.OnClickListener cancelListener) {
        mTvCancel.setOnClickListener(cancelListener);
    }

    public RxDialogFingerPrint(Context context) {
        super(context);
        initView();
        setCancelable(false);
        setCanceledOnTouchOutside(false);
    }

    public void setContent(String content) {
        this.mTvContent.setText(content);
    }

    public TextView getContentView() {
        return mTvContent;
    }

    private void initView() {
        View dialogView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_finger_print, null);
        mFingerPrinterView= (FingerPrinterView) dialogView.findViewById(R.id.finger_printer_view);
        mTvCancel  = (TextView) dialogView.findViewById(R.id.tv_cancel);
        mTvContent = (TextView) dialogView.findViewById(R.id.tv_content);
        mTvContent.setTextIsSelectable(true);
        setContentView(dialogView);
    }
}
