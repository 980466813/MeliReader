package com.lning.melireader.widget;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatTextView;
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
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import com.lning.melireader.R;
import com.lning.melireader.core.app.listener.OnItemClickListener;
import com.lning.melireader.core.repository.db.pojo.TagPojo;
import com.lning.melireader.core.utils.ScreenUtils;
import com.lning.melireader.core.utils.ToastUtils;

import java.util.List;

import am.widget.wraplayout.WrapLayout;


public class TagDialog extends DialogFragment {

    //点击发表，内容不为空时的回调
    private OnItemClickListener mListener;

    private ProgressDialog progressDialog;
    private String hint;

    private Dialog dialog;
    private WrapLayout wrapLayout;
    private List<TagPojo> tagPojos;

    public TagDialog() {
    }

    @SuppressLint("ValidFragment")
    public TagDialog(List<TagPojo> tagPojoList, String hint, OnItemClickListener listener) {//提示文字
        this.hint = hint;
        this.tagPojos = tagPojoList;
        this.mListener = listener;

    }


    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // 使用不带Theme的构造器, 获得的dialog边框距离屏幕仍有几毫米的缝隙。
        dialog = new Dialog(getActivity(), R.style.BottomDialogStyle);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // 设置Content前设定
//        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        View contentview = View.inflate(getActivity(), R.layout.dialog_tag_layout, null);
        dialog.setContentView(contentview);
        dialog.setCanceledOnTouchOutside(true); // 外部点击取消
        // 设置宽度为屏宽, 靠近屏幕底部。
        Window window = dialog.getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.gravity = Gravity.BOTTOM; // 紧贴底部
        lp.alpha = 1;
        lp.dimAmount = 0.5f;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.width = WindowManager.LayoutParams.MATCH_PARENT; // 宽度持平
        window.setAttributes(lp);
        window.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        contentview.findViewById(R.id.toolbar_left_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        final AppCompatTextView finish = contentview.findViewById(R.id.toolbar_right_btn);
        finish.setVisibility(tagPojos.size() == 0 ? View.GONE : View.VISIBLE);
        wrapLayout = contentview.findViewById(R.id.tag_wrap_wl);
        if (tagPojos != null && tagPojos.size() > 0) {
            for (TagPojo tagPojo : tagPojos) {
                wrapLayout.addView(makeTagView(tagPojo));
            }
        } else {
            contentview.findViewById(R.id.tag_option_tv).setVisibility(View.GONE);
            wrapLayout.setVisibility(View.GONE);
        }
        final AppCompatEditText inputDlg = contentview.findViewById(R.id.tag_create_et);
        inputDlg.setHint(hint);
        inputDlg.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (tagPojos.size() == 0) {
                    if (s.length() > 0) {
                        finish.setVisibility(View.VISIBLE);
                    } else {
                        finish.setVisibility(View.GONE);
                    }
                }
            }
        });

        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < wrapLayout.getChildCount(); i++) {
                    CheckBox checkBox = (CheckBox) wrapLayout.getChildAt(i);
                    if (checkBox.isChecked()) {
                        sb.append("," + checkBox.getText().toString());
                    }
                }
                String tag = inputDlg.getText().toString();
                if (TextUtils.isEmpty(sb.toString()) && TextUtils.isEmpty(tag)) {
                    ToastUtils.show(getContext(), "标签内容不能为空");
                    return;
                } else {
                    if (TextUtils.isEmpty(tag)) {
                        tag = sb.toString().substring(1, sb.toString().length());
                    } else {
                        tag += sb.toString();
                    }
                    progressDialog = new ProgressDialog(getActivity());
                    progressDialog.setCanceledOnTouchOutside(false);
                    progressDialog.show();
                    progressDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
                        @Override
                        public boolean onKey(DialogInterface dialogInterface, int keyCode, KeyEvent keyEvent) {
                            if (keyCode == KeyEvent.KEYCODE_BACK && keyEvent.getAction() == KeyEvent.ACTION_DOWN) {
                                hideProgressDialog();
                                mListener.OnItemClick(null, -3);
                            }
                            return false;
                        }
                    });
                    mListener.OnItemClick(null, tag, -2);
                }
            }
        });
        inputDlg.setFocusable(true);
        inputDlg.setFocusableInTouchMode(true);
        return dialog;
    }

    private CheckBox makeTagView(TagPojo tagPojo) {
        final CheckBox checkBox = new CheckBox(getContext());
        checkBox.setButtonDrawable(null);
        checkBox.setTextColor(checkBox.isChecked() ? getResources().getColor(R.color.md_white) : getResources().getColor(R.color.md_grey_400));
        checkBox.setBackgroundResource(R.drawable.bg_tag_tv);
        checkBox.setText(tagPojo.getContent());
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                checkBox.setTextColor(b ? getResources().getColor(R.color.md_white) : getResources().getColor(R.color.md_grey_400));
            }
        });
        return checkBox;
    }

    public void hideProgressDialog() {
        if (null != progressDialog && progressDialog.isShowing()) {
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
}