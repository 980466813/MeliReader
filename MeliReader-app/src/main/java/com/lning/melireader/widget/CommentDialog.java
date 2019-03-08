package com.lning.melireader.widget;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.DialogFragment;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.lning.melireader.R;
import com.lning.melireader.core.app.listener.OnItemClickListener;
import com.lning.melireader.core.utils.LogUtils;
import com.lning.melireader.core.utils.ScreenUtils;
import com.lning.melireader.core.utils.ToastUtils;


public class CommentDialog extends DialogFragment {

    //点击发表，内容不为空时的回调
    private OnItemClickListener mListener;

    private ProgressDialog progressDialog;
    private String hint;

    private Dialog dialog;
    private EditText inputDlg;
    private int numCount = 150;
    private String tag = null;

    public CommentDialog() {
    }


    @SuppressLint("ValidFragment")
    public CommentDialog(String hint, OnItemClickListener sendBackListener) {//提示文字
        this.hint = hint;
        this.mListener = sendBackListener;

    }


    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // 使用不带Theme的构造器, 获得的dialog边框距离屏幕仍有几毫米的缝隙。
        dialog = new Dialog(getActivity(), R.style.BottomDialogStyle);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // 设置Content前设定
//        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        View contentview = View.inflate(getActivity(), R.layout.dialog_comment_layout, null);
        dialog.setContentView(contentview);
        dialog.setCanceledOnTouchOutside(true); // 外部点击取消
        // 设置宽度为屏宽, 靠近屏幕底部。
        Window window = dialog.getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.gravity = Gravity.BOTTOM; // 紧贴底部
        lp.alpha = 1;
        lp.dimAmount = 0.5f;
        lp.height = ScreenUtils.dip2px(getContext(), 180.0F);
        lp.width = WindowManager.LayoutParams.MATCH_PARENT; // 宽度持平
        window.setAttributes(lp);
        window.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        inputDlg = contentview.findViewById(R.id.dialog_input_et);
        inputDlg.setHint(hint);
        final Button tv_send = contentview.findViewById(R.id.dialog_publish_btn);
        inputDlg.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0) {
                    tv_send.setEnabled(true);
                } else {
                    tv_send.setEnabled(false);
                }

            }
        });

        tv_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(inputDlg.getText().toString())) {
                    ToastUtils.show(getContext(), "评论内容不能为空");
                    return;
                } else {
                    progressDialog = new ProgressDialog(getActivity());
                    progressDialog.setCanceledOnTouchOutside(false);
                    progressDialog.show();
                    progressDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
                        @Override
                        public boolean onKey(DialogInterface dialogInterface, int keyCode, KeyEvent keyEvent) {
                            if (keyCode == KeyEvent.KEYCODE_BACK && keyEvent.getAction() == KeyEvent.ACTION_DOWN) {
                                hideProgressDialog();
                                mListener.OnItemClick(null, -1);
                            }
                            return false;
                        }
                    });
                    mListener.OnItemClick(null, inputDlg.getText().toString(), -1);
                }
            }
        });
        inputDlg.setFocusable(true);
        inputDlg.setFocusableInTouchMode(true);
        inputDlg.requestFocus();
        return dialog;
    }

    public void hideProgressDialog() {
        if (null != progressDialog && progressDialog.isShowing()) {
            LogUtils.d("hideProgressDialog执行");
            progressDialog.dismiss();
        }
    }

    public void hideSoftkeyboard() {
        try {
            ((InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE))
                    .hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(),
                            InputMethodManager.HIDE_NOT_ALWAYS);
        } catch (NullPointerException e) {

        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}