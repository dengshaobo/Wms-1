package com.hzx.wms.utils;

import android.app.Activity;
import android.content.Context;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

/**
 * Created by linhu on 2019/6/20.
 */

public class EditSearchAction {
    private SearchActionListener listener;

    public void searchAction(final Activity activity, final EditText editText) {
        //输入法搜索
        editText.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
                }

                listener.searchActionOnNext(editText.getText().toString());
            }
            return false;
        });
    }

    public void setListener(SearchActionListener listener) {
        this.listener = listener;
    }

    public interface SearchActionListener {
        void searchActionOnNext(String str);
    }
}
